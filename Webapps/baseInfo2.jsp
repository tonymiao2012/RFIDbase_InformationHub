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
String number=(String)session.getAttribute("number");		//��ʼ����Ϣ����
String begintime=(String)session.getAttribute("begintime");
String endtime=(String)session.getAttribute("endtime");
String range1=(String)session.getAttribute("range1");
String range2=(String)session.getAttribute("range2");

int r1=Integer.parseInt(range1);		//��string����תΪint
int r2=Integer.parseInt(range2);
//����ȫ�ֱ���
int m=0;		//�õ�����ȫ�ֱ���
int r=0;		//��Χ����
int n=0;
SomeFunction f=new SomeFunction();
String sqla="";
String sqlb="";
//���Դ��ݲ���,�ɹ�
//out.println(range1);
//out.println(range2);
//��Χ2¼�뷶Χ����

r=f.getRangeM(r1, r2).length;		//ģʽ2�ķ�Χ��
int[] cardidScope2=new int[r];			
cardidScope2=f.getRangeM(r1,r2).clone();	//ģʽ2�ķ�Χ���飬���Եõ��������

//��ǩ����
int[] tag=new int[r];		
//�õ�ƴ���ַ��������ڶ�SQL���ʹ��
String range=new String(f.getString2(cardidScope2));
//����ƴ���ַ�������Ҫ(�ɹ�)
//out.println(range);
try{
	//���ݿ�����
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@10.161.3.190:1521:gatherdata";
	String username="datauser";
	String password="a123";
	database ds=new database(driver,url,username,password);	
	Connection conn=ds.getConnection();
	Statement stm=ds.getStatement(conn);
	//String s=f.getTable(begintime);		//�õ�ָ����
	//������֣����֧��3���ѯ
	String[] bt=new String[3];
	String[] et=new String[3];
	String[] dateA=new String[3];
	String[] dateT=new String[3];
	bt=f.getDateArray(begintime).clone();
	et=f.getDateArray(endtime).clone();
	dateA=f.dateRange(bt,et).clone();		//�õ�������ʽ�ĸ�������ʽΪYYYY-MM-DD
	dateT=f.getTableTrim(dateA).clone();	//�õ���������	
	String[] tableName=ds.dataTableName1(stm);		//����ZTEST��������
	int[] tagTable=f.judgeTables(dateT,tableName);	//�õ�������顣���ж��Ƿ�Ϊ�ձ�
	for(int i=0;i<tagTable.length;i++){	
		if(tagTable[i]==0){		//ת���ձ���ҳ��
			session.setAttribute("tagTable",tagTable);
			session.setAttribute("dateA",dateA);
			response.sendRedirect("noTableZtest.jsp");
		}
	}
	//û�пձ����
	//��Ƶ1�յ�����������
	sqla=f.sqlPlus1(dateA,dateT,number,range,begintime,endtime);
	String sql1=sqla+" ORDER BY cardid";
	m=ds.processSql(sql1, stm);		//m����õ���Χ��r�����ܷ�Χ
	int[] cardid1=new int[m];
	cardid1=ds.process(sql1, stm, r).clone();
	out.println("<p align=left>");
	out.println("�û�վ��ʱ�������Ƶ1�յ��Ŀ���:");
	out.println("<font color=#FF0000>");
	out.println(m);
	out.println("</font>");
	out.println("&nbsp");
	out.println("����:");
	for(int i=0;i<m;i++)
		out.println(cardid1[i]);
	out.println("<br>");
	//��Ƶ1δ�յ�����
	tag=f.tagIt(cardid1, m, cardidScope2, r).clone();
	out.println("�û�վ��ʱ�������Ƶ1δ�յ�����:");
	int o=0;
	for(int i=0;i<r;i++){		//�������δ�յ�����
		if(tag[i]==0)
			o++;
	}
	out.println("<font color=#FF0000>");
	out.println(o);
	out.println("</font>");
	out.println("&nbsp");
	out.println("����:");
	for(int i=0;i<r;i++){		//�������δ�յ�����
		if(tag[i]==0)
			out.println(cardidScope2[i]);
	}
	out.println("</p>");
	//��Ƶ2�յ�����������
	sqlb=f.sqlPlus2(dateA,dateT,number,range,begintime,endtime);
	String sql2=sqlb+" ORDER BY cardid";
	m=ds.processSql(sql2, stm);
	int[] cardid2=new int[m];		//��Ƶ2�յ����ű��浽cardid2[]��
	cardid2=ds.process(sql2, stm, r).clone();
	out.println("<p align=left>");
	out.println("�û�վ��ʱ�������Ƶ2�յ��Ŀ���:");
	out.println("<font color=#FF0000>");
	out.println(m);
	out.println("</font>");
	out.println("&nbsp");
	out.println("����:");
	for(int i=0;i<m;i++)
		out.println(cardid2[i]);
	out.println("<br>");
	//��Ƶ2δ�յ�����
	n=f.tagIt(cardid2, m, cardidScope2, r).length;
	tag=f.tagIt(cardid2, m, cardidScope2, r).clone();
	out.println("�û�վ��ʱ�������Ƶ2δ�յ�����:");
	o=0;
	for(int i=0;i<r;i++){		//�������δ�յ�����
		if(tag[i]==0)
			o++;
	}
	out.println("<font color=#FF0000>");
	out.println(o);
	out.println("</font>");
	out.println("&nbsp");
	out.println("����:");
	for(int i=0;i<r;i++){		//�������δ�յ�����
		if(tag[i]==0)
			out.println(cardidScope2[i]);
	}
	out.println("</p>");
	//��Ƶ1��2�յ����ſ���
	sqla=f.sqlPlusAll(sqla,sqlb);
	sqla+=" ORDER BY cardid";
	String sql3=sqla;
	m=ds.processSql(sql3, stm);
	int[] cardid3=new int[m];
	cardid3=ds.process(sql3, stm, r).clone();
	out.println("<p align=left>");
	out.println("�û�վ��ʱ�������Ƶ1����Ƶ2�յ��Ŀ���:");
	out.println("<font color=#FF0000>");
	out.println(m);
	out.println("</font>");
	out.println("&nbsp");
	out.println("����:");
	for(int i=0;i<m;i++)
		out.println(cardid3[i]);
	out.println("<br>");
	//��Ƶ1��2δ�յ�����
	n=f.tagIt(cardid3, m, cardidScope2, r).length;
	tag=f.tagIt(cardid3, m, cardidScope2, r).clone();
	out.println("�û�վ��ʱ�������Ƶ1����Ƶ2δ�յ�����:");
	o=0;
	for(int i=0;i<r;i++){		//�������δ�յ�����
		if(tag[i]==0)
			o++;
	}
	out.println("<font color=#FF0000>");
	out.println(o);
	out.println("</font>");
	out.println("&nbsp");
	out.println("����:");
	for(int i=0;i<r;i++){		//�������δ�յ�����
		if(tag[i]==0)
			out.println(cardidScope2[i]);
	}
	out.println("</p>");
	ds.closeConnS(stm);
	ds.closeConnC(conn);
	
}catch (Exception e) { 		//�º�У�鲶���쳣
    //e.printStackTrace(); 
    out.println(e.getMessage());
} 

%>
</body>
</html>