package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/newCategoryForm")
public class AdminAddCategoryForm  extends HttpServlet{

		
	private static final long serialVersionUID = 1L;
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		try{
			
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			
			
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
			out.println("<form name='category-form' action='addNewCategory' method='post'onsubmit='return validateForm()'>");
				out.println("<h1>Add New Category </h1>");					
				out.println("<table border='0'>");				
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Category</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text' id='catname' name='catname'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Category Description</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='textarea' id='catdescription' name='catdescription'/> "
								+ "</td>");		
					out.println("</tr>");										
					out.println("<td>"							
							+ "</td>");
					out.println("<td style='padding-top:25px'>"
							+ "<input type='submit' value='Add Category' /> "
							+ "</td>");		
				out.println("</tr>");
			
				out.println("</table>");
					
			out.println("</form >");
			// JavaScript validation script
            out.println("<script>");
	            out.println("function validateForm() {");
		            out.println("var catname = document.forms['category-form']['catname'].value;");
		            out.println("var catdescription = document.forms['category-form']['catdescription'].value;");
		            out.println("if (catname === '' || catdescription === '') {");
		            out.println("alert('Category and Category Description must be filled out');");
		            out.println("return false;");
		            out.println("}");
		            out.println("return true;");
	            out.println("}");
	            out.println("</script>");
            
			out.println("</body></html>");
				
				
		} catch (Exception e){
			e.printStackTrace();
		} 
	}


}
