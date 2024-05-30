package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/hr")
public class HrHomeView extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	final String driver="com.mysql.cj.jdbc.Driver";
	final String url="jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user="root";
	final String password="mysql";
	
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String sql;
			HttpSession session = request.getSession(false);
			String loginName = (String) session.getAttribute("user");
			String loginID = (String) session.getAttribute("userId");

			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			// all tickets
			sql="SELECT "
					+ "t.ticket_id,"
					+ " t.user_pers_id,"
					+ "u.user_name, "
					+ "t.ticket_subject, "
					+ "t.ticket_description, "
					+ "t.ticket_date,t.ticket_technician_id,"
					+ "tc.ticket_cat_id,"
					+ "tc.ticket_cat_name, "
					+ "ts.ticket_status_id,"
					+ " ts.ticket_status,"
					+ "uts.user_name "					
					+ "FROM ticket t "
					+ "LEFT JOIN user_details u ON t.user_pers_id=u.user_pers_id "
					+ "LEFT JOIN ticket_category tc ON t.ticket_cat_id = tc.ticket_cat_id "
					+ "LEFT JOIN ticket_status ts ON t.ticket_status_id = ts.ticket_status_id "
					+ "LEFT JOIN user_details uts ON t.ticket_technician_id = uts.user_pers_id "
					+ "WHERE t.ticket_technician_id ="+loginID;
					
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			out.println("<html><body>");
			out.println("<h3>Welcome  "+loginName+" </h3>");
			out.println("<div style='text-align:right; width:100%'>");
				out.println("<a href='Logout'> Logout</a>");
			out.println("</div>");
			out.println("<div style='width:100%; position:relative; border-bottom: ridge;border-top: ridge;'>");
				out.println("<a href='hr'>Tickets</a>");
			out.println("</div>");
			out.println("<h1> Ticket Details</h1>");
				out.println("<table border='1'>");
					out.println("<tr>");
						out.println("<th>Sl No</th>");						
						out.println("<th>Subject</th>");
						out.println("<th>DESCRIPTION</th>");
						out.println("<th>CATEGORY</th>");
						out.println("<th>TICKET RAISED BY</th>");
						out.println("<th>TICKET DATE</th>");
						out.println("<th>TICKET STATUS</th>");
						out.println("<th>TICKET ALLOTED TO</th>");
						out.println("<th>VIEW TICKET</th>");
												
					out.println("</tr>");
					
					int i=1;
					while(rs.next())	{
						out.println("<tr>");
							out.println("<td>"+i+"</td>");
							out.println("<td>"+rs.getString("ticket_subject")+"</td>");
							out.println("<td>"+rs.getString("ticket_description")+"</td>");
							out.println("<td>"+rs.getString("ticket_cat_name")+"</td>");
							out.println("<td>"+rs.getString("user_name")+"</td>");
							out.println("<td>"+rs.getString("ticket_date")+"</td>");
							out.println("<td>"+rs.getString("ticket_status")+"</td>");	
							out.println("<td>");
							if(rs.getString("uts.user_name")!=null){
								out.println(rs.getString("uts.user_name"));
							}
							out.println("</td>");	
							out.println("<td>");
							
							out.println(" <a href='viewticket?eid="+ rs.getInt("ticket_id")+"'>Process Ticket</a>");
							
							out.println("</td>");
						out.println("</tr>");
						i++;
					}
				out.println("</table>");
			out.println("</body></html>");
				
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

