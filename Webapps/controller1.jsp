<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Welcome</title>
</head>
<body>
<%@include file="StatisticsLogin.jsp" %>
<%
String numbers=request.getParameter("numbers");		//初始化信息读入
if(numbers.contains("，")){
	numbers.replace("，",",");
}
String begintimes=request.getParameter("begintimes");
String endtimes=request.getParameter("endtimes");
if(begintimes==null||endtimes==null||begintimes==""||endtimes==""){
	response.sendRedirect("errors1.jsp");
	return;
}else if(numbers==null||numbers==""){
	session.setAttribute("begintimes",begintimes);
	session.setAttribute("endtimes",endtimes);
	response.sendRedirect("staProcess1.jsp");
}else{
	session.setAttribute("numbers",numbers);
	session.setAttribute("begintimes",begintimes);
	session.setAttribute("endtimes",endtimes);
	response.sendRedirect("staProcess.jsp");
}
 %>
</body>
</html>