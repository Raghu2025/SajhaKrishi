package com.SajhaKrishi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.SajhaKrishi.config.DBConnection;
import com.SajhaKrishi.model.User;

public class UserDao {
	Connection conn;

	public UserDao() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
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
			pstmt.setLong(7, user.getRoleId());
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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"),
						rs.getString("password"), rs.getString("address"), rs.getString("district"), rs.getInt("role"),
						rs.getString("phone_number"));
				return user.matchPassword(password) ? user : null;
			}
		} catch (SQLException e) {
			System.out.println(e);
			System.err.println("Error during validation: " + e.getMessage());
		}
		return null;
	}

	public boolean isEmailExists(String email) {
		String query = "SELECT COUNT(*) FROM users WHERE email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateUser(User user) {
		String query = "UPDATE users SET full_name=?, phone_number=?, address=?, district=? WHERE id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, user.getFullName());
			pstmt.setString(2, user.getPhoneNumber());
			pstmt.setString(3, user.getAddress());
			pstmt.setString(4, user.getDistrict());
			pstmt.setInt(5, user.getId());

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePassword(int userId, String newPlainPassword) {
		String query = "UPDATE users SET password=? WHERE id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			String hashedPassword = BCrypt.hashpw(newPlainPassword, BCrypt.gensalt());
			pstmt.setString(1, hashedPassword);
			pstmt.setInt(2, userId);

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public User getUserById(int id) {
		String query = "SELECT * FROM users WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new User(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"),
							rs.getString("password"), rs.getString("address"), rs.getString("district"),
							rs.getInt("role"), rs.getString("phone_number"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByEmail(String email) {
		String query = "SELECT * FROM users WHERE email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new User(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"),
							rs.getString("password"), rs.getString("address"), rs.getString("district"),
							rs.getInt("role"), rs.getString("phone_number"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteUser(int id) {
		String query = "UPDATE users SET status = 'I' WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private User mapResultSetToUser(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"),
				rs.getString("password"), rs.getString("address"), rs.getString("district"), rs.getInt("role"),
				rs.getString("phone_number"));
		user.setRoleName(rs.getString("role_name"));

		// Set login attempt tracking fields
		user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
		user.setIsLocked(rs.getString("is_locked"));
		user.setAccountLockedUntil(rs.getLong("account_locked_until"));
		user.setLastFailedLogin(rs.getLong("last_failed_login"));

		return user;
	}

	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		String query = "SELECT users.*, role.name AS role_name FROM users JOIN role ON users.role = role.id";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				userList.add(mapResultSetToUser(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error fetching all users: " + e.getMessage());
		}
		return userList;
	}

	/**
	 * Get login attempt info for a user
	 */
	public LoginAttemptInfo getLoginAttemptInfo(String email) {
		String query = "SELECT id, failed_login_attempts, last_failed_login, account_locked_until, is_locked FROM users WHERE email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new LoginAttemptInfo(rs.getInt("id"), rs.getInt("failed_login_attempts"),
							rs.getLong("last_failed_login"), rs.getLong("account_locked_until"),
							"Y".equalsIgnoreCase(rs.getString("is_locked")));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error getting login attempt info: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Increment failed login attempts for a user
	 */
	public boolean incrementFailedLoginAttempts(String email) {
		String query = "UPDATE users SET failed_login_attempts = failed_login_attempts + 1, last_failed_login = ? WHERE email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setLong(1, System.currentTimeMillis());
			pstmt.setString(2, email);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error incrementing failed login attempts: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Lock user account after max failed attempts
	 */
	public boolean lockUserAccount(String email, long lockUntilTime) {
		String query = "UPDATE users SET is_locked = 'Y', account_locked_until = ? WHERE email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setLong(1, lockUntilTime);
			pstmt.setString(2, email);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error locking user account: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Unlock user account and reset failed attempts
	 */
	public boolean unlockUserAccount(String email) {
		String query = "UPDATE users SET is_locked = 'N', failed_login_attempts = 0, account_locked_until = NULL, last_failed_login = NULL WHERE email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error unlocking user account: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Reset failed login attempts for a user (on successful login)
	 */
	public boolean resetFailedLoginAttempts(int userId) {
		String query = "UPDATE users SET failed_login_attempts = 0, last_failed_login = NULL, account_locked_until = NULL, is_locked = 'N' WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error resetting failed login attempts: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Inner class to hold login attempt information
	 */
	public static class LoginAttemptInfo {
		public int userId;
		public int failedAttempts;
		public long lastFailedLoginTime;
		public long accountLockedUntilTime;
		public boolean isLocked;

		public LoginAttemptInfo(int userId, int failedAttempts, long lastFailedLoginTime, long accountLockedUntilTime,
				boolean isLocked) {
			this.userId = userId;
			this.failedAttempts = failedAttempts;
			this.lastFailedLoginTime = lastFailedLoginTime;
			this.accountLockedUntilTime = accountLockedUntilTime;
			this.isLocked = isLocked;
		}
	}
}
