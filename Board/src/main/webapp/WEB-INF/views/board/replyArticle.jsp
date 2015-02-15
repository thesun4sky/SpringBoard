<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="borderLine" style="margin-left: 20px; margin-top: 20px;">
		<form action="" role="form" method="post">
			<div class="form-group">
				<label for="title">제목</label> 
				<input type="text" id="title" name="title" size="20" class="form-control" value="Re:"/>
			</div>

			<div class="form-group">
				<label for="writerName">이름</label> 
				<input type="text" id="writerName" name="writerName" class="form-control" placeholder="이름"/>
			</div>

			<div class="form-group">
				<label for="password">암호</label> 
				<input type="password" id="password" name="password" class="form-control" placeholder="암호"/>
			</div>

			<div class="form-group">
				<label for="content">내용</label> 
				<textarea id="content" name="content" class="form-control" cols="40" rows="5"></textarea>
			</div>
			<input type="text" id="currentPage" value="${currentPage}">
			<input type="text" id="id" value="${parentId}">
			<button id="btn_submit" class="btn btn-default">작성</button>
		</form>
	</div>
	
	<script>
		//글쓰기를 AJAX로 구현 해볼까??
		$(function(){
		 $("#btn_submit").click(function(){
			$.ajax({
				url:"replyBoard&currentPage="+$("#currentPage").val()+"&parentId="+$("#parentId").val(),
				type:"post",
				data: {	"title" : $("#title").val(),
						"writerName" :$("#writerName").val(),
						"password" :$("#password").val(),
						"content" : $("#content").val(),		
						}, 
				dataType: "json",   // 데이터타입을 JSON형식으로 지정
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				success: function(data,textStaus,jqXHR){
					
				},
				error:function(jqXHR,textStatus,errorThrown){
					
				}
			})

		 });
	 });
	</script>
</body>
</html>