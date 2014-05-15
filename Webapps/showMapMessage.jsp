<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.lang.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Welcome</title>
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0px; padding: 0px }
  #map_data { height: 50% }
</style>
<%	
	long[] basenumber=(long[])session.getAttribute("baseNumber");
	String[] timearray=(String[])session.getAttribute("timeArray");
	int m=basenumber.length; 
%>

<script type="text/javascript">
var readerid=new Array();
var time=new Array();
var n=<%=m%>
<%for(int i=0;i<basenumber.length;i++){%>		
    readerid[<%=i%>]=<%=basenumber[i]%>;
    time[<%=i%>]=<%=timearray[i]%>;
<%}%>
var i=0;
function set(){
	var intervalid=window.setInterval(showMessage,500);
}
function showMessage(){
	document.writeln(readerid[i]);
	document.writeln(time[i]);
	i++;
	if(i==m-1){
		clearInterval(intervalid);
	}
}
</script>
 

</head>
<body onload="set()">
<div id="map_data" style="width:15%; height:85%;float:left;">
</body>
</html>