<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Welcome</title>
</head>
<body>
<%
String number=request.getParameter("number");		//初始化信息读入
String begintime=request.getParameter("begintime");
String endtime=request.getParameter("endtime");
String range=request.getParameter("range");		//读入一个范围
String range1=request.getParameter("range1");
String range2=request.getParameter("range2");
String radiobutton=request.getParameter("radiobutton");
if(begintime==""||endtime==""||number==""||begintime==null||endtime==null||number==null){		
	//""在文本框中表示空
	response.sendRedirect("error1.jsp");
	return;
}else if(range==""||range1==""||range2==""||range==null||range1==null||range2==null){
	response.sendRedirect("error2.jsp");
	return;
}else if(radiobutton==null||radiobutton==""){
	response.sendRedirect("error3.jsp");
	return;
}else if(radiobutton.equals("radiobutton1")){
	session.setAttribute("number", number);
	session.setAttribute("begintime", begintime);
	session.setAttribute("endtime", endtime);
	session.setAttribute("range", range);
	response.sendRedirect("baseInfo1.jsp");
	return;
}else if(radiobutton.equals("radiobutton2")){
	session.setAttribute("number", number);
	session.setAttribute("begintime", begintime);
	session.setAttribute("endtime", endtime);
	session.setAttribute("range1", range1);
	session.setAttribute("range2", range2);
	response.sendRedirect("baseInfo2.jsp");
}
%>
</body>
</html>