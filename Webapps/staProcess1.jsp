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
//�������
String begintimes=(String)session.getAttribute("begintimes");
String endtimes=(String)session.getAttribute("endtimes");
//����ȫ�ֱ���
SomeFunction f=new SomeFunction();		//���ܺ�����
String[] sql=new String[3];		//Ҫִ�е�SQL���
String s="";	//Ҫ���浥λ��Ϣ
String[] unit=new String[2];
int m=0;		//�����վ���ص�KB��
try{
	//oracle���Ӳ���
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@10.161.3.190:1521:gatherdata";
	String username="datauser";
	String password="a123";
	database ds=new database(driver,url,username,password);	
	Connection conn=ds.getConnection();
	Statement stm=ds.getStatement(conn);
	//�õ��������飬��������
	String[] bt=new String[3];
	String[] et=new String[3];
	String[] dateA=new String[3];
	String[] tables=new String[3];
	bt=f.getDateArray(begintimes);
	et=f.getDateArray(endtimes);
	dateA=f.dateRange(bt,et);		//�õ���������
	tables=f.getTableTrimS(dateA);	//�õ���������	
	String[] tableName=ds.dataTableName(stm);     //����READERFLOW��������
	//int[] baseRange=ds.getBaseNumAll(dateA,stm,begintimes,endtimes,tables);//�����жϲ��֣��¼������ݣ�������
	//���쳣��ֱ�ӱ�catchץס����������ж�
	int[] tag=f.judgeTables(tables,tableName);	//�õ�������顣�����Ϊ�ձ�
		for(int i=0;i<tag.length;i++){
			if(tag[i]==0){		//ת���ձ���ҳ��
			session.setAttribute("tag",tag);
			session.setAttribute("dateA",dateA);
			response.sendRedirect("noTableFound.jsp");
		}
	}
	int[] baseRange=ds.getBaseNumAll(dateA,stm,begintimes,endtimes,tables);		//�õ�ʱ�������л�վ��Χ����
	//û�пձ����
	out.println("<center>");
	out.println("ʱ����ڻ�վͳ�����ݣ�");
	out.println("<br>");
	for(int i=0;i<baseRange.length;i++){
		out.println("��վ"+baseRange[i]+" ������:");
		//�õ�Ŀ�����е������ͣ�������m
		sql=f.getStaSql(baseRange[i],tables,begintimes,endtimes,dateA);
		if(sql.length==1){
			m=ds.processSqlS(sql[0],stm);
		}else if(sql.length==2){
			m=ds.processSqlS(sql[0],stm)+ds.processSqlS(sql[1],stm);
		}else{
			m=ds.processSqlS(sql[0],stm)+ds.processSqlS(sql[1],stm)+ds.processSqlS(sql[2],stm);
		}
		//���뵥λ���㲿�֣�1MB=1024KB 1GB=1024MB
		Double d=new Double((double)m);
		s=f.unitAdd(m);
		unit=s.split(" ");
		java.text.DecimalFormat df=new java.text.DecimalFormat( "#.## "); 		//����һ�ָ�ʽ��������λС��
		d=Double.parseDouble(unit[0]);		//��string����תΪdouble 
		out.println("<font color=#FF0000>");
		out.println(df.format(d));
		out.println("</font>");
		out.println(unit[1]);
		out.println("<br>");
		m=0;	//����
	}
	out.println("</center>");
	ds.closeConnS(stm);
	ds.closeConnC(conn);

}
catch (Exception e) { 		//�º�У�鲶���쳣
    //e.printStackTrace(); 
    out.println(e.getMessage());
    //response.sendRedirect("noTableFound1.jsp");
} 
 %>
</body>
</html>