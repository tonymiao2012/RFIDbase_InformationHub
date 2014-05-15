<%@ page language="java" contentType="text/html; charset=GB2312"
    pageEncoding="GB2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="baseInfo.SomeFunction" %>
<%@page import="baseInfo.database" %>
<%@page import="baseInfo.mapBaseMessage" %>
<%@page import="java.sql.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
</head>
<body>
<%
	String cardid=request.getParameter("mapcardid");	//接收传入参数。成功
	String mapbegintime=request.getParameter("mapbegintime");
	String mapendtime=request.getParameter("mapendtime");
	long mapcardid=Long.parseLong(cardid);		//换为长整型
	try{
		//数据库连接部分
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@10.161.3.190:1521:gatherdata";
		String username="datauser";
		String password="a123";
		//建立连接
		database ds=new database(driver,url,username,password);
		Connection conn=ds.getConnection();
		Statement stm=ds.getStatement(conn);
		//得到所用表名
		SomeFunction f=new SomeFunction();
		String mapTablename=f.getMapTable(mapbegintime);	//单表情况下地图表名
		String[] tableName=ds.dataTableName1(stm);		//完整ZTEST表名数组
		if(!f.judgetablename(mapTablename, tableName)){	//表名校验
			out.println("对应日期没有数据表。重新输入");
		}else{
			if(!ds.judgeCardid(mapcardid, stm, mapTablename)){		//校验卡号
				out.println("当天没有这个卡号，请重新输入");
			}
			else{
				//得到基站号，时间。
				mapBaseMessage mb=ds.mapGetBaseNum(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				mb=f.readeridAndTime(mb.getBaseArray(), mb.getTimeArray());
				for(int i=0;i<mb.getBaseArray().length;i++){
					out.println(mb.getBaseArray()[i]);
					out.println(mb.getTimeArray()[i]);
				}
				ds.closeConnS(stm);
				ds.closeConnC(conn);
			}	
		}	
	}catch(Exception e){
			//事后异常处理
			//e.printStackTrace();
			out.println(e.getMessage());
	}	
%>
</body>
</html>