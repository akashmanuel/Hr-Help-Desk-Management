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

@WebServlet("/userRegistrationForm")
public class AdminNewUserRegistrationFormView extends HttpServlet{

		
	private static final long serialVersionUID = 1L;
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		try{
			
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			out.println("<html><body>");
			// JavaScript validation script
            out.println("<script>");
            out.println("function validateForm() {");
            out.println("var uname = document.forms['user-form']['uname'].value;");
            out.println("var uemailId = document.forms['user-form']['uemailId'].value;");
            out.println("var ucontactNo = document.forms['user-form']['ucontactNo'].value;");
            out.println("var udepartment = document.forms['user-form']['udepartment'].value;");
            out.println("var uusername = document.forms['user-form']['uusername'].value;");
            out.println("var upassword = document.forms['user-form']['upassword'].value;");
            out.println("if (uname === '' || uemailId === '' || ucontactNo === '' || udepartment === '' || uusername === '' || upassword === '') {");
            out.println("alert('Please fill out all fields');");
            out.println("return false;");
            out.println("}");
            out.println("return true;");
            out.println("}");
            out.println("</script>");

            
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
			
			out.println("<form name='user-form' action='newUserRegistration' method='post' onsubmit='return validateForm();'>");
				out.println("<h1>New User Registration form</h1>");					
				out.println("<table border='0'>");
				
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Name</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='uname'  name='uname'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Email ID</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='uemailId'  name='uemailId'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Contact Number</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text'  id='ucontactNo' name='ucontactNo'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Department</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='udepartment' name='udepartment'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>User Name</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text'  id='uusername'  name='uusername'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Password</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='password' id='upassword' name='upassword'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Role</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<select name='role' id='role' >"
								+ "<option value='admin'>Admin</option>"
								+ "<option value='hr'>HR User</option>"
								+ "<option value='employee'>Employee</option>"
								+ "</select> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td>"							
							+ "</td>");
					out.println("<td style='padding-top:25px'>"
							+ "<input type='submit' value='Register' /> "
							+ "</td>");		
				out.println("</tr>");
			
				out.println("</table>");
					
			out.println("</form >");
			out.println("</body></html>");
				
				
		} catch (Exception e){
			e.printStackTrace();
		} 
	}


}
