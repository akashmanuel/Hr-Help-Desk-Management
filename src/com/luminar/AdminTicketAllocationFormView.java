package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class User {
    private int userId;
    private String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}

@WebServlet("/allocateticketform")
public class AdminTicketAllocationFormView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String sql="";
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			String adminID = (String) session.getAttribute("userId");
			
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			int ticketId=Integer.parseInt(request.getParameter("eid"));
			
			
			sql="SELECT user_pers_id,user_name,user_login_username,user_email,user_role,user_dept FROM user_details where user_role='hr' ";
			
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			
			
			List<User> userList = new ArrayList<>();
            while (rs.next()) {
                int userId = rs.getInt("user_pers_id");
                String userName = rs.getString("user_name");
                userList.add(new User(userId, userName));
            }
            
         // all tickets
         			sql="SELECT "
         					+ "t.ticket_id,"
         					+ " t.user_pers_id,"
         					+ "u.user_name, "
         					+ "u.user_contact_no, "
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
         					+ "WHERE t.ticket_id ="+ticketId;
         					
         	pst=con.prepareStatement(sql);
         	rs=pst.executeQuery();
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			
						
			out.println("<html><body>");
			out.println("<h3>Welcome  "+name+" </h1>");
			out.println("<div style='text-align:right; width:100%'>");
				out.println("<a href='Logout'> Logout</a>");
			out.println("</div>");
			out.println("<div style='width:100%; position:relative; border-bottom: ridge;border-top: ridge;'>");
				out.println("<a href='admin'>Tickets</a>");
				out.println("<span>     |   </span>");
				out.println("<a href='listUsers'> Users</a>");
				out.println("<span>  |   </span>");
				out.println("<a href='userRegistrationForm'> New User Registration</a>");
				out.println("<span>  |   </span>");
				out.println("<a href='newCategoryForm'> Add New Category </a>");
				out.println("<span>  |   </span>");
				out.println("<a href='listCategory'> List Category </a>");;
			out.println("</div >");
			out.println("<h1> Ticket Details</h1>");
			
			out.println("<table border='1'>");
				out.println("<tr>");
					out.println("<th>Sl No</th>");						
					out.println("<th>Subject</th>");
					out.println("<th>DESCRIPTION</th>");
					out.println("<th>CATEGORY</th>");
					out.println("<th>TICKET RAISED BY</th>");
					out.println("<th>CONTACT NO</th>");
					out.println("<th>TICKET DATE</th>");
					out.println("<th>TICKET STATUS</th>");
					out.println("<th>TICKET ALLOTED TO</th>");
																	
				out.println("</tr>");
				
				int i=1;
				while(rs.next())	{
					 				
					out.println("<tr>");
						out.println("<td>"+i+"</td>");
						out.println("<td>"+rs.getString("ticket_subject")+"</td>");
						out.println("<td>"+rs.getString("ticket_description")+"</td>");
						out.println("<td>"+rs.getString("ticket_cat_name")+"</td>");
						out.println("<td>"+rs.getString("user_name")+"</td>");
						out.println("<td>"+rs.getString("user_contact_no")+"</td>");
						out.println("<td>"+rs.getString("ticket_date")+"</td>");
						out.println("<td>"+rs.getString("ticket_status")+"</td>");	
						out.println("<td>");
						if(rs.getString("uts.user_name")!=null){
							out.println(rs.getString("uts.user_name"));
						}
						out.println("</td>");	
						
					out.println("</tr>");
					i++;
				}
			out.println("</table>");
			
			out.println("<form name='allocate-ticket' action='allocateticket' method='post'>");
				out.println("<h1> Ticket Allocation</h1>");
				
				out.println("<input type='hidden' name='ticketId' value='"+ticketId+"'></br>");
				out.println("<table border='0'>");
				
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Technician Name     :  </b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<select name='role' id='role'>");
						for (User user : userList) {
			                out.println("<option value='" + user.getUserId() + "'>" + user.getUserName() + "</option>");
			            }   
						out.println("</select>"
								+ "</td>");	
						
					out.println("</tr>");
										
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td>"							
							+ "</td>");
					out.println("<td style='padding-top:25px'>"
							+ "<input type='submit' value='Allocate' /> "
							+ "</td>");		
				out.println("</tr>");
							
				out.println("</table>");
				
			out.println("</form >");
			out.println("</body></html>");
			
			out.println("<html><body>");
							
			

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

