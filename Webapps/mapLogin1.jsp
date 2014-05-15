<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Welcome</title>
</head>
<body>
<form action="/baseInfo/mapControl" method="post">
<div align="center">
  <table width="87%" height="47" border="9">
    <tr> 
      <td align="left" valign="top">请输入要查询的卡号：
        <% 
        String mapcardid1=(String)session.getAttribute("mapcardid");
        if(null!=mapcardid1){%>
        <input name="mapcardid" type="text" value=<%=mapcardid1%> size="15">
        <%}else{ %>
        <input name="mapcardid" type="text" value="268437513" size="15">
        <%} %>
        开始时间：
        <%
        String mapbegintime1=(String)session.getAttribute("mapbegintime");
        if(null!=mapbegintime1){
        %>
        <input name="mapbegintime" type="text" id="mapbegintime" value="<%=mapbegintime1%>" size="20">
        <%}else{ %>
        <input name="mapbegintime" type="text" id="mapbegintime" value="2012-3-12 00:00:01" size="20">
        <%} %>
        结束时间：
        <%
        String mapendtime1=(String)session.getAttribute("mapendtime");
        if(null!=mapendtime1){
        %>
        <input name="mapendtime" type="text" id="mapendtime" value="<%=mapendtime1%>" size="20">
        <%}else{%>
        <input name="mapendtime" type="text" id="mapendtime" value="2012-3-12 23:59:59" size="20">
       	 <%} %>
	</td>
      </tr>
  </table>
</div>
<div align="center" >
  <input type="submit" name="Submit" value="提交">
  &nbsp;
  <input type="reset" name="Submit2" value="重置">
  &nbsp;
</div>
</form>
</body>
</html>