package com.SajhaKrishi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

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
		String query = "INSERT INTO users (full_name, email,phone_number, password, address, district, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		if (conn == null) {
			System.err.println("Database connection is null");
			return false;
		}
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, user.getFullName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPhoneNumber());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getAddress());
			pstmt.setString(6, user.getDistrict());
			pstmt.setLong(7, user.getRole());
			pstmt.setString(8, user.getStatus());

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

	public User validateUser(String email, String password) {
		String query = "SELECT * FROM users WHERE email = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(email);
			if (rs.next()) {
	            User user =  new User(
	                    rs.getInt("id"),
	                    rs.getString("full_name"),
	                    rs.getString("email"),
	                    rs.getString("password"),
	                    rs.getString("address"),
	                    rs.getString("district"),
	                    rs.getInt("role"),
	                    rs.getString("phone_number")
	                );
	            return user.matchPassword(password) ? user : null;
			}
		} catch (SQLException e) {
			System.out.println(e);
			System.err.println("Error during validation: " + e.getMessage());
		}
		return null;
	}
}
