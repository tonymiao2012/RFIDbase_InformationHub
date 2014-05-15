package baseInfo;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class mapServlet extends HttpServlet {
	 /**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=GB2312");		//��Ӧ��ʽ�Լ�����
		request.setCharacterEncoding("gb2312");		//ҳ�淢�͵��������
		PrintWriter out = response.getWriter();		//����writer
		//ʹ��ʲô������ͻ��˷������Ķ���������,��Ҫ��HTML���뱣��һ�¡���TOMCAT�У������get���������������ݣ����ܲ������á�
		HttpSession session=request.getSession();
		String cardid=(String)session.getAttribute("mapcardid");
		String mapbegintime=(String)session.getAttribute("mapbegintime");
		String mapendtime=(String)session.getAttribute("mapendtime");
		long mapcardid=Long.parseLong(cardid);		//��Ϊ������
		//�������ݴ������
		//out.println(mapcardid);
		//out.println(mapbegintime);
		//out.println(mapendtime);
		//out.flush();
		//out.close();
		
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
				//out.println(mapTablename); //�������Գɹ�
				double[] xarray=ds.mapGetxArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				double[] yarray=ds.mapGetyArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				int[] hourArray=ds.mapGetHourArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				int[] minArray=ds.mapGetMinArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				int[] secArray=ds.mapGetSecArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				/*���Դ�������ꡣ�ɹ�
				for(int i=0;i<secArray.length;i++)
					out.println(secArray[i]);
				 */
				mapMessage mm=f.mapStay(hourArray, minArray, secArray, xarray, yarray);
				int[] result=mm.getMapStay();
				//double[] result=f.mapShowTime(temp);
				//���Խ������
				/*
				for(int i=0;i<result.length;i++)
				out.println(result[i]);
				 */
				mapBaseMessage mb=ds.mapGetBaseNum(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				mb=f.readeridAndTime(mb.getBaseArray(), mb.getTimeArray());
				//������������
				session.setAttribute("getX", mm.getXarray());
				session.setAttribute("getY", mm.getYarray());
				session.setAttribute("result", result);
				session.setAttribute("baseNumber", mb.getBaseArray());
				session.setAttribute("timeArray", mb.getTimeArray());
				response.sendRedirect("mapLogin.jsp");
				ds.closeConnS(stm);
				ds.closeConnC(conn);
			}	
		}
		}catch(Exception e){
			//�º��쳣����
			//e.printStackTrace();
			out.println(e.getMessage());
		}

	}

}
