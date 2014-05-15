package baseInfo;

import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class mapControl extends HttpServlet {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mapcardid=request.getParameter("mapcardid");
		String mapbegintime=request.getParameter("mapbegintime");
		String mapendtime=request.getParameter("mapendtime");
		//使用servlet的前端校验
		if(mapcardid==null||mapcardid==""){
			request.getRequestDispatcher("maperror.jsp").forward(request, response);
		}else if(mapbegintime==null||mapendtime==null||mapbegintime==""||mapendtime==""){
			response.sendRedirect("maperror.jsp");
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("mapcardid", mapcardid);
			session.setAttribute("mapbegintime", mapbegintime);
			session.setAttribute("mapendtime", mapendtime);
			response.sendRedirect("/baseInfo/mapServlet");
			//request.getRequestDispatcher("/mapServlet").forward(request, response);
			}

	}
}
