<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Welcome</title>
</head>
<body>
<form action ="mainPage.jsp" method="post">
<input type="submit" name="Submit" value="回到主页">
</form>
<div align="center">
<form action ="controller1.jsp" method="post">
  <p><strong><font face="黑体">欢迎使用基站流量统计器</font></strong></p>
    <table width="58%" height="344" border="9" cellspacing="10" bordercolor="#99CC00" bgcolor="#99CC00">
      <tr bordercolor="#000000"> 
        <td width="46%" rowspan="2"> 
          <h3 align="center">请输入基站号</h3>
        <p align="center">
          <%
          String numbers1=(String)session.getAttribute("numbers");		//动态保存用户输入
          if(null!=numbers1){%> 
          <textarea name="numbers" cols="30" rows="7"><%=numbers1 %></textarea>
          <%}else{ %>
          <textarea name="numbers" cols="30" rows="7">7,1001145,1001015,1001112,1001071,1001083,1001073,1001121</textarea>
          <%
          }
           %>
        </p>
        </td>
        <td width="54%" height="89" bordercolor="#000000"> 
          <h4 align="center">请输入开始时间</h4>
        <p align="center"> 
        <% 
        String begintimes1=(String)session.getAttribute("begintimes");
        if(null!=begintimes1){
        %>
          <input type="text" name="begintimes" value="<%=begintimes1 %>">
        <%}else{ %>
          <input type="text" name="begintimes" value="2012-3-28 00:00:01">
          <%}
          %>
        </p></td>
    </tr>
    <tr> 
        <td height="89" bordercolor="#000000"> 
          <h4 align="center">请输入结束时间</h4>
        <p align="center"> 
        <%
        String endtimes1=(String)session.getAttribute("endtimes");
        if(null!=endtimes1){
         %>
          <input type="text" name="endtimes" value="<%=endtimes1 %>">
          <%}else{ %>
          <input type="text" name="endtimes" value="2012-3-28 23:04:00">
          <%} %>
        </p></td>
    </tr>
  </table>
  <p> 
    <input type="submit" name="Submit" value="提交">
    &nbsp; &nbsp;&nbsp;&nbsp; 
    <input name="reset" type="reset" id="reset" value="重置">
  </p>
  </form>
</div>
<center>
<h5>注意：不能进行跨月查询，跨天查询不能超过连续3天。</h5>
</center>

</body>
