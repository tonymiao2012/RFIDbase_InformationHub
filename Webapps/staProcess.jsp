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
//�û���Ϣ����
String numbers=(String)session.getAttribute("numbers");
String begintimes=(String)session.getAttribute("begintimes");
String endtimes=(String)session.getAttribute("endtimes");
//���Դ�ֵ�������ɹ�

//����ȫ�ֱ���
SomeFunction f=new SomeFunction();
String[] sql=new String[3];		//Ҫִ�е�SQL���
int n=0;			//��վ���鳤��
int m=0;			//�����վ���ص�KB��
String s="";	//Ҫ���浥λ��Ϣ
String[] unit=new String[2];
unit[0]="0";
unit[1]="B";
//�õ���վ����
n=f.getBaseNumber(numbers).length;
int[] baseNum=new int[n];
baseNum=f.getBaseNumber(numbers);
//for(int i=0;i<baseNum.length;i++)
	//out.println(baseNum[i]);

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
	String[] tableName=ds.dataTableName(stm);//����READERFLOW��������
	//bug���Բ���
	//for(int i=0;i<3;i++){
		//System.out.println(bt[i]);
		//System.out.println(et[i]);
		//out.println(dateA[i]);
		//out.println(tables[i]);
		//}
	//for(int p=0;p<tableName.length;p++)
		//out.println(tableName[p]);
	//�����жϲ��֣��¼�������
	int[] tag=f.judgeTables(tables,tableName);	//�õ�������顣���ж��Ƿ�Ϊ�ձ�
	for(int i=0;i<tag.length;i++){	
		if(tag[i]==0){		//ת���ձ���ҳ��
			session.setAttribute("tag",tag);
			session.setAttribute("dateA",dateA);
			response.sendRedirect("noTableFound.jsp");
		}
	}

	//û�ÿձ�ֱ��������
	out.println("<p align=center>");
	out.println("ʱ����ڻ�վͳ�����ݣ�");
	out.println("<br>");
	for(int i=0;i<baseNum.length;i++){
		out.println("��վ"+baseNum[i]+" ������:");
		//�õ�Ŀ�����е������ͣ�������m
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
		java.text.DecimalFormat df=new java.text.DecimalFormat( "#.## "); 		//����һ�ָ�ʽ��������λС��
		d=Double.parseDouble(unit[0]);		//��string����תΪdouble 
		out.println("<font color=#FF0000>");
		out.println(df.format(d));
		out.println("</font>");
		out.println(unit[1]);
		out.println("<br>");
		m=0;	//����
		s="";
	}
	out.println("</p>");
	ds.closeConnS(stm);
	ds.closeConnC(conn);

}
catch (Exception e) { 		//�º�У�鲶���쳣
    //e.printStackTrace(); 
    out.println(e.getMessage());
    //response.sendRedirect("error3.jsp");
}

 %>
</body>
</html>