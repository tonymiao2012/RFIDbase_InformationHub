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
//用户信息读入
String numbers=(String)session.getAttribute("numbers");
String begintimes=(String)session.getAttribute("begintimes");
String endtimes=(String)session.getAttribute("endtimes");
//测试传值参数，成功

//声明全局变量
SomeFunction f=new SomeFunction();
String[] sql=new String[3];		//要执行的SQL语句
int n=0;			//基站数组长度
int m=0;			//保存基站返回的KB和
String s="";	//要保存单位信息
String[] unit=new String[2];
unit[0]="0";
unit[1]="B";
//得到基站数组
n=f.getBaseNumber(numbers).length;
int[] baseNum=new int[n];
baseNum=f.getBaseNumber(numbers);
//for(int i=0;i<baseNum.length;i++)
	//out.println(baseNum[i]);

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
	String[] tableName=ds.dataTableName(stm);//完整READERFLOW表名数组
	//bug调试部分
	//for(int i=0;i<3;i++){
		//System.out.println(bt[i]);
		//System.out.println(et[i]);
		//out.println(dateA[i]);
		//out.println(tables[i]);
		//}
	//for(int p=0;p<tableName.length;p++)
		//out.println(tableName[p]);
	//表名判断部分，新加入内容
	int[] tag=f.judgeTables(tables,tableName);	//得到标记数组。来判断是否为空表
	for(int i=0;i<tag.length;i++){	
		if(tag[i]==0){		//转到空表处理页面
			session.setAttribute("tag",tag);
			session.setAttribute("dateA",dateA);
			response.sendRedirect("noTableFound.jsp");
		}
	}

	//没用空表，直接输出结果
	out.println("<p align=center>");
	out.println("时间段内基站统计数据：");
	out.println("<br>");
	for(int i=0;i<baseNum.length;i++){
		out.println("基站"+baseNum[i]+" 的流量:");
		//得到目标多表中的流量和，保存在m
		sql=f.getStaSql(baseNum[i],tables,begintimes,endtimes,dateA);
		if(sql.length==1){
			m=ds.processSqlS(sql[0],stm);
		}else if(sql.length==2){
			m=ds.processSqlS(sql[0],stm)+ds.processSqlS(sql[1],stm);
		}else{
			m=ds.processSqlS(sql[0],stm)+ds.processSqlS(sql[1],stm)+ds.processSqlS(sql[2],stm);
		}
		Double d=new Double((double)m);
		s=f.unitAdd(d);
		unit=s.split(" ");
		java.text.DecimalFormat df=new java.text.DecimalFormat( "#.## "); 		//定义一种格式。保留两位小数
		d=Double.parseDouble(unit[0]);		//将string类型转为double 
		out.println("<font color=#FF0000>");
		out.println(df.format(d));
		out.println("</font>");
		out.println(unit[1]);
		out.println("<br>");
		m=0;	//清零
		s="";
	}
	out.println("</p>");
	ds.closeConnS(stm);
	ds.closeConnC(conn);

}
catch (Exception e) { 		//事后校验捕获异常
    //e.printStackTrace(); 
    out.println(e.getMessage());
    //response.sendRedirect("error3.jsp");
}

 %>
</body>
</html>