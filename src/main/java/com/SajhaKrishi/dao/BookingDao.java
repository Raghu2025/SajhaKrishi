package com.SajhaKrishi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.SajhaKrishi.config.DBConnection;
import com.SajhaKrishi.model.BookingModel;

public class BookingDao {

	Connection conn;

	public BookingDao() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param booking
	 * @return
	 */
	public boolean addBooking(BookingModel booking) {
		String query = "INSERT INTO bookings (equipment_id, kisan_id, owner_id, start_date, end_date, "
				+ "total_days, price_per_day, total_price, deposit_amount, "
				+ "status, payment_status, pickup_address, notes, status_flag) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		if (conn == null) {
			System.err.println("Database connection is null");
			return false;
		}

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, booking.getEquipmentId());
			pstmt.setInt(2, booking.getKisanId());
			pstmt.setInt(3, booking.getOwnerId());
			pstmt.setString(4, booking.getStartDate());
			pstmt.setString(5, booking.getEndDate());
			pstmt.setInt(6, booking.getTotalDays());
			pstmt.setDouble(7, booking.getPricePerDay());
			pstmt.setDouble(8, booking.getTotalPrice());
			pstmt.setDouble(9, booking.getDepositAmount());
			pstmt.setString(10, booking.getStatus());
			pstmt.setString(11, booking.getPaymentStatus());
			pstmt.setString(12, booking.getPickupAddress());
			pstmt.setString(13, booking.getNotes());
			pstmt.setString(14, booking.getStatusFlag());

			int rowsInserted = pstmt.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Booking added successfully");
				return true;
			} else {
				System.err.println("Booking insertion failed");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("Error while adding booking: " + e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BookingModel getBookingById(int id) {
		String query = """
				     SELECT b.*,
				           e.name AS equipment_name,
				           e.image_path,
				           e.price_per_day,
				           c.name AS category_name
				    FROM bookings b
				    JOIN equipment e ON b.equipment_id = e.id
				    JOIN category c ON e.category_id = c.id
				    WHERE b.id = ?
				""";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToBooking(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching booking by ID: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Get All Booking
	 * 
	 * @return
	 */
	public List<BookingModel> getAllBookings() {
		List<BookingModel> bookingList = new ArrayList<>();
		String query = """
				     SELECT b.*,
				           e.name AS equipment_name,
				           e.image_path,
				           e.price_per_day,
				           c.name AS category_name
				    FROM bookings b
				    JOIN equipment e ON b.equipment_id = e.id
				    JOIN category c ON e.category_id = c.id
				""";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				bookingList.add(mapResultSetToBooking(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error fetching all bookings: " + e.getMessage());
		}
		return bookingList;
	}

	/**
	 * By Kissan
	 * 
	 * @param kisanId
	 * @param status
	 * @return
	 */
	public List<BookingModel> getBookingsByKisan(int kisanId, String status) {
		List<BookingModel> bookingList = new ArrayList<>();
		String query;

		// Build query based on whether status is provided
		if (status == null || status.isEmpty()) {
			query = """
					    SELECT b.*,
					           e.name AS equipment_name,
					           e.image_path,
					           e.price_per_day,
					           c.name AS category_name
					    FROM bookings b
					    JOIN equipment e ON b.equipment_id = e.id
					    JOIN category c ON e.category_id = c.id
					    WHERE b.kisan_id = ? AND b.status = 'A'
					    ORDER BY b.booked_at DESC
					""";
		} else {
			query = """
					    SELECT b.*,
					           e.name AS equipment_name,
					           e.image_path,
					           e.price_per_day,
					           c.name AS category_name
					    FROM bookings b
					    JOIN equipment e ON b.equipment_id = e.id
					    JOIN category c ON e.category_id = c.id
					    WHERE b.kisan_id = ? AND b.status_flag = ? AND b.status = 'A'
					    ORDER BY b.booked_at DESC
					""";
		}

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, kisanId);
			if (status != null && !status.isEmpty()) {
				pstmt.setString(2, status);
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					bookingList.add(mapResultSetToBooking(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching bookings by kisan: " + e.getMessage());
		}
		return bookingList;
	}

	/**
	 * By Owner
	 * 
	 * @param ownerId
	 * @param status
	 * @return
	 */
	public List<BookingModel> getBookingsByOwner(int ownerId, String status) {
		List<BookingModel> bookingList = new ArrayList<>();
		String query;

		// Build query based on whether status is provided
		if (status == null || status.isEmpty()) {
			query = """
					    SELECT b.*,
					           e.name AS equipment_name,
					           e.image_path,
					           e.price_per_day,
					           c.name AS category_name
					    FROM bookings b
					    JOIN equipment e ON b.equipment_id = e.id
					    JOIN category c ON e.category_id = c.id
					    WHERE b.owner_id = ? AND b.status = 'A'
					    ORDER BY b.booked_at DESC
					""";
		} else {
			query = """
					    SELECT b.*,
					           e.name AS equipment_name,
					           e.image_path,
					           e.price_per_day,
					           c.name AS category_name
					    FROM bookings b
					    JOIN equipment e ON b.equipment_id = e.id
					    JOIN category c ON e.category_id = c.id
					    WHERE b.owner_id = ? AND b.status_flag = ? AND b.status = 'A'
					    ORDER BY b.booked_at DESC
					""";
		}

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, ownerId);
			if (status != null && !status.isEmpty()) {
				pstmt.setString(2, status);
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					bookingList.add(mapResultSetToBooking(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching bookings by owner: " + e.getMessage());
		}
		return bookingList;
	}

	/**
	 * 
	 * @param equipmentId
	 * @return
	 */
	public List<BookingModel> getBookingsByEquipment(int equipmentId) {
		List<BookingModel> bookingList = new ArrayList<>();
		String query = "SELECT * FROM bookings WHERE equipment_id = ? AND status_flag = 'A'";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, equipmentId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					bookingList.add(mapResultSetToBooking(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching bookings by equipment: " + e.getMessage());
		}
		return bookingList;
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	public List<BookingModel> getBookingsByStatus(String status) {
		 String query;

		    if (status == null || status.isEmpty()) {
		        query = """
		            SELECT b.*, 
		                   e.name AS equipment_name,
		                   e.image_path,
		                   c.name AS category_name
		            FROM bookings b
		            JOIN equipment e ON b.equipment_id = e.id
		            JOIN category c ON e.category_id = c.id
		            WHERE b.status = 'A'
		            ORDER BY b.booked_at DESC
		        """;
		    } else {
		        query = """
		            SELECT b.*, 
		                   e.name AS equipment_name,
		                   e.image_path,
		                   c.name AS category_name
		            FROM bookings b
		            JOIN equipment e ON b.equipment_id = e.id
		            JOIN category c ON e.category_id = c.id
		            WHERE b.status_flag = ?
		            AND b.status = 'A'
		            ORDER BY b.booked_at DESC
		        """;
		    }

		    List<BookingModel> list = new ArrayList<>();

		    try (PreparedStatement ps = conn.prepareStatement(query)) {
		        if (status != null && !status.isEmpty()) {
		            ps.setString(1, status);
		        }
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		            list.add(mapResultSetToBooking(rs));
		        }
		    } catch (SQLException e) {
		        System.out.println("Error fetching bookings by status: " + e.getMessage());
		    }

		    return list;
	}

	/**
	 * 
	 * @param equipmentId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean isEquipmentAvailable(int equipmentId, String startDate, String endDate) {
		String query = "SELECT COUNT(*) FROM bookings WHERE equipment_id = ? " + "AND status_flag NOT IN ('CANCELLED') "
				+ "AND status = 'A' " + "AND (start_date <= ? AND end_date >= ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, equipmentId);
			pstmt.setString(2, endDate);
			pstmt.setString(3, startDate);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) == 0; // true = no conflict = available
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking availability: " + e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateBookingStatus(int id, String status) {
		String query = "UPDATE bookings SET status_flag = ? WHERE id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, status);
			pstmt.setInt(2, id);

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error updating booking status: " + e.getMessage());
			return false;
		}
	}

	/**
	 * UpdatePaymentStatus
	 * 
	 * @param id
	 * @param paymentStatus
	 * @return
	 */
	public boolean updatePaymentStatus(int id, String paymentStatus) {
		String query = "UPDATE bookings SET payment_status = ? WHERE id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, paymentStatus);
			pstmt.setInt(2, id);

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error updating payment status: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cancel Booking
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteBooking(int id) {
		String query = "UPDATE bookings SET status_flag = 'CANCELLED' WHERE id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error deleting booking: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Booking Mapper
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private BookingModel mapResultSetToBooking(ResultSet rs) throws SQLException {
		BookingModel booking = new BookingModel(rs.getInt("id"));
		booking.setEquipmentId(rs.getInt("equipment_id"));
		booking.setEquipmentName(rs.getString("equipment_name"));
		booking.setImagePath(rs.getString("image_path"));
		booking.setCategoryName(rs.getString("category_name"));
		booking.setKisanId(rs.getInt("kisan_id"));
		booking.setOwnerId(rs.getInt("owner_id"));
		booking.setStartDate(rs.getString("start_date"));
		booking.setEndDate(rs.getString("end_date"));
		booking.setTotalDays(rs.getInt("total_days"));
		booking.setPricePerDay(rs.getDouble("price_per_day"));
		booking.setTotalPrice(rs.getDouble("total_price"));
		booking.setDepositAmount(rs.getDouble("deposit_amount"));
		booking.setStatus(rs.getString("status"));
		booking.setStatusFlag(rs.getString("status_flag"));
		booking.setPaymentStatus(rs.getString("payment_status"));
		booking.setPickupAddress(rs.getString("pickup_address"));
		booking.setNotes(rs.getString("notes"));
		booking.setBookedAt(rs.getString("booked_at"));
		return booking;
	}
}