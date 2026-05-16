package com.SajhaKrishi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import com.SajhaKrishi.constant.ApiConstant;
import com.SajhaKrishi.constant.BookingStatus;
import com.SajhaKrishi.constant.PageConstant;
import com.SajhaKrishi.dao.BookingDao;
import com.SajhaKrishi.dao.EquipmentDao;
import com.SajhaKrishi.dao.UserDao;
import com.SajhaKrishi.model.BookingModel;
import com.SajhaKrishi.model.EquipmentModel;
import com.SajhaKrishi.model.User;

/**
 * Servlet implementation class DashboardController
 */
@WebServlet(ApiConstant.DASHBOARD)
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookingDao bookingDao;
	private EquipmentDao equipmentDao;
	private UserDao userDao;

	@Override
	public void init() {
		bookingDao = new BookingDao();
		equipmentDao = new EquipmentDao();
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

		try {
			boolean isAdmin = user.getRoleId() == 1;

			List<BookingModel> displayBookings;
			List<BookingModel> statsBookings;
			int totalEquipment = 0;
			int totalUser = 0;

			if (isAdmin) {
				// Admin sees ALL bookings across the platform
				displayBookings = bookingDao.getAllBookings();
				statsBookings = displayBookings;
				totalEquipment = equipmentDao.getAllEquipment().size();
				totalUser = userDao.getAllUsers().size();
			} else {
				// Normal user sees only their equipment bookings
				displayBookings = bookingDao.getBookingsByOwner(user.getId(), null);
				statsBookings = displayBookings;
			}

			// Calculate stats
			int activeRentals = 0;
			int pendingRequests = 0;
			double totalEarnings = 0;

			for (BookingModel booking : statsBookings) {
				if (BookingStatus.CONFIRMED.name().equals(booking.getStatusFlag())) {
					activeRentals++;
				}
				if (BookingStatus.PENDING.name().equals(booking.getStatusFlag())) {
					pendingRequests++;
				}
				if (BookingStatus.COMPLETED.name().equals(booking.getStatusFlag())) {
					totalEarnings += booking.getTotalPrice();
				}
			}

			// Limit recent transactions to 5
			List<BookingModel> recentTransactions = displayBookings.subList(0, Math.min(5, displayBookings.size()));

			// Set attributes
			request.setAttribute("user", user);
			request.setAttribute("isAdmin", isAdmin);
			request.setAttribute("totalEquipment", totalEquipment);
			request.setAttribute("totalUser", totalUser);
			request.setAttribute("activeRentals", activeRentals);
			request.setAttribute("pendingRequests", pendingRequests);
			request.setAttribute("totalEarnings", totalEarnings);
			request.setAttribute("recentTransactions", recentTransactions);
			request.setAttribute("selectedNavItem", "dashboard");
			request.setAttribute("contentPage", PageConstant.DASHBOARD);
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
