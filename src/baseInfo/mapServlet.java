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

		response.setContentType("text/html;charset=GB2312");		//响应格式以及编码
		request.setCharacterEncoding("gb2312");		//页面发送的请求编码
		PrintWriter out = response.getWriter();		//声明writer
		//使用什么编码读客户端发过来的二进制数据,需要与HTML编码保持一致。在TOMCAT中，如果是get方法传过来的数据，可能不起作用。
		HttpSession session=request.getSession();
		String cardid=(String)session.getAttribute("mapcardid");
		String mapbegintime=(String)session.getAttribute("mapbegintime");
		String mapendtime=(String)session.getAttribute("mapendtime");
		long mapcardid=Long.parseLong(cardid);		//换为长整型
		//测试数据传入测试
		//out.println(mapcardid);
		//out.println(mapbegintime);
		//out.println(mapendtime);
		//out.flush();
		//out.close();
		
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
				//out.println(mapTablename); //报名测试成功
				double[] xarray=ds.mapGetxArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				double[] yarray=ds.mapGetyArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				int[] hourArray=ds.mapGetHourArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				int[] minArray=ds.mapGetMinArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				int[] secArray=ds.mapGetSecArray(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				/*测试传入的坐标。成功
				for(int i=0;i<secArray.length;i++)
					out.println(secArray[i]);
				 */
				mapMessage mm=f.mapStay(hourArray, minArray, secArray, xarray, yarray);
				int[] result=mm.getMapStay();
				//double[] result=f.mapShowTime(temp);
				//测试结果数组
				/*
				for(int i=0;i<result.length;i++)
				out.println(result[i]);
				 */
				mapBaseMessage mb=ds.mapGetBaseNum(stm, mapbegintime, mapendtime, mapTablename, mapcardid);
				mb=f.readeridAndTime(mb.getBaseArray(), mb.getTimeArray());
				//返回这个结果集
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
			//事后异常处理
			//e.printStackTrace();
			out.println(e.getMessage());
		}

	}

}
