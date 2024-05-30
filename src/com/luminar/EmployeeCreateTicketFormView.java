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

@WebServlet("/employeecreateticketform")
public class EmployeeCreateTicketFormView extends HttpServlet{

		
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		try{
					
						
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			String userId = (String) session.getAttribute("userId");
			
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			String sql="SELECT ticket_cat_id, ticket_cat_name FROM ticket_category where ticket_cat_status>0";
			
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			out.println("<html><body>");
			// JavaScript validation script
            out.println("<script>");
            out.println("function validateForm() {");
            out.println("var tsubject = document.getElementById('tsubject').value.trim();");
            out.println("var tdescription = document.getElementById('tdescription').value.trim();");
            out.println("var tcategory = document.getElementById('tcategory').value.trim();");
            out.println("if (tsubject === '' || tdescription === '' || tcategory === '') {");
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
				out.println("<a href='employee'>View Tickets</a>");
				out.println("<span>     |   </span>");
				out.println("<a href='employeecreateticketform'>Create Tickets</a>");
			out.println("</div>");
			out.println("<form name='ticket-form' action='employeecreateticket' method='post' onsubmit='return validateForm()'>");
				out.println("<h1>Create New Ticket</h1>");					
				out.println("<table border='0'>");
				
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Subject</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<input  type='text'  style='width:500px' id='tsubject' name='tsubject'/> "
								+ "</td>");		
					out.println("</tr>");
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Description</b></label>"
								+ "</td>");
						out.println("<td>"
								+ "<textarea id='tdescription' name='tdescription' rows='4' cols='65'></textarea> "
								+ "</td>");		
					out.println("</tr>");
					
					
					out.println("<tr>");
						out.println("<td>"
								+ "<label><b>Category</b></label>"
								+ "</td>");
						out.println("<td>");
						out.println("<select name='tcategory' id='tcategory' >");
						      while (rs.next()) {
						            	
						             String optionName = rs.getString("ticket_cat_name");						                
						             int optionId = rs.getInt("ticket_cat_id");
						                
						             out.println("<option value='" + optionId + "'>" + optionName + "</option>");
						            
						               
						       }
						 out.println("</select >");
						out.println("</td>"	);
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td>"							
							+ "</td>");
					out.println("<td style='padding-top:25px'>"
							+ "<input type='submit' value='Create Ticket' /> "
							+ "</td>");		
				out.println("</tr>");
			
				out.println("</table>");
					
			out.println("</form >");
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
				
				e.printStackTrace();
			}
		}
	}
}

