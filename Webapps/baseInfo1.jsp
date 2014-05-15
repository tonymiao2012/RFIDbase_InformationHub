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
<%@include file="login.jsp" %>
<%
String number=(String)session.getAttribute("number");		//初始化信息读入
String begintime=(String)session.getAttribute("begintime");
String endtime=(String)session.getAttribute("endtime");
String range=(String)session.getAttribute("range");		//读入一个范围
//测试传递参数,成功
//out.println(range);
//范围类型1.rage字符串切割,定义新的cardidScope数组
String[] temp=range.split(",");
int r=temp.length;		//定义一个保存范围数字的变量。
int[] cardidScope=new int[temp.length];		//卡号范围数组
for(int i=0;i<temp.length;i++){
	try{
		cardidScope[i] = Integer.parseInt(temp[i].trim());
		}catch (NumberFormatException nbFmtExp){
			cardidScope[i]=0;
			}
	}
	
//全局变量声明
int n=0;
int m=0;		//用于保存得到卡数
int[] tag=new int[r];
SomeFunction f=new SomeFunction();
String sqla="";
String sqlb="";
//数据库建立链接
try{
	//oracle连接部分
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@10.161.3.190:1521:gatherdata";
	String username="datauser";
	String password="a123";
	database ds=new database(driver,url,username,password);	
	Connection conn=ds.getConnection();
	Statement stm=ds.getStatement(conn);
	//射频1收到卡数，卡号
	//单表情况
	//String s=f.getTable(begintime);	
	//多表情况,最多支持三表查询。
	String[] bt=new String[3];
	String[] et=new String[3];
	String[] dateA=new String[3];
	String[] dateT=new String[3];
	bt=f.getDateArray(begintime).clone();
	et=f.getDateArray(endtime).clone();
	dateA=f.dateRange(bt,et).clone();		//得到日期形式的副本。格式为YYYY-MM-DD
	dateT=f.getTableTrim(dateA).clone();	//得到表名数组	
	String[] tableName=ds.dataTableName1(stm);		//完整ZTEST表名数组
	/*测试函数,能够得到表名和日期数组
	out.println(dateA.length);
	out.println(dateT.length);
	for(int i=0;i<dateT.length;i++)
		out.println(dateT[i]);
	for(int i=0;i<dateA.length;i++)
		out.println(dateA[i]);
	*/
	//测试总表明数组,成功
	//for(int i=0;i<tableName.length;i++)
		//out.println(tableName[i]);
	
	int[] tagTable=f.judgeTables(dateT,tableName);	//得到标记数组。来判断是否为空表
	for(int i=0;i<tagTable.length;i++){	
		if(tagTable[i]==0){		//转到空表处理页面
			session.setAttribute("tagTable",tagTable);
			session.setAttribute("dateA",dateA);
			response.sendRedirect("noTableZtest.jsp");
		}
	}
	//没用空表情况，继续处理结果
	sqla=f.sqlPlus1(dateA,dateT,number,range,begintime,endtime);
	String sql1=sqla+" ORDER BY cardid";
	m=ds.processSql(sql1, stm);		//m保存得到范围，r保存总范围
	int[] cardid1=new int[m];
	//将这个数组拷贝出来。保存到cardid1[]中
	cardid1=ds.process(sql1, stm, r).clone();
	out.println("<p align=left>");
	out.println("该基站在时间段内射频1收到的卡数:");
	out.println("<font color=#FF0000>");
	out.println(m);
	out.println("</font>");
	out.println("&nbsp");
	out.println("卡号:");
	for(int i=0;i<m;i++)
		out.println(cardid1[i]);
	out.println("<br>");
	//射频1未收到部分
	tag=f.tagIt(cardid1, m, cardidScope, r).clone();
	out.println("该基站在时间段内射频1未收到卡数:");
	int o=0;
	for(int i=0;i<r;i++){		//依次输出未收到卡数
		if(tag[i]==0)
			o++;
	}
	out.println("<font color=#FF0000>");
	out.println(o);
	out.println("</font>");
	out.println("&nbsp");
	out.println("卡号:");
	for(int i=0;i<r;i++){		//依次输出未收到卡号
		if(tag[i]==0)
			out.println(cardidScope[i]);
	}
	out.println("</p>");
	//射频2收到卡数，卡号
	sqlb=f.sqlPlus2(dateA,dateT,number,range,begintime,endtime);
	String sql2=sqlb+" ORDER BY cardid";
	m=ds.processSql(sql2, stm);
	int[] cardid2=new int[m];		//射频2收到卡号保存到cardid2[]中
	cardid2=ds.process(sql2, stm, r).clone();
	out.println("<p align=left>");
	out.println("该基站在时间段内射频2收到的卡数:");
	out.println("<font color=#FF0000>");
	out.println(m);
	out.println("</font>");
	out.println("&nbsp");
	out.println("卡号:");
	for(int i=0;i<m;i++)
		out.println(cardid2[i]);
	out.println("<br>");
	//射频2未收到部分
	n=f.tagIt(cardid2, m, cardidScope, r).length;
	tag=f.tagIt(cardid2, m, cardidScope, r).clone();
	out.println("该基站在时间段内射频2未收到卡数:");
	o=0;
	for(int i=0;i<r;i++){		//依次输出未收到卡数
		if(tag[i]==0)
			o++;
	}
	out.println("<font color=#FF0000>");
	out.println(o);
	out.println("</font>");
	out.println("&nbsp");
	out.println("卡号:");
	for(int i=0;i<r;i++){		//依次输出未收到卡号
		if(tag[i]==0)
			out.println(cardidScope[i]);
	}
	out.println("</p>");
	//射频1与2收到卡号卡数
	sqla=f.sqlPlusAll(sqla,sqlb);
	sqla+=" ORDER BY cardid";
	String sql3=sqla;
	m=ds.processSql(sql3, stm);
	int[] cardid3=new int[m];
	cardid3=ds.process(sql3, stm, r).clone();
	out.println("<p align=left>");
	out.println("该基站在时间段内射频1和射频2收到的卡数:");
		out.println("<font color=#FF0000>");
	out.println(m);
	out.println("</font>");
	out.println("&nbsp");
	out.println("卡号:");
	for(int i=0;i<m;i++)
		out.println(cardid3[i]);
	out.println("<br>");
	//射频1和2未收到部分
	n=f.tagIt(cardid3, m, cardidScope, r).length;
	tag=f.tagIt(cardid3, m, cardidScope, r).clone();
	out.println("该基站在时间段内射频1和射频2未收到卡数:");
	o=0;
	for(int i=0;i<r;i++){		//依次输出未收到卡数
		if(tag[i]==0){
			o++;
		}
	}
	out.println("<font color=#FF0000>");
	out.println(o);
	out.println("</font>");
	out.println("&nbsp");
	out.println("卡号:");
	for(int i=0;i<r;i++){		//依次输出未收到卡号
		if(tag[i]==0){
			out.println(cardidScope[i]);
		}
	}
	out.println("</p>");
	ds.closeConnS(stm);
	ds.closeConnC(conn);

}catch (Exception e) { 		//事后校验捕获异常
    //e.printStackTrace(); 
    out.println(e.getMessage());
} 

%>
</body>
</html>