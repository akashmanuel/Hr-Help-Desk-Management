package com.luminar;

import java.io.IOException;
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

@WebServlet("/newUserRegistration")

public class AdminNewUserRegistrationAction extends HttpServlet {
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
            String uname = request.getParameter("uname").trim();
            String uemailId = request.getParameter("uemailId").trim();
            String ucontactNo = request.getParameter("ucontactNo").trim();
            String udepartment = request.getParameter("udepartment").trim();
            String uusername = request.getParameter("uusername").trim();
            String upassword = request.getParameter("upassword").trim();
            String role = request.getParameter("role").trim();

            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);

            // Check for duplicate username or email ID
            String checkDuplicateSql = "SELECT * FROM user_details WHERE user_login_username=? OR user_email=?";
            pst = con.prepareStatement(checkDuplicateSql);
            pst.setString(1, uusername);
            pst.setString(2, uemailId);
            rs = pst.executeQuery();
            if (rs.next()) {
                // Redirect to error servlet for duplicate user
            	 response.sendRedirect("AdminError?errorMessage=Username or Email already exists");
                return; // Stop further processing
            }

            // Insert new user if no duplicate is found
            String insertSql = "INSERT INTO user_details(user_name,user_login_username,user_login_password,user_email,user_role,user_dept,user_status,user_created_by,user_contact_no) VALUES(?,?,?,?,?,?,1,?,?)";
            pst = con.prepareStatement(insertSql);
            pst.setString(1, uname);
            pst.setString(2, uusername);
            pst.setString(3, upassword);
            pst.setString(4, uemailId);
            pst.setString(5, role);
            pst.setString(6, udepartment);
            pst.setString(7, adminID);
            pst.setString(8, ucontactNo);
            pst.executeUpdate();

            response.sendRedirect("listUsers");

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