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
	String cardid=request.getParameter("mapcardid");	//���մ���������ɹ�
	String mapbegintime=request.getParameter("mapbegintime");
	String mapendtime=request.getParameter("mapendtime");
	long mapcardid=Long.parseLong(cardid);		//��Ϊ������
	try{
		//���ݿ����Ӳ���
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@10.161.3.190:1521:gatherdata";
		String username="datauser";
		String password="a123";
		//��������
		database ds=new database(driver,url,username,password);
		Connection conn=ds.getConnection();
		Statement stm=ds.getStatement(conn);
		//�õ����ñ���
		SomeFunction f=new SomeFunction();
		String mapTablename=f.getMapTable(mapbegintime);	//��������µ�ͼ����
		String[] tableName=ds.dataTableName1(stm);		//����ZTEST��������
		if(!f.judgetablename(mapTablename, tableName)){	//����У��
			out.println("��Ӧ����û�����ݱ���������");
		}else{
			if(!ds.judgeCardid(mapcardid, stm, mapTablename)){		//У�鿨��
				out.println("����û��������ţ�����������");
			}
			else{
				//�õ���վ�ţ�ʱ�䡣
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
			//�º��쳣����
			//e.printStackTrace();
			out.println(e.getMessage());
	}	
%>
</body>
</html>