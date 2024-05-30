package com.luminar;

import java.io.IOException;
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

@WebServlet("/addResolution")
public class HrResolutionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String sql;

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			
			String empID = (String) session.getAttribute("userId");			
			String ticketId = request.getParameter("currentticketID");
			String resolution = request.getParameter("tresolution").trim();
			String ticketStatusId = request.getParameter("tstatus");
			
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String todayString = today.format(formatter);
	        
			
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
					
			
					
			sql="INSERT INTO ticket_resolution (ticket_id, user_pers_id, ticket_resolution_date, ticket_status_id, ticket_resolution_description) values("
					+ticketId
					+","
					+empID
					+",'"
					+todayString
					+ "',"
					+ticketStatusId
					+ ",'"
					+resolution										
					+ "')" ;
			
			
			pst=con.prepareStatement(sql);
			pst.executeUpdate();
			
			sql="UPDATE ticket set ticket_status_id="+ticketStatusId +" where ticket_id="+ticketStatusId;									
			pst=con.prepareStatement(sql);			
			System.out.println(sql);	        
			pst.executeUpdate();
			
			response.sendRedirect("viewticket?eid="+ticketId);
			

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
