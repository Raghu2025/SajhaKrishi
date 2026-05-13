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

	// ════════════════════════════
	// CREATE
	// ════════════════════════════
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
			pstmt.setString(14, booking.getStatus());

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

	// ════════════════════════════
	// READ — Single
	// ════════════════════════════
	public BookingModel getBookingById(int id) {
		String query = "SELECT * FROM bookings WHERE id = ?";

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

	// ════════════════════════════
	// READ — All (Admin)
	// ════════════════════════════
	public List<BookingModel> getAllBookings() {
		List<BookingModel> bookingList = new ArrayList<>();
		String query = "SELECT * FROM bookings WHERE status_flag = 'A' ORDER BY booked_at DESC";

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

	// ════════════════════════════
	// READ — By Kisan
	// ════════════════════════════
	public List<BookingModel> getBookingsByKisan(int kisanId) {
		List<BookingModel> bookingList = new ArrayList<>();
		String query = "SELECT * FROM bookings WHERE kisan_id = ? AND status_flag = 'A' ORDER BY booked_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, kisanId);

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

	// ════════════════════════════
	// READ — By Owner
	// ════════════════════════════
	public List<BookingModel> getBookingsByOwner(int ownerId) {
		List<BookingModel> bookingList = new ArrayList<>();
		String query = "SELECT * FROM bookings WHERE owner_id = ? AND status_flag = 'A' ORDER BY booked_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, ownerId);

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

	// ════════════════════════════
	// READ — By Equipment
	// ════════════════════════════
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

	// ════════════════════════════
	// READ — By Status
	// ════════════════════════════
	public List<BookingModel> getBookingsByStatus(String status) {
		List<BookingModel> bookingList = new ArrayList<>();
		String query = "SELECT * FROM bookings WHERE status = ? AND status_flag = 'A' ORDER BY booked_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, status);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					bookingList.add(mapResultSetToBooking(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching bookings by status: " + e.getMessage());
		}
		return bookingList;
	}

	// ════════════════════════════
	// CHECK — Date Conflict
	// Prevents double booking
	// ════════════════════════════
	public boolean isEquipmentAvailable(int equipmentId, String startDate, String endDate) {
		String query = "SELECT COUNT(*) FROM bookings WHERE equipment_id = ? " + "AND status NOT IN ('Cancelled') "
				+ "AND status_flag = 'A' " + "AND (start_date <= ? AND end_date >= ?)";

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
		String query = "UPDATE bookings SET status = ? WHERE id = ?";

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
	 * @param id
	 * @return
	 */
	public boolean deleteBooking(int id) {
		String query = "UPDATE bookings SET status_flag = 'I' WHERE id = ?";

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
		booking.setKisanId(rs.getInt("kisan_id"));
		booking.setOwnerId(rs.getInt("owner_id"));
		booking.setStartDate(rs.getString("start_date"));
		booking.setEndDate(rs.getString("end_date"));
		booking.setTotalDays(rs.getInt("total_days"));
		booking.setPricePerDay(rs.getDouble("price_per_day"));
		booking.setTotalPrice(rs.getDouble("total_price"));
		booking.setDepositAmount(rs.getDouble("deposit_amount"));
		booking.setStatus(rs.getString("status"));
		booking.setPaymentStatus(rs.getString("payment_status"));
		booking.setPickupAddress(rs.getString("pickup_address"));
		booking.setNotes(rs.getString("notes"));
		booking.setBookedAt(rs.getString("booked_at"));
		return booking;
	}
}