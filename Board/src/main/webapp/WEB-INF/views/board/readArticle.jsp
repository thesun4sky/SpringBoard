<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div><button onclick="goReplyArticle(${Article.id},${currentPage})">답글</button><button>수정</button><button>삭제</button></div>
	<div class="borderLine" style="margin-left: 20px; margin-top: 20px;">
			<div class="form-group">
				<h3>제목</h3> 
				<div class="borderLine" id="title">${Article.title }</div>
			</div>

			<div class="form-group">
				<h3>작성자</h3>
				<div class="borderLine" id="writerName">${Article.writerName }</div>
			</div>
			
			<div class="form-group">
				<h3>글 내용</h3> 
				<div class="borderLine" id="content">${Article.content }</div>
			</div>
	</div>
	
	<script>
	
	function goReplyArticle(id,currentPage){
		$("#mainTable").load("replyArticle","parentId="+id+"&p="+currentPage);
	}
		
	</script>
</body>
</html>