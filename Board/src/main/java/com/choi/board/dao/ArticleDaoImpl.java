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
import org.springframework.stereotype.Service;

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
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into article ");
		sql.append("(group_id,sequence_no,posting_date,read_count,writer_name, ");
		sql.append("password,title,content) ");
		sql.append("values(?,?,?,0,?,?,?,?)");
		int groupId = IdGenerator.getInstance().generateNextId("article",jdbcTemplate);
		article.setGroupId(groupId);
		article.setPostingDate(new Date());
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		article.setSequenceNumber(decimalFormat.format(groupId)+"999999");
		
		jdbcTemplate.update(sql.toString(),article.getGroupId(),article.getSequenceNumber()
				,new Timestamp(article.getPostingDate().getTime())
				,article.getWriterName()
				,article.getPassword()
				,article.getTitle()
				,article.getContent());
		
		

	}

	@Override
	public void updeate(Article article) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Article article) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Article read(int article_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Article> select(int firstRow,int endRow) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select article_id,group_id,sequence_no,posting_date,read_count,writer_name,password,title");
		sql.append(" from article order by sequence_no desc limit ? , ?");
		
		
		List<Article> list = new ArrayList<Article>();
		list = jdbcTemplate.query(sql.toString(),new Object[]{firstRow-1,endRow-firstRow+1},new RowMapper<Article>(){
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
		System.out.println("totalPageCount = " + totalPageCount);
		int firstRow = (requestPageNumber-1) * COUNT_PER_PAGE+1;
		//requestPageNumber는 1 부터 시작 해
		//ex) 1페이지 10개  (1-1) * 10 + 1 = 1 첫번째 글부터 시작 서
		int endRow = firstRow + COUNT_PER_PAGE-1;
		//1+9 니까 끝나는 글의 경우 10번째 글이 되어야 함
		
		if(endRow > totalArticleCount){
			endRow = totalArticleCount;
			//만약 endRow가 totalArticleCount보다 클경우 그냥 가장 마지막에 있는것으로 끝
		}
		
		List<Article> articleList = select(firstRow,endRow);
		System.out.println("firstRow = " + firstRow+ " endRow = "+ endRow);
		System.out.println("requestPageNumber = " + requestPageNumber);
		ArticleListModel articleListView = new ArticleListModel(articleList, requestPageNumber, totalPageCount, firstRow, endRow);
		return articleListView;
	}

	@Override
	public int calculateTotalPageCount(int totalArticleCount) {
		if(totalArticleCount == 0){
			return 0;
		}
		
		int pageCount = totalArticleCount/COUNT_PER_PAGE;
		if(totalArticleCount %COUNT_PER_PAGE>0){
			pageCount++; //나머지가 있을경우 해당 페이지를 표시해 줘야 하니까
		}
		return pageCount;
	}

	@Override
	public int selectCount() {
		int count = jdbcTemplate.queryForInt("select count(*) from article");
		return count;
	}

}
