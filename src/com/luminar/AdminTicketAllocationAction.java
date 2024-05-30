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

@WebServlet("/allocateticket")
public class AdminTicketAllocationAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String sql;
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			String adminID = (String)session.getAttribute("userId");
			
			int ticketID =Integer.parseInt(request.getParameter("ticketId")) ;
			int technicianUserID = Integer.parseInt(request.getParameter("role"));
			
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String todayString = today.format(formatter);

			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
					
							
			
			sql="UPDATE ticket set ticket_status_id=2, ticket_technician_id="+technicianUserID+" , ticket_alloted_date='"+todayString+"' where ticket_id="+ticketID;									
			pst=con.prepareStatement(sql);		
			pst.executeUpdate();
						
			response.sendRedirect("admin");

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

