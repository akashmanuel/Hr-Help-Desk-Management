package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			session.invalidate();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.println("<html><body>");
			out.println("<div style=' width:100%;padding: 25px 50px 75px 100px;' >");
				out.print("You are successfully logged out!");			
				
				out.println("<a href='index.html'>Click here</a> to login again");
			out.println("</div>");
			out.close();
			out.println("</body></html>");
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
