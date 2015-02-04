<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>No title</title>
</head>
<body>

	<div id="mainTable"></div>

<script>

<% 
	String p=request.getParameter("p");
	if(p==null ||p.equals("")){
		p="1";	
	}else{
		
	}
%>
var p = <%=p%>;
console.log(p);
/* document.location.href="listView?p="+p; */
 
 $("#mainTable").load("listView","p="+p);
 

</script>
</body>
</html>