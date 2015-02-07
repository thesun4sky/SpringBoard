<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table class="table table-bordered">
		<thead>
			<tr>
				<td>글번호</td>
				<td>제목</td>
				<td>작성자</td>
				<td>작성일</td>
				<td>조회수</td>
			</tr>
		</thead>
		
		<tbody>
			<c:choose>
				<c:when test="${listModel.hasArticle == false }">
					<tr>
						<td colspan="5">글이 없습니다.</td>
					</tr>
				</c:when>
				
				<c:otherwise>
					<c:forEach var="article" items="${listModel.articleList}">
						<tr>
							<td>${article.id}</td>
							<td>
								<c:if test="${article.level>0 }">
									<c:forEach begin="1" end="${article.level }">-</c:forEach>&gt;
								</c:if>
								<c:set var ="query" value="articleId=${article.id}&p=${listModel.requestPage }"/>
								<a href="<c:url value="read?${query }"/>">${article.title }</a>
							</td>
							<td>${article.writerName }</td>
							<td>${article.postingDate }</td>
							<td>${article.readCount }</td>
						</tr>
					</c:forEach>
					
					<tr>
						<td colspan="5">
							<c:set var="beginPage" value="${listModel.beginPage}"/>
							<c:set var="endPage" value="${listModel.endPage}"/>
							
							<c:if test="${beginPage>10 }">
								<a href="javascript:goNumberPage(${beginPage-1 })">이전</a>
							</c:if>
							<c:forEach var="pno" begin="${beginPage}" end="${endPage }">
								<a href="javascript:goNumberPage(${pno})">[${pno}]</a>
							</c:forEach>
							<c:if test="${endPage<listModel.totalPageCount }">
								<a href="javascript:goNumberPage(${endPage+1 })">다음</a>
							</c:if>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
		
	</table>
	<button id="btn_submit" class="btn btn-default">작성</button>
	
	<script>
	$(function(){
		 $("#btn_submit").click(function(){
			 $("#mainTable").load("WriteForm","p="+p);
		 });
	});
	
	function goNumberPage(pageNumber){
		$("#mainTable").load("listView","p="+pageNumber);
	}
	
	function goReadArticle(query){
		$("#mainTable").load("readArticle",query);
	}
	</script>
</body>
</html>