package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminError")
public class AdminErrorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set response content type
        response.setContentType("text/html");

        // Get error message from request
        String errorMessage = (String) request.getParameter("errorMessage");

        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Error</title></head><body>");
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
        out.println("<h2>Error</h2>");
        out.println("<p>" + errorMessage + "</p>");
        out.println("</body></html>");
    }
}
