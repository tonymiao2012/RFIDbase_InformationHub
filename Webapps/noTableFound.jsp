<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="baseInfo.SomeFunction" %>
<%@page import="baseInfo.database" %>
<%@page import="java.sql.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Welcome</title>
</head>
<body>
<%@include file="StatisticsLogin.jsp" %>
<%
//�����ϲ���Ϣ
int[] tag=(int[])session.getAttribute("tag");
String[] dateA=(String[])session.getAttribute("dateA");

//�������ҵ��ĸ��ǿձ�
if(tag.length!=dateA.length)
	out.println("����");
out.println("<center>");
for(int i=0;i<dateA.length;i++){
	if(tag[i]==0){
		out.println("<font color=#FF0000>");
		out.println(dateA[i]);
		out.println("</font>");
		}
}
out.println("<font color=#FF0000>");
out.println(" û�ж�Ӧ�����ݿ��������������");
out.println("</font>");
out.println("</center>");

 %>
</body>
</html>