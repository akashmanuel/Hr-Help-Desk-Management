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

@WebServlet("/listCategory")
public class AdminListCategoryView extends HttpServlet{
	
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
						
			String sql="SELECT ticket_cat_id ,ticket_cat_name,ticket_cat_description, ticket_cat_status, ticket_cat_creation_date ,ticket_cat_created_by FROM ticket_category where ticket_cat_status>0 ";
			System.out.println(sql);
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
				
			out.println("<h1> Category List</h1>");
				out.println("<table border='1'>");
					out.println("<tr>");
						out.println("<th>Sl No</th>");						
						out.println("<th>Category Name</th>");
						out.println("<th>Category Description </th>");						

					out.println("</tr>");
					
					int i=1;
					while(rs.next())	{
						out.println("<tr>");
							out.println("<td>"+i+"</td>");
							out.println("<td>"+rs.getString(2)+"</td>");
							out.println("<td>"+rs.getString(3)+"</td>");
																					

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
