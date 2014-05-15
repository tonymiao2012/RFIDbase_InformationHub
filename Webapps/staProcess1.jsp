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
//传入参数
String begintimes=(String)session.getAttribute("begintimes");
String endtimes=(String)session.getAttribute("endtimes");
//声明全局变量
SomeFunction f=new SomeFunction();		//功能函数集
String[] sql=new String[3];		//要执行的SQL语句
String s="";	//要保存单位信息
String[] unit=new String[2];
int m=0;		//保存基站返回的KB和
try{
	//oracle连接部分
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@10.161.3.190:1521:gatherdata";
	String username="datauser";
	String password="a123";
	database ds=new database(driver,url,username,password);	
	Connection conn=ds.getConnection();
	Statement stm=ds.getStatement(conn);
	//得到表名数组，日期数组
	String[] bt=new String[3];
	String[] et=new String[3];
	String[] dateA=new String[3];
	String[] tables=new String[3];
	bt=f.getDateArray(begintimes);
	et=f.getDateArray(endtimes);
	dateA=f.dateRange(bt,et);		//得到日期数组
	tables=f.getTableTrimS(dateA);	//得到表名数组	
	String[] tableName=ds.dataTableName(stm);     //完整READERFLOW表名数组
	//int[] baseRange=ds.getBaseNumAll(dateA,stm,begintimes,endtimes,tables);//表名判断部分，新加入内容，放在这
	//的异常会直接被catch抓住，不会进入判断
	int[] tag=f.judgeTables(tables,tableName);	//得到标记数组。来标记为空表
		for(int i=0;i<tag.length;i++){
			if(tag[i]==0){		//转到空表处理页面
			session.setAttribute("tag",tag);
			session.setAttribute("dateA",dateA);
			response.sendRedirect("noTableFound.jsp");
		}
	}
	int[] baseRange=ds.getBaseNumAll(dateA,stm,begintimes,endtimes,tables);		//得到时段内所有基站范围数组
	//没有空表情况
	out.println("<center>");
	out.println("时间段内基站统计数据：");
	out.println("<br>");
	for(int i=0;i<baseRange.length;i++){
		out.println("基站"+baseRange[i]+" 的流量:");
		//得到目标多表中的流量和，保存在m
		sql=f.getStaSql(baseRange[i],tables,begintimes,endtimes,dateA);
		if(sql.length==1){
			m=ds.processSqlS(sql[0],stm);
		}else if(sql.length==2){
			m=ds.processSqlS(sql[0],stm)+ds.processSqlS(sql[1],stm);
		}else{
			m=ds.processSqlS(sql[0],stm)+ds.processSqlS(sql[1],stm)+ds.processSqlS(sql[2],stm);
		}
		//加入单位换算部分，1MB=1024KB 1GB=1024MB
		Double d=new Double((double)m);
		s=f.unitAdd(m);
		unit=s.split(" ");
		java.text.DecimalFormat df=new java.text.DecimalFormat( "#.## "); 		//定义一种格式。保留两位小数
		d=Double.parseDouble(unit[0]);		//将string类型转为double 
		out.println("<font color=#FF0000>");
		out.println(df.format(d));
		out.println("</font>");
		out.println(unit[1]);
		out.println("<br>");
		m=0;	//清零
	}
	out.println("</center>");
	ds.closeConnS(stm);
	ds.closeConnC(conn);

}
catch (Exception e) { 		//事后校验捕获异常
    //e.printStackTrace(); 
    out.println(e.getMessage());
    //response.sendRedirect("noTableFound1.jsp");
} 
 %>
</body>
</html>