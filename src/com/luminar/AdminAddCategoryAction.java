package com.luminar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addNewCategory")

public class AdminAddCategoryAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String adminID = (String) session.getAttribute("userId");
			String catName = request.getParameter("catname").trim();;

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			// Check if category name already exists
			String checkDuplicateSql = "SELECT * FROM ticket_category WHERE ticket_cat_name = ?";
			pst = con.prepareStatement(checkDuplicateSql);
			pst.setString(1, catName);
			rs = pst.executeQuery();

			if (rs.next()) {
				// Redirect to error servlet for duplicate category name
				response.sendRedirect("AdminError?errorMessage=duplicateCategory");
			} else {
				// Proceed with inserting new category
				String catDescription = request.getParameter("catdescription").trim();;
				int catStatus = 1;
				LocalDate today = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String todayString = today.format(formatter);

				String insertSql = "INSERT INTO ticket_category(ticket_cat_name, ticket_cat_description, ticket_cat_status, ticket_cat_creation_date, ticket_cat_created_by) VALUES (?, ?, ?, ?, ?)";
				pst = con.prepareStatement(insertSql);
				pst.setString(1, catName);
				pst.setString(2, catDescription);
				pst.setInt(3, catStatus);
				pst.setString(4, todayString);
				pst.setString(5, adminID);

				pst.executeUpdate();

				response.sendRedirect("listCategory");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close resources
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