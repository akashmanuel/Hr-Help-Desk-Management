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

@WebServlet("/listUsers")

public class AdminListUsersView  extends HttpServlet{
	
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

			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");

			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			String sql="SELECT user_pers_id,user_name,user_login_username,user_email,user_role,user_dept FROM user_details where user_status>0 ";
			
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			out.println("<html><body>");
			out.println("<h3>Welcome  "+name+" </h3>");
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
				
			out.println("<h1> User List</h1>");
				out.println("<table border='1'>");
					out.println("<tr>");
						out.println("<th>Sl No</th>");						
						out.println("<th>Name</th>");
						out.println("<th>User Name</th>");
						out.println("<th>Email ID</th>");
						out.println("<th>Role</th>");
						out.println("<th>Department</th>");
						out.println("<th>EDIT</th>");
						out.println("<th>DELETE</th>");
					out.println("</tr>");
					
					int i=1;
					while(rs.next())	{
						out.println("<tr>");
							out.println("<td>"+i+"</td>");
							out.println("<td>"+rs.getString(2)+"</td>");
							out.println("<td>"+rs.getString(3)+"</td>");
							out.println("<td>"+rs.getString(4)+"</td>");
							out.println("<td>"+rs.getString(5)+"</td>");
							out.println("<td>"+rs.getString(6)+"</td>");														
														
							out.println("<td> <a href='edituser?eid="+ rs.getInt(1)+"'>Edit</a></td>");
							out.println("<td> <a href='deleteuser?did="+ rs.getInt(1)+"'>Delete</a></td>");
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

