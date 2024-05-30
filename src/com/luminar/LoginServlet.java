package com.luminar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	String userName = "";
	String userRole = "";
	String userId = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			String uname = request.getParameter("uname").trim();
			String upass = request.getParameter("upass").trim();
			boolean loginSuccess = false;

			
			String sql = "SELECT user_pers_id, user_name, user_role FROM user_details where user_login_username='"
					+ uname + "' and user_login_password='" + upass + "'";

		
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			System.out.println(rs.getFetchSize());

			
			while (rs.next()) {
				userId = rs.getString(1);
				userName = rs.getString(2);
				userRole = rs.getString(3);
				loginSuccess = true;
			}
			if (loginSuccess) {
				HttpSession session = request.getSession();
				session.setAttribute("user", userName);
				session.setAttribute("userId", userId);
				session.setAttribute("role", userRole);

				if (userRole.equals("admin")) {

					response.sendRedirect("admin");

				} else if (userRole.equals("hr")) {

					response.sendRedirect("hr");

				} else if (userRole.equals("employee")) {

					response.sendRedirect("employee");

				}
			} else {
				response.sendRedirect("index.html");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
}
