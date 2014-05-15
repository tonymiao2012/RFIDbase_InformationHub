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
<form action ="controller.jsp" method="post">
<center>
  <table width="633" height="268" border=9 align=center cellspacing="10" bordercolor="#99CC00" bgcolor="#99CC00">
    <caption>
    <font face="黑体">欢迎使用基站查询器</font> 
    </caption>
    <tr bordercolor="#000000"> 
      <td width="341" height="34"> 
        <div align="center">请输入基站ID </div>
      </td>
      <td width="260"> 
        <div align="center"> 
          <%
      	  	String number1=(String)session.getAttribute("number");
      	  	if(null!=number1){%>
          <input type="text" name="number" value=<%=number1%>>
          <%}else{%>
          <input type="text" name="number" value="1001089">
          <%}
      	   %>
        </div></td>
    </tr>
    <tr bordercolor="#000000"> 
      <td height="42"> 
        <div align="center">请输入开始时间</div></td>
      <td> 
        <div align="center"> 
          <% 
      		String begintime1=(String)session.getAttribute("begintime");
      		if(null!=begintime1){
      		%>
          <input type="text" name="begintime" value="<%= begintime1 %>">
          <%
      	  	}else{%>
          <input type="text" name="begintime" value="2012-3-12 15:03:00">
          <%}
      	   %>
        </div></td>
    </tr>
    <tr bordercolor="#000000"> 
      <td height="36"> 
        <div align="center">请输入结束时间</div></td>
      <td> 
        <div align="center"> <font size="7"> 
          <% 
      		String endtime1=(String)session.getAttribute("endtime");
      		if(null!=endtime1){%>
          <input type="text" name="endtime" value="<%= endtime1 %>">
          <%}else{%>
          <input type="text" name="endtime" value="2012-3-12 15:04:00">
          <%}
      	   %>
          </font></div></td>
    </tr>
    <tr bordercolor="#000000"> 
      <td height="114"> 
        <div align="center"> 
        <input name="radiobutton" type="radio" value="radiobutton1" checked>
        方式一 <br>
        <% 
          String rangeall=(String)session.getAttribute("range");
          if(null!=rangeall){%>
        <textarea name="range" rows="6" cols="40"><%=rangeall %></textarea>
        <%}else{%>
        <textarea name="range" rows="6" cols="40">268437256,268437302,268437425,268437486,268437501,268437504,268437507,268437509,268437511,268437512,268437513,268437515,268437525,268437526,268437527,268437530,268437534,268437535,268437538,268437539,268437540,268437543,268437546,268437548,268437550,268437551,268437563,268437571,268437575,268437576,268437577,268437583,268437584,268438610,268440259,268440266,268440279,268440296,268440344,268440352,268440357,268440399,268440415,268440435,268440453,268440469,268440489,268440490,268440498,268440500,268440523,268440541,268440569,268440589,268440618,268440619,268440634,268440641,268440678,268440685,268440713,268440714,268440724,268440750,268440776,268440784,268440795,268440865,268440878,268440883,268440938,268440997,268441003,268441005,268441029,268441049,268441061,268441065,268441069,268441070,268441109,268441110,268441131,268441137,268441146,268441156,268441159,268441187,268441195,268441202,268441211,268441222,520094504,535822563</textarea>
        <% }
      		%>
        <br>
      </div></td>
      <td align="center" bgcolor="#99CC00"> 
        <p align="center"> 
          <font color="#000000">
          <input type="radio" name="radiobutton" value="radiobutton2">
          方式二 </font></p>
        <p align="center"> <font color="#000000">
          <% 
      		String rangea=(String)session.getAttribute("range1");
      		if(null!=rangea){
      		%>
          <input name=range1 type="text" value=<%= rangea %> size="12">
          <%
      	  	}else{%>
          <input name=range1 type="text" value="268437534" size="12">
          <%}
      	   %>
          - 
          <% 
      		String rangeb=(String)session.getAttribute("range2");
      		if(null!=rangeb){
      		%>
          <input name=range2 type="text" value=<%= rangeb %> size="12">
          <%
      	  	}else{%>
          <input name=range2 type="text" value="268437550" size="12">
          <%}
      	   %>
          </font> </p></td>
  </table>


<center>
    <td>
<input type="submit" value=提交 /></td>
    &nbsp; &nbsp;&nbsp;&nbsp; 
    <td><input name="reset" type="reset"/></td>
</center>

</form>
<center>
<h5>注意：不能进行跨月查询，跨天查询不能超过连续3天。</h5>
</center>
</body>
</html>