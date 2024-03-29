package com.choi.board.dao;

import java.util.List;

import com.choi.board.model.Article;
import com.choi.board.model.ArticleListModel;

public interface ArticleDao {
	
	public static final int COUNT_PER_PAGE =10;
	
	public void insert(Article article)throws Exception;
	public void update(Article article)throws Exception;
	public void delete(int articleId)throws Exception;
	public Article read(int article_id,int requestPageNumber) throws Exception;
	public List<Article> select(int firstRow,int endRow)throws Exception;
	public ArticleListModel getArticleList(int requestPageNumber) throws Exception;
	public int calculateTotalPageCount(int totalArticleCount);
	public int selectCount();

}
