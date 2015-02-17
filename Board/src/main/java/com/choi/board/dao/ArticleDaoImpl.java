package com.choi.board.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.choi.board.model.Article;
import com.choi.board.model.ArticleListModel;
import com.choi.board.util.IdGenerator;

@Component
public class ArticleDaoImpl implements ArticleDao {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(Article article) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Insert Start");
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into article ");
		sql.append("(group_id,sequence_no,posting_date,read_count,writer_name, ");
		sql.append("password,title,content) ");
		sql.append("values(?,?,?,0,?,?,?,?)");
		
		int groupId = article.getGroupId();
		if(groupId == 0 ){ //글이 리플이 아닌 경우에는 ID Generator를 생성 해주자.
			groupId = IdGenerator.getInstance().generateNextId("article",jdbcTemplate);
			article.setGroupId(groupId);
			//그리고 해당 글에 대한 레벨 역시 초기화를 필요로 한다.
			DecimalFormat decimalFormat = new DecimalFormat("0000000000");
			article.setSequenceNumber(decimalFormat.format(groupId)+"999999");
		}
		
		article.setPostingDate(new Date());
		
		
		jdbcTemplate.update(sql.toString(),article.getGroupId(),article.getSequenceNumber()
				,new Timestamp(article.getPostingDate().getTime())
				,article.getWriterName()
				,article.getPassword()
				,article.getTitle()
				,article.getContent());
		
		

	}

	@Override
	public void update(Article article) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int articleId) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("delete from article where article_id = ?");
		
		jdbcTemplate.update(sql.toString(), articleId);
	}

	@Override
	public Article read(int article_id,int requestPageNumber) throws Exception {
		System.out.println("read 시작");
		StringBuffer sql = new StringBuffer();
		sql.append("select article_id,title,writer_name,content,sequence_no,group_id,password from article where article_id = ?");

		System.out.println("article_id =" + article_id);
		RowMapper<Article> mapper = new RowMapper<Article>() {
		    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	Article readArticle = new Article();
		    	readArticle.setId(rs.getInt("article_id"));
		    	readArticle.setTitle(rs.getString("title"));
		    	readArticle.setWriterName(rs.getString("writer_name"));
		    	readArticle.setContent(rs.getString("content"));
		    	readArticle.setSequenceNumber(rs.getString("sequence_no"));
		    	readArticle.setGroupId(rs.getInt("group_id"));
		    	readArticle.setPassword(rs.getString("password"));
		      return readArticle;
		    }
		  };
		  
		Article article =(Article)jdbcTemplate.queryForObject(sql.toString(),new Object[]{article_id},mapper);
		System.out.println("read 정상 종료");
		
		return article;
	}

	public List<Article> select(int firstRow,int endRow) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select article_id,group_id,sequence_no,posting_date,read_count,writer_name,password,title");
		sql.append(" from article order by sequence_no desc limit ? , ?");
		
		
		
		List<Article> list = new ArrayList<Article>();
		list = jdbcTemplate.query(sql.toString(),new Object[]{firstRow,endRow},new RowMapper<Article>(){
			public Article mapRow(ResultSet rs,int rowNum)throws SQLException{
				Article article = new Article();
				article.setId(rs.getInt("article_id"));
				article.setGroupId(rs.getInt("group_id"));
				article.setSequenceNumber(rs.getString("sequence_no"));
				article.setPostingDate(rs.getTimestamp("posting_date"));
				article.setReadCount(rs.getInt("read_count"));
				article.setWriterName(rs.getString("writer_name"));
				article.setPassword(rs.getString("password"));
				article.setTitle(rs.getString("title"));
				
				return article;
			}
		});
		
		return list;
	}

	@Override
	public ArticleListModel getArticleList(int requestPageNumber) throws Exception {
		if(requestPageNumber<0){
			throw new IllegalArgumentException("ilegalPageNumber");
		}
		
		int totalArticleCount = selectCount();
		System.out.println("totalArticleCount = " + totalArticleCount);
		if(totalArticleCount ==0){
			return new ArticleListModel();
		}
		
		int totalPageCount = calculateTotalPageCount(totalArticleCount);
		int firstRow = (requestPageNumber-1) * COUNT_PER_PAGE;
		//requestPageNumber는 1 부터 시작 해
		//ex) 1페이지 10개  (1-1) * 10  = 0  첫번째 글부터 시작
		int endRow = firstRow + COUNT_PER_PAGE;
		//0+10 니까 끝나는 글의 경우 10번째 글이 되어야 함
		
		
		if(endRow > totalArticleCount){
			endRow = totalArticleCount;
			//만약 endRow가 totalArticleCount보다 클경우 그냥 가장 마지막에 있는것으로 끝
		}
		
		List<Article> articleList = select(firstRow,endRow);
		ArticleListModel articleListView = new ArticleListModel(articleList, requestPageNumber, totalPageCount, firstRow, endRow);
		
		int beginPage = (articleListView.getRequestPage())/10*10+1;
		int endPage = beginPage + 9;
		System.out.println("beginPage = " + beginPage);
		
		if(endPage > articleListView.getTotalPageCount()){
			endPage = articleListView.getTotalPageCount();
		}
		
		articleListView.setBeginPage(beginPage);
		articleListView.setEndPage(endPage);
		return articleListView;
	}

	@Override
	public int calculateTotalPageCount(int totalArticleCount) {
		if(totalArticleCount == 0){
			return 0;
		}
		
		int pageCount = totalArticleCount/COUNT_PER_PAGE;
		//페이지에 대한 총 개수를 가져온다 ex) 178개의 글이 있을 경우 178/10 할 경우 총 17.8이 나오며 우선 17페이지로 하며
		if(totalArticleCount %COUNT_PER_PAGE>0){
			pageCount++; //나머지가 있을경우 해당 페이지를 표시해 줘야 하니까 1페이즈를 증가 하여 총 18 페이지가 된다.
		}
		return pageCount;
	}

	@Override
	public int selectCount() {
		int count = jdbcTemplate.queryForInt("select count(*) from article");
		return count;
	}

	public String selectLastSequenceNumber(String searchMaxSeqNum,
			String searchMinSeqNum) {

		StringBuffer sql = new StringBuffer();
		sql.append("select min(sequence_no) from article where sequence_no< ? and sequence_no >=?");
		
		
		return jdbcTemplate.queryForObject(sql.toString(),new Object[]{searchMaxSeqNum,searchMinSeqNum},String.class);

	}



}
