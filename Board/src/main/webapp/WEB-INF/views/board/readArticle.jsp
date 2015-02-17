<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
#pop {
	width: 300px;
	height: 150px;
	position: absolute;
	top: 10px;
	left: 100px;
	text-align: center;
	background: #3d3d3d;
	color: #fff;
	border: 2px solid #000;
}
</style>


</head>
<body>


	<div class="borderLine" style="margin-left: 20px; margin-top: 20px;">
		<div>
			<button class="btn btn-default" onclick="goReplyArticle(${Article.id},${currentPage})">답글</button>
			<button class="btn btn-default" onclick="pwConfirm('update',${currentPage})">수정</button>
			<button class="btn btn-default" onclick="pwConfirm('delete',${currentPage})">삭제</button>
		</div>
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


	<div id="pop" class="container" style="display: none;">
		<form class="form-signin">
			<div style="height: 100px;">
				<label>패스워드 입력</label> 
				<input type="text" class="form-control"
					id="articlePassword" placeholder="password" />
			</div>
			<input class="btn btn-default" type="button" value="확인"
				onclick="pwConfirmAction(${Article.id})"> &nbsp; <input
				class="btn btn-default" type="button" onclick="popup_close(this)"
				value="취소">
		</form>
	</div>

	<script>
	var articleAction="";
	var currentPage="";
	
	function goReplyArticle(id,currentPage){
		$("#mainTable").load("replyArticle","parentId="+id+"&p="+currentPage);
	}
	
	function pwConfirm(action,curp){
		articleAction = action; 
		currentPage = curp;
		
		$("#pop").show();
	}
	
	function popup_close(obj){
		$("#pop").hide();	  
	}
	
	
	function pwConfirmAction(id){	
		
		$("#mainTable").load("pwConfirm","id="+id+"&action="+articleAction+"&password="+$("#articlePassword").val()+"&currentPage="+currentPage);
/* 		$.ajax({
			url:"pwConfirm",
			type:"post",
			data: {	"id" : id,
					"action" :articleAction,
					"password" :$("#articlePassword").val(),
					"currentPage":currentPage,
					}, 
			dataType: "json",   // 데이터타입을 JSON형식으로 지정
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success: function(data,textStaus,jqXHR){
			},
			error:function(jqXHR,textStatus,errorThrown){

			}
		}) */	
	}
	
	
		
	</script>
</body>
</html>