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

@WebServlet("/updateuser")
public class AdminEditUserAction extends HttpServlet {
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
            String name = (String) session.getAttribute("user");
            String adminID = (String) session.getAttribute("userId");

            String userperID = request.getParameter("userperID").trim();
            String uname = request.getParameter("uname").trim();
            String uemailId = request.getParameter("uemailId").trim();
            String ucontactNo = request.getParameter("ucontactNo").trim();
            String udepartment = request.getParameter("udepartment").trim();
            String uusername = request.getParameter("uusername").trim();
            String upass = request.getParameter("upassword").trim();
            String role = request.getParameter("role").trim();

            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);

            // Check if username or email already exists
            boolean usernameExists = checkUsernameExists(uusername);
            boolean emailExists = checkEmailExists(uemailId);

            if (usernameExists || emailExists) {
            	 response.sendRedirect("AdminError?errorMessage=Username or Email already exists");
                 return;
            } else {
                // Update user details if username and email are unique
                String sql = "UPDATE user_details set user_name=?,user_login_username=?,user_login_password=?,user_email=?,user_role=?,user_dept=?,user_contact_no=? where user_pers_id=?";
                pst = con.prepareStatement(sql);

                pst.setString(1, uname);
                pst.setString(2, uusername);
                pst.setString(3, upass);
                pst.setString(4, uemailId);
                pst.setString(5, role);
                pst.setString(6, udepartment);
                pst.setString(7, ucontactNo);
                pst.setString(8, userperID);

                pst.executeUpdate();

                response.sendRedirect("listUsers");
            }

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

    // Method to check if username already exists
    private boolean checkUsernameExists(String username) throws SQLException {
        String sql = "SELECT * FROM user_details WHERE user_login_username=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        rs = pst.executeQuery();
        return rs.next(); // True if username exists, false otherwise
    }

    // Method to check if email ID already exists
    private boolean checkEmailExists(String email) throws SQLException {
        String sql = "SELECT * FROM user_details WHERE user_email=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, email);
        rs = pst.executeQuery();
        return rs.next(); // True if email exists, false otherwise
    }
}