<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="/resources/css/bootstrap-ko.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-responsive.css"/>"
	rel="stylesheet">
<!-- JQUERY -->
<script src="<c:url value="/resources/js/jquery-1.11.2.js"/>"></script>
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<title>Home</title>
</head>
<body>
	<div>
		<%-- <jsp:include page="board/writeForm.jsp"></jsp:include> --%>
		 <jsp:include page="board/list.jsp"></jsp:include> 
	</div>
	
</body>
</html>
