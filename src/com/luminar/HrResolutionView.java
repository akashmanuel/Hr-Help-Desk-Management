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

@WebServlet("/viewticket")
public class HrResolutionView extends HttpServlet {
	
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
			int i;
			int ticketStatusId=0;
			HttpSession session = request.getSession(false);
			String loginName = (String) session.getAttribute("user");
			String loginID = (String) session.getAttribute("userId");
			
			int ticketId=Integer.parseInt(request.getParameter("eid"));

			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
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
			 // JavaScript validation script
            out.println("<script>");
            out.println("function validateForm() {");
            out.println("var tresolution = document.getElementById('tresolution').value.trim();");
            out.println("if (tresolution === '') {");
            out.println("alert('Please enter the resolution');");
            out.println("return false;");
            out.println("}");
            out.println("return true;");
            out.println("}");
            out.println("</script>");
            
            
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
						out.println("<th>CONTACT NO</th>");
						out.println("<th>TICKET DATE</th>");
						out.println("<th>TICKET STATUS</th>");
						out.println("<th>TICKET ALLOTED TO</th>");
																		
					out.println("</tr>");
					
					i=1;
					while(rs.next())	{
						 ticketStatusId=rs.getInt("ticket_status_id");
						
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
				
				sql="SELECT ticket_status_id, ticket_status FROM ticket_status";
						
				pst=con.prepareStatement(sql);
				rs=pst.executeQuery();
				
				out.println("<div style='width:100%; position:relative; border-bottom: ridge;'>");
					out.println("<h2> Ticket Resolution</h2>");
				out.println("</div ></br></br>");
					
				out.println("<form name='resolution-form' action='addResolution' method='post' onsubmit='return validateForm()'>");
            out.println("<table border='0'>");
				
				out.println("<input type='hidden' name='currentticketID' value='"+ticketId+"'>");
					out.println("<table border='0'>");				
						out.println("<tr>");
								out.println("<td>"
										+ "<label><b>Resolution    :</b></label>"
										+ "</td>");
								out.println("<td>"
										+ "<textarea id='tresolution' name='tresolution' rows='4' cols='65'></textarea> "
										+ "</td>");		
						out.println("</tr>");
							
						out.println("<tr>");
								out.println("<td>"
										+ "<label><b>Ticket Status</b></label>"
										+ "</td>");
								out.println("<td>"
										+ "<select name='tstatus' id='tstatus'>");
								 while (rs.next()) {
						                out.println("<option value='" + rs.getInt("ticket_status_id") + "'"+ (rs.getInt("ticket_status_id")==(ticketStatusId) ? " selected" : "") + ">" + rs.getString("ticket_status") + "</option>");
								
						            }   
									out.println("</select>"
										+ "</td>");	
								
						out.println("</tr>");
						out.println("<tr>");
							out.println("<td>"							
									+ "</td>");
							out.println("<td style='padding-top:25px'>"
									+ "<input type='submit' value='Resolution' /> "
									+ "</td>");		
							out.println("</tr>");
					
						out.println("</table>");
						
					out.println("</form >");
					
					out.println("<div style='width:100%; position:relative; border-bottom: ridge;'>");
					out.println("<h2> Ticket Resolutions</h2>");
				out.println("</div ></br></br>");
				
				sql="SELECT "
						+ "t.ticket_resolution_id,"
						+ " t.user_pers_id,"
						+ "t.ticket_id, "
						+ "t.ticket_resolution_date, "
						+ "t.ticket_status_id, "
						+ "t.ticket_resolution_description, "
						+ "ts.ticket_status, "
						+ "u.user_name "					
						+ "FROM ticket_resolution t "
						+ "LEFT JOIN ticket_status ts ON t.ticket_status_id=ts.ticket_status_id "
						+ "LEFT JOIN user_details u ON t.user_pers_id=u.user_pers_id  "
						+ "WHERE t.ticket_id ="+ticketId;
						
				pst=con.prepareStatement(sql);
				rs=pst.executeQuery();
				out.println("<table border='1'>");
				out.println("<tr>");
					out.println("<th>Sl No</th>");	
					out.println("<th>Technician</th>");
					out.println("<th>Resolution</th>");
					out.println("<th>Status</th>");					
					out.println("<th>Resolution Date</th>");				
																	
				out.println("</tr>");
				
				 i=1;
				while(rs.next())	{
					 				
					out.println("<tr>");
						out.println("<td>"+i+"</td>");
						out.println("<td>"+rs.getString("user_name")+"</td>");
						out.println("<td>"+rs.getString("ticket_resolution_description")+"</td>");
						out.println("<td>"+rs.getString("ticket_status")+"</td>");						
						out.println("<td>"+rs.getString("ticket_resolution_date")+"</td>");
												
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

