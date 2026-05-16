package com.SajhaKrishi.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.SajhaKrishi.constant.ApiConstant;
import com.SajhaKrishi.constant.BookingStatus;
import com.SajhaKrishi.constant.PageConstant;
import com.SajhaKrishi.constant.PaymentStatus;
import com.SajhaKrishi.dao.BookingDao;
import com.SajhaKrishi.dao.EquipmentDao;
import com.SajhaKrishi.model.BookingModel;
import com.SajhaKrishi.model.EquipmentModel;
import com.SajhaKrishi.model.User;
import com.SajhaKrishi.utils.ValidationUtil;

@WebServlet(ApiConstant.BOOKING + "/*")
public class BookingController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BookingDao bookingDao;
	private EquipmentDao equipmentDao;

	@Override
	public void init() {
		bookingDao = new BookingDao();
		equipmentDao = new EquipmentDao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals(ApiConstant.LIST)) {
			request.setAttribute("selectedNavItem", "bookingList");
			handleList(request, response);

		} else if (pathInfo.equals(ApiConstant.SELF_BOOKING)) {
			request.setAttribute("selectedNavItem", "selfBookingList");
			request.setAttribute("type",  "mine");
			handleList(request, response);

		} else if (pathInfo.equals(ApiConstant.DETAIL)) {
			request.setAttribute("type",  "owner");
			handleDetail(request, response);

		} else if (pathInfo.equals(ApiConstant.DELETE)) {
			handleCancel(request, response);

		} else if (pathInfo.equals("/update")) {
			handleUpdateStatus(request, response);

		} else {
			response.sendRedirect(request.getContextPath() + ApiConstant.BOOKING + ApiConstant.LIST);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if ("/update".equals(pathInfo)) {
			handleUpdateStatus(request, response);
			return;
		}
		handleCreateBooking(request, response);
	}

	/**
	 * List all bookings for the logged-in user If owner: show bookings of their
	 * equipment If kisan: show their bookings
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleList(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
	        return;
	    }

	    User user = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);
	    if (user == null) {
	        response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
	        return;
	    }

	    // "mine"  = bookings I made as a farmer (kisan)
	    // "owner" = booking requests on my equipment (default)
	    String type   = request.getAttribute("type") != null 
	              ? (String) request.getAttribute("type") 
	                      : request.getParameter("type");
	    String status = request.getParameter("status");
	    if (type == null || type.isBlank()) type = "owner";

	    List<BookingModel> bookingList;

	    if (type.equals("mine")) {
	        bookingList = bookingDao.getBookingsByKisan(user.getId(), status);
	    } else {
	        bookingList = bookingDao.getBookingsByOwner(user.getId(), status);
	    }

	    request.setAttribute("bookingList", bookingList);
	    request.setAttribute("currentType",   type);
	    request.setAttribute("currentStatus", status);
	    request.setAttribute("contentPage",   PageConstant.BOOKING_LIST);
	    request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
	}

	/**
	 * View booking details
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int bookingId = Integer.parseInt(request.getParameter("id"));
			BookingModel booking = bookingDao.getBookingById(bookingId);

			if (booking == null) {
				response.sendRedirect(request.getContextPath() + "/booking/list");
				return;
			}

			// Get equipment details
			EquipmentModel equipment = equipmentDao.getEquipmentById(booking.getEquipmentId());
			request.setAttribute("booking", booking);
			request.setAttribute("equipment", equipment);
			request.setAttribute("contentPage", PageConstant.BOOKING_DETAIL);
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/booking/list");
		}
	}

	/**
	 * Create a new booking POST action
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleCreateBooking(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		try {
			HttpSession session = request.getSession(false);
			User kisan = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);
			int equipmentId = Integer.parseInt(request.getParameter("equipmentId"));

			if (kisan == null) {
				response.sendRedirect(ApiConstant.LOGIN + "?redirectUrl" + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" + equipmentId);
				return;
			}

			// Extract parameters
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String pickupAddress = request.getParameter("pickupAddress");
			String notes = request.getParameter("notes");

			// Validate input
			if (startDate == null || endDate == null || pickupAddress == null || pickupAddress.trim().isEmpty()) {
				request.getSession().setAttribute("error", "Missing required fields");
				response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" +equipmentId);
				return;
			}

			// Get equipment details
			EquipmentModel equipment = equipmentDao.getEquipmentById(equipmentId);
			if (equipment == null) {
				request.getSession().setAttribute("error", "Equipment not found");
				response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" +equipmentId);
				return;
			}

			// Check availability
			if (!bookingDao.isEquipmentAvailable(equipmentId, startDate, endDate)) {
				request.getSession().setAttribute("error", "Equipment is not available for the selected dates");
				response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" +equipmentId);
				return;
			}

			// Calculate total days and price
			int totalDays = calculateDays(startDate, endDate);
			if (totalDays <= 0) {
				request.getSession().setAttribute("error", "Invalid date range");
				response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" +equipmentId);
				return;
			}

			double totalPrice = equipment.getPricePerDay() * totalDays;

			// Create booking
			BookingModel booking = new BookingModel();
			booking.setEquipmentId(equipmentId);
			booking.setKisanId(kisan.getId());
			booking.setOwnerId(equipment.getOwnerId());
			booking.setStartDate(startDate);
			booking.setEndDate(endDate);
			booking.setTotalDays(totalDays);
			booking.setPricePerDay(equipment.getPricePerDay());
			booking.setTotalPrice(totalPrice);
			booking.setDepositAmount(equipment.getDepositAmount());
			booking.setStatus("A");
			booking.setStatusFlag(BookingStatus.PENDING.name());
			booking.setPaymentStatus(PaymentStatus.UNPAID.name());
			booking.setPickupAddress(pickupAddress);
			booking.setNotes(notes != null ? notes : "");

			// Save to database
			if (bookingDao.addBooking(booking)) {
				request.getSession().setAttribute("success", "Booking request created successfully!");
				response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" +equipmentId);
			} else {
				request.getSession().setAttribute("error", "Failed to create booking");
				response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" +equipmentId);
			}

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Update booking status Only owner or admin can update POST action
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleUpdateStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute(ApiConstant.USER_SESSION_KEY) == null) {
				response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
				return;
			}

			int bookingId = Integer.parseInt(request.getParameter("bookingId"));
			String newStatus = request.getParameter("status");
			String type = request.getParameter("type");
			if (type == null || type.isBlank()) {
				type = "owner";
			}

			// Validate status
			if (newStatus == null || newStatus.trim().isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status");
				return;
			}

			// Check if status is valid
			if (!isValidStatus(newStatus)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status value");
				return;
			}

			// Update booking status
			if (bookingDao.updateBookingStatus(bookingId, newStatus)) {
				request.getSession().setAttribute("success", "Booking status updated successfully!");
			} else {
				request.getSession().setAttribute("error", "Failed to update booking status");
			}

			response.sendRedirect(request.getContextPath() + ApiConstant.BOOKING + ApiConstant.LIST + "?type=" + type);

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID");
		}
	}

	/**
	 * Cancel a booking (soft delete) GET action
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleCancel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int bookingId = Integer.parseInt(request.getParameter("id"));

			if (bookingDao.deleteBooking(bookingId)) {
				request.getSession().setAttribute("success", "Booking cancelled successfully!");
			} else {
				request.getSession().setAttribute("error", "Failed to cancel booking");
			}

			response.sendRedirect(request.getContextPath() + "/booking/list");

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID");
		}
	}

	/**
	 * Calculate total days between two dates (yyyy-MM-dd format)
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private int calculateDays(String startDate, String endDate) {
		try {
			java.time.LocalDate start = java.time.LocalDate.parse(startDate);
			java.time.LocalDate end = java.time.LocalDate.parse(endDate);
			return (int) java.time.temporal.ChronoUnit.DAYS.between(start, end);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Validate booking status
	 *
	 * @param status
	 * @return
	 */
	private boolean isValidStatus(String status) {
		if (status == null || status.isBlank()) {
			return false;
		}
		try {
			BookingStatus.valueOf(status);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
