package com.SajhaKrishi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.SajhaKrishi.config.DBConnection;
import com.SajhaKrishi.model.User;


public class UserDao {
	Connection conn;
	public UserDao() {
		try {
		 conn = DBConnection.getConnection();	
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public boolean registerUser(User user) {
		 String query = "INSERT INTO users (full_name, phone_number, password, address, district, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

		    if (conn == null) {
		        System.err.println("Database connection is null");
		        return false;
		    }

		    try (PreparedStatement pstmt = conn.prepareStatement(query)) {

		        pstmt.setString(1, user.getFullName());
		        pstmt.setString(2, user.getPhoneNumber());
		        pstmt.setString(3, user.getPassword());
		        pstmt.setString(4, user.getAddress());
		        pstmt.setString(5, user.getDistrict());
		        pstmt.setLong(6, user.getRole());
		        pstmt.setString(7, user.getStatus());

		        int rowsInserted = pstmt.executeUpdate();

		        if (rowsInserted > 0) {
		            System.out.println("User registered successfully");
		            return true;
		        } else {
		            System.err.println("User registration failed");
		            return false;
		        }

		    } catch (SQLException e) {
		        System.err.println("Error while registering user: " + e.getMessage());
		        return false;
		    }
		
	}
}
