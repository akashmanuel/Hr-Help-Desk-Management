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

@WebServlet("/employee")
public class EmployeeHomeView extends HttpServlet {
	
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
			String userId=(String)session.getAttribute("userId");

			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			//String sql="SELECT * FROM ticket where user_pers_id="+userId;
			String sql="SELECT "
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
					+ "WHERE t.user_pers_id ="+userId;
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<style>"
			+"body {"
			+"	background: #F1F1F1 repeat scroll center top;"
			+"	color: #333333;"
			+"		font: 15px/20px 'Open Sans', sans-serif;"
			+"		text-align: centre;"
			+"		margin: 2% auto"
			+"	}"
			+"	a {"
			+"		color: #159AFF"
			+"	}"
			+"	label {"
			+"	font: 15px/20px 'Open Sans', sans-serif;"
			+"	}"		
			+"	#frame {"
			+"		background: #FFF;"
			+"		padding: 20px;"
			+"		box-shadow: 0 0 5px #CCCCCC;"
			+"		border: 1px solid #DBD3D0;"
			+"		max-width: 450px;"
			+"		margin: 0% auto;"
			+"		text-align: left;"
			+"		width: 100%"
			+"	}"
			+"	form {"
			+"		width: 100%"
			+"	}	"		
			+"	input[type='text'], input[type='email'], input[type='password'], input[type='number'], textarea, select {"
			+"		box-sizing: inherit!important;"
			+"		margin-top: 0!important"
			+"	}"
			+"	.unchecked:before {"
			+"		content: !important;"
			+"		color: inherit!important;"
			+"		font-size: inherit!important"
			+"	}"
			+"	table {"
			+"		width: 100%!important;"
			+ "     text-align: centre;"
			+"	}	"
			+"		.submit-btn{width: auto}"
			+"		.verifyheader{margin-bottom: 15px}"
					
			+"	form{"
			+"		border: 1px solid #E0E0E0 !important;"
			+"		border-radius: 0px !important;"
			+"		color: #444444 !important;"
			+"		font-family: Open Sans, arial !important;"
			+"		font-size: 16px !important;"
			+"		font-weight: lighter !important;"
			+"		height: 20px !important;"
			+"		padding: 13px 5% !important;"
			+"		width: 90% !important;"
			+"		background-color: #FFF !important;"
			+"	}"
			+"		.container{text-align: center}"
			+"	form .lefttext,.lefttext{"
			+"		margin-top: 11px;"
			+"		padding-right: 20px;"
			+"		text-align: right;"
			+ "font-size: 14px;"
			+"		font-weight: bold;"
			+"		width: 90%;"
			+"}"
		+"	form#signupform .submit-btn, .container .submit-btn {"
		+"			background: none repeat scroll 0 0 #53AE65;"
			+"		border: 0 none;"
			+"		color: #FFFFFF;"
			+"		cursor: pointer;"
			+"		font-size: 15px;"
			+"		font-weight: bold;"
			+"		height: 45px;"
			+"		letter-spacing: 1px;"
			+"		padding: 12px 45px;"
			+"	}"		
			
	+"	</style>");
			out.println("</head>");
			
			out.println("<body>");
			out.println("<h3>Welcome  "+name+" </h3>");
			out.println("<div style='text-align:right; width:100%'>");
				out.println("<a href='Logout'> Logout</a>");
			out.println("</div>");
			out.println("<div style='width:100%; position:relative; border-bottom: ridge;border-top: ridge;'>");
				out.println("<a href='employee' style='padding:0 10px 0 20px ;'> View Tickets</a>");
				out.println("<span>     |   </span>");
				out.println("<a href='employeecreateticketform' style='padding:0 20px 0 20px ;'>Create Tickets</a>");
			out.println("</div >");
							
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
					
					out.println(" <a href='viewticketdetails?eid="+ rs.getInt("ticket_id")+"'> View Ticket Details</a>");
					
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
				
				e.printStackTrace();
			}
		}
	}
}
