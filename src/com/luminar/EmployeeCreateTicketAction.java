package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/employeecreateticket")
public class EmployeeCreateTicketAction  extends HttpServlet{

		
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
		
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		try{
					
						
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			String userId = (String) session.getAttribute("userId");
			
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String todayString = today.format(formatter);
	        
			String tsubject = request.getParameter("tsubject").trim();
			String tdescription = request.getParameter("tdescription").trim();
			int tcategory = Integer.parseInt(request.getParameter("tcategory"));
			
			
			
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			String sql="INSERT INTO ticket(user_pers_id, ticket_subject, ticket_description, ticket_date, ticket_cat_id, ticket_status_id) values("
					+"'"
					+userId
					+"','"	
					+tsubject
					+ "','"
					+tdescription
					+ "','"
					+todayString
					+ "',"
					+tcategory
					+ ","
					+"1"
					+ ")" ;
			
			
			pst=con.prepareStatement(sql);
			pst.executeUpdate();
			
			response.sendRedirect("employee");
				
				
		}catch (Exception e) {
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
