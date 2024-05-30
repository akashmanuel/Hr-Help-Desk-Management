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

@WebServlet("/edituser")
public class AdminEditUserForm extends HttpServlet {
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
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			String adminID = (String) session.getAttribute("userId");
			
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			int id=Integer.parseInt(request.getParameter("eid"));
			
			
			String sql="SELECT user_pers_id,user_name,user_login_username,user_login_password,user_email,user_role,user_dept,user_contact_no FROM user_details where user_status>0 and user_pers_id="+id;
			
			
			
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
						
			out.println("<html><body>");
			// JavaScript validation script
			out.println("<script>");
			out.println("function validateForm() {");
			out.println("var uname = document.forms['edit-user']['uname'].value;");
			out.println("var uemailId = document.forms['edit-user']['uemailId'].value;");
			out.println("var ucontactNo = document.forms['edit-user']['ucontactNo'].value;");
			out.println("var udepartment = document.forms['edit-user']['udepartment'].value;");
			out.println("var uusername = document.forms['edit-user']['uusername'].value;");
			out.println("var upassword = document.forms['edit-user']['upassword'].value;");
			out.println("if (uname === '' || uemailId === '' || ucontactNo === '' || udepartment === '' || uusername === '' || upassword === '') {");
			out.println("alert('Please fill out all fields');");
			out.println("return false;");
			out.println("}");
			out.println("return true;");
			out.println("}");
			out.println("</script>");
            
            
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
			
			out.println("<form name='edit-user' action='updateuser' method='post'onsubmit='return validateForm()'>");
				out.println("<h1> User Details</h1>");
				while(rs.next()){
				out.println("<input type='hidden' name='userperID' value='"+rs.getInt(1)+"'></br>");
				out.println("<table border='0'>");
				
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Name</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='uname' name='uname' value='"+rs.getString(2)+"'> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Email ID</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text'  id='uemailId' name='uemailId' value='"+rs.getString(5)+"'> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Contact Number</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='ucontactNo'  name='ucontactNo' value='"+rs.getString(8)+"'> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Department</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='udepartment' name='udepartment' value='"+rs.getString(7)+"'> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>User Name</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='uusername' name='uusername' value='"+rs.getString(3)+"'> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Password</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='password' id='upassword' name='upassword' value='"+rs.getString(4)+"'> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Role</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<select name='role' id='role'>"
							    + "<option value='admin'" + (rs.getString(6).equals("admin") ? " selected" : "") + ">Admin</option>"
							    + "<option value='hr'" + (rs.getString(6).equals("hr") ? " selected" : "") + ">HR User</option>"
							    + "<option value='employee'" + (rs.getString(6).equals("employee") ? " selected" : "") + ">Employee</option>"
							    + "</select>"
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td>"							
							+ "</td>");
					out.println("<td style='padding-top:25px'>"
							+ "<input type='submit' value='Update' /> "
							+ "</td>");		
				out.println("</tr>");
							
				out.println("</table>");
				}
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
