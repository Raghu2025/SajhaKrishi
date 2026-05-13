package com.SajhaKrishi.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.SajhaKrishi.constant.*;
import com.SajhaKrishi.dao.UserDao;
import com.SajhaKrishi.model.User;

/**
 * Servlet implementation class AuthController
 */
@WebServlet({ ApiConstant.LOGIN, ApiConstant.REGISTER, ApiConstant.LOGOUT, ApiConstant.FORGET_PASSWORD })
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String[] districtlist = DropdownConstant.DISTRICT;
	private UserDao userDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthController() {
		super();
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		// Routing based on servlet path
		if (path.equals(ApiConstant.LOGIN)) {
			request.getRequestDispatcher(PageConstant.LOGIN_PAGE).forward(request, response);
		} else if (path.equals(ApiConstant.REGISTER)) {
			this.handleRegisterPage(request, response);
		} else if (path.equals(ApiConstant.LOGOUT)) {
			handleLogout(request, response);
		} else if (path.equals(ApiConstant.FORGET_PASSWORD)) {
			request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
		}
	}

	private void handleRegisterPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("district", this.districtlist);
		request.getRequestDispatcher(PageConstant.REGISTER_PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println(path);
		if (path.equals(ApiConstant.LOGIN)) {
			System.out.println("ogin");
			handleLogin(request, response);
		} else if (path.equals(ApiConstant.REGISTER)) {
			handleRegister(request, response);
		} else if (path.equals(ApiConstant.LOGOUT)) {
			handleLogout(request, response);
		} else if (path.equals(ApiConstant.FORGET_PASSWORD)) {

		}
	}

	protected void handleLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = userDao.validateUser(email, password);

		if (user != null) {
			// Safe to access user now

			// Create session
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			// Create cookie
			Cookie userCookie = new Cookie("user_id", String.valueOf(user.getId()));
			userCookie.setMaxAge(60 * 60 * 24); // 24 hours
			response.addCookie(userCookie);

			// Redirect
			response.sendRedirect(request.getContextPath() + ApiConstant.DASHBOARD);

		} else {
			request.setAttribute("error", "Invalid username or password.");
			request.getRequestDispatcher(PageConstant.LOGIN_PAGE).forward(request, response);
		}
	}

	protected void handleRegister(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String fullName = request.getParameter("fullName");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String district = request.getParameter("district");
		int role = 2;
		User user = new User(fullName, email, password, address, district, role, phoneNumber);
		user.setPassword(password);
		boolean success = userDao.registerUser(user);
		if (success) {
			response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
		} else {
			request.setAttribute("error", "Registration failed. Try again.");
			this.handleRegisterPage(request, response);
		}

	}

	protected void handleLogout(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Get the current session (do not create a new one if it doesn't exist)
		HttpSession session = request.getSession(false);

		if (session != null) {
			// Clear all data from the session
			session.removeAttribute(ApiConstant.USER_SESSION_KEY);

			// Completely destroy the session
			session.invalidate();
		}

		// Redirect to login page or homepage with a success message
		// You can also add a query parameter like ?logout=true to show a message on the
		// login page
		response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
	}

	protected void handleForgetPassword(HttpServletRequest request, HttpServletResponse response) {

	}

}
