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

	<div class="borderLine" style="margin-left: 20px; margin-top: 20px;">
			<div class="form-group">
				<h3>제목</h3> 
				<div class="borderLine id="title">${Article.title }</div>
			</div>

			<div class="form-group">
				<h3>작성자</h3>
				<div class="borderLine id="writerName">${Article.writerName }</div>
			</div>
			
			<div class="form-group">
				<h3>글 내용</h3> 
				<div class="borderLine id="content">${Article.content }</div>
			</div>
	</div>
	
	
</body>
</html>