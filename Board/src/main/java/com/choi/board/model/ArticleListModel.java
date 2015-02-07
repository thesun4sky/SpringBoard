package com.choi.board.model;

import java.util.ArrayList;
import java.util.List;

public class ArticleListModel {

	private List<Article> articleList; // 화면에 뿌려줄 게시글 목록
	private int requestPage; //요청 페이지 번호
	private int totalPageCount; // 총페이지 번호
	private int startRow; //시작 글 번호
	private int endRow; //끝 번호
	private int beginPage;
	private int endPage;
	
	

	public ArticleListModel(){
		this(new ArrayList<Article>(),0,0,0,0);
	}

	public ArticleListModel(List<Article> articleList, int requestPageNumber, int totalPageCount, int startRow,
			int endRow) {
			
		this.articleList = articleList;
		this.requestPage = requestPageNumber;
		this.totalPageCount = totalPageCount;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public int getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(int requestPage) {
		this.requestPage = requestPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	public boolean isHasArticle(){
		return ! articleList.isEmpty();
	}
	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
}
