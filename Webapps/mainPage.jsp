<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Mainpage</title>
</head>
<body>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

<div align="center">
<form action ="control.jsp" method="post">
    <table width="51%" height="201" border="9" align="center" bordercolor="#99CC00">
      <tr> 
    <td align="center" valign="middle" bordercolor="#FFFFFF" bgcolor="#99CC00"> 
      <div align="center">
        <h2><font color="#000000" face="黑体"><strong>欢迎使用基站系统</strong></font></h2>
            <p><strong><font color="#000000" size="3" face="黑体">请选择要使用的功能</font></strong></p>
            <p> 
              <input type="radio" name="button" value="button1" checked>
              <strong>基站查询器</strong> 
              <input type="radio" name="button" value="button2">
              <strong>基站统计器</strong>
              <input type="radio" name="button" value="button3">
              <strong>基站折线地图 </strong></p>
        <p>
          <input type="submit" name="Submit" value="提交">
        </p>
        </div></td>
  </tr>
</table>
  </form>
</div>
</body>
</html>