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
import com.SajhaKrishi.utils.LoginAttemptUtil;
import com.SajhaKrishi.utils.EmailUtil;
import com.SajhaKrishi.utils.ForgetPasswordUtil;
import com.SajhaKrishi.dao.UserDao.LoginAttemptInfo;

/**
 * Servlet implementation class AuthController
 */
@WebServlet({ ApiConstant.LOGIN, ApiConstant.REGISTER, ApiConstant.LOGOUT, ApiConstant.FORGET_PASSWORD,
		ApiConstant.ADMIN_USERS, ApiConstant.PROFILE })
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
			String returnUrl = request.getParameter("returnUrl");
			request.setAttribute("returnUrl", returnUrl);
			request.getRequestDispatcher(PageConstant.LOGIN_PAGE).forward(request, response);
		} else if (path.equals(ApiConstant.REGISTER)) {
			this.handleRegisterPage(request, response);
		} else if (path.equals(ApiConstant.LOGOUT)) {
			handleLogout(request, response);
		} else if (path.equals(ApiConstant.FORGET_PASSWORD)) {
			request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
		} else if (path.equals(ApiConstant.ADMIN_USERS)) {
			handleUserList(request, response);
		} else if (path.equals(ApiConstant.PROFILE)) {
			handleProfile(request, response);
		}
	}

	private void handleRegisterPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("district", this.districtlist);
		request.getRequestDispatcher(PageConstant.REGISTER_PAGE).forward(request, response);
	}

	private void handleUserList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
			return;
		}
		User current = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);
		if (current == null || current.getRoleId() != 1) {
			response.sendRedirect(request.getContextPath() + ApiConstant.UNAUTHORIZED);
			return;
		}
		// fetch all users and forward to user list JSP
		java.util.List<User> users = userDao.getAllUsers();
		request.setAttribute("userList", users);
		request.setAttribute("selectedNavItem", "user");
		request.setAttribute("contentPage", PageConstant.USER_LIST);
		request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		if (path.equals(ApiConstant.LOGIN)) {
			handleLogin(request, response);
		} else if (path.equals(ApiConstant.REGISTER)) {
			handleRegister(request, response);
		} else if (path.equals(ApiConstant.LOGOUT)) {
			handleLogout(request, response);
		} else if (path.equals(ApiConstant.FORGET_PASSWORD)) {
			handleForgetPassword(request, response);
		} else if (path.equals(ApiConstant.PROFILE)) {
			handleProfileUpdate(request, response);
		} else if (path.equals(ApiConstant.ADMIN_USERS)) {
			String action = request.getParameter("action");
			if ("unlock".equals(action)) {
				handleUnlockUser(request, response);
			}
		}
	}

	protected void handleLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String returnUrl = request.getParameter("returnUrl");

		// Get login attempt info for this email
		LoginAttemptInfo attemptInfo = userDao.getLoginAttemptInfo(email);

		// Check if account is locked
		if (attemptInfo != null && attemptInfo.isLocked && LoginAttemptUtil.isAccountLocked(attemptInfo.accountLockedUntilTime)) {
			long remainingMinutes = LoginAttemptUtil.getRemainingLockTimeMinutes(attemptInfo.accountLockedUntilTime);
			request.setAttribute("error", String.format(
				"Account is locked due to multiple failed login attempts. Please try again in %d minute(s).",
				remainingMinutes
			));
			request.setAttribute("returnUrl", returnUrl);
			request.getRequestDispatcher(PageConstant.LOGIN_PAGE).forward(request, response);
			return;
		}

		// If account was locked and time has expired, unlock it
		if (attemptInfo != null && attemptInfo.isLocked && !LoginAttemptUtil.isAccountLocked(attemptInfo.accountLockedUntilTime)) {
			userDao.unlockUserAccount(email);
			attemptInfo = null; // Reset attempt info
		}

		// Attempt to validate user
		User user = userDao.validateUser(email, password);

		if (user != null) {
			// Successful login - reset failed attempts
			userDao.resetFailedLoginAttempts(user.getId());

			// Create session and cookies
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			Cookie userCookie = new Cookie("user_id", String.valueOf(user.getId()));
			userCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(userCookie);

			// Redirect to returnUrl if present and safe, otherwise dashboard
			if (returnUrl != null && !returnUrl.isBlank() && returnUrl.startsWith("/")) {
				response.sendRedirect(request.getContextPath() + returnUrl);
			} else {
				response.sendRedirect(request.getContextPath() + ApiConstant.DASHBOARD);
			}

		} else {
			// Failed login attempt
			userDao.incrementFailedLoginAttempts(email);

			// Refresh attempt info
			LoginAttemptInfo updatedAttemptInfo = userDao.getLoginAttemptInfo(email);

			// Check if we should lock the account
			if (updatedAttemptInfo != null && updatedAttemptInfo.failedAttempts >= LoginAttemptUtil.MAX_FAILED_ATTEMPTS) {
				long lockUntilTime = LoginAttemptUtil.calculateLockExpirationTime();
				userDao.lockUserAccount(email, lockUntilTime);
				
				request.setAttribute("error", String.format(
					"Account locked after %d failed login attempts. Please try again in %d minutes.",
					LoginAttemptUtil.MAX_FAILED_ATTEMPTS,
					LoginAttemptUtil.ACCOUNT_LOCK_DURATION_MINUTES
				));
			} else if (updatedAttemptInfo != null) {
				int attemptsRemaining = LoginAttemptUtil.MAX_FAILED_ATTEMPTS - updatedAttemptInfo.failedAttempts;
				request.setAttribute("error", String.format(
					"Invalid email or password. (%d attempts remaining)",
					attemptsRemaining
				));
			} else {
				request.setAttribute("error", "Invalid email or password.");
			}

			request.setAttribute("returnUrl", returnUrl);
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

		try {
			String action = request.getParameter("action");
			if ("resendOTP".equals(action)) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					String email = (String) session.getAttribute("resetEmail");
					if (email != null && !email.isBlank()) {
						String otp = ForgetPasswordUtil.generateAndStoreOTP(email);
						String body = "Your SajhaKrishi password reset code is: " + otp + "\nThis code is valid for 10 minutes.";
						EmailUtil.sendEmail(email, "SajhaKrishi Password Reset Code", body);
						response.setStatus(HttpServletResponse.SC_OK);
						return;
					}
				}
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			String stepParam = request.getParameter("step");
			int step = 1;
			try { if (stepParam != null) step = Integer.parseInt(stepParam); } catch (NumberFormatException ex) { step = 1; }

			if (step == 1) {
				String email = request.getParameter("email");
				if (email == null || email.isBlank()) {
					request.setAttribute("error", "Please provide your email address.");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
				if (!userDao.isEmailExists(email)) {
					request.setAttribute("error", "No account found with that email.");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}

				String otp = ForgetPasswordUtil.generateAndStoreOTP(email);
				String body = "Your SajhaKrishi password reset code is: " + otp + "\nThis code is valid for 10 minutes.";
				EmailUtil.sendEmail(email, "SajhaKrishi Password Reset Code", body);

				HttpSession session = request.getSession();
				session.setAttribute("resetEmail", email);
				request.setAttribute("success", "A verification code has been sent to your email.");
				request.setAttribute("showStep", "2");
				request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
				return;
			} else if (step == 2) {
				String otp = request.getParameter("otp");
				HttpSession session = request.getSession(false);
				if (session == null) {
					request.setAttribute("error", "Session expired. Please start again.");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
				String email = (String) session.getAttribute("resetEmail");
				if (email == null) {
					request.setAttribute("error", "Session expired. Please start again.");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
				boolean ok = ForgetPasswordUtil.verifyOtp(email, otp);
				if (ok) {
					session.setAttribute("otpVerified", true);
					request.setAttribute("success", "Code verified. You may set a new password.");
					request.setAttribute("showStep", "3");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				} else {
					request.setAttribute("error", "Invalid or expired code.");
					request.setAttribute("showStep", "2");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
			} else if (step == 3) {
				HttpSession session = request.getSession(false);
				if (session == null || session.getAttribute("otpVerified") == null
						|| !(Boolean) session.getAttribute("otpVerified")) {
					request.setAttribute("error", "OTP verification required.");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
				String email = (String) session.getAttribute("resetEmail");
				String newPassword = request.getParameter("newPassword");
				String confirmPassword = request.getParameter("confirmPassword");
				if (newPassword == null || newPassword.isBlank() || !newPassword.equals(confirmPassword)) {
					request.setAttribute("error", "Passwords do not match or are invalid.");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
				com.SajhaKrishi.model.User user = userDao.getUserByEmail(email);
				if (user == null) {
					request.setAttribute("error", "User not found.");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
				boolean ok = userDao.updatePassword(user.getId(), newPassword);
				if (ok) {
					ForgetPasswordUtil.clearOtp(email);
					session.removeAttribute("resetEmail");
					session.removeAttribute("otpVerified");
					request.setAttribute("success", "Password reset successfully. You can now login.");
					request.setAttribute("showStep", "success");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				} else {
					request.setAttribute("error", "Failed to reset password. Try again.");
					request.setAttribute("showStep", "3");
					request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				request.setAttribute("error", "An error occurred: " + e.getMessage());
				request.getRequestDispatcher(PageConstant.FORGET_PASSWORD).forward(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void handleProfile(HttpServletRequest request, HttpServletResponse response)
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

		// Get fresh user data from DB to ensure latest info
		User freshUser = userDao.getUserById(user.getId());
		if (freshUser == null) {
			response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
			return;
		}

		request.setAttribute("user", freshUser);
		request.setAttribute("districts", DropdownConstant.DISTRICT);
		request.setAttribute("selectedNavItem", "profile");
		request.setAttribute("contentPage", PageConstant.PROFILE);
		request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
	}

	private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response)
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

		String fullName = request.getParameter("fullName");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		String district = request.getParameter("district");
		String password = request.getParameter("password");

		try {
			// Update basic user info
			user.setFullName(fullName);
			user.setPhoneNumber(phoneNumber);
			user.setAddress(address);
			user.setDistrict(district);

			boolean success = userDao.updateUser(user);

			// Update password if provided
			if (password != null && !password.isBlank()) {
				success = success && userDao.updatePassword(user.getId(), password);
			}

			if (success) {
				// Refresh user session with updated data
				User updatedUser = userDao.getUserById(user.getId());
				session.setAttribute(ApiConstant.USER_SESSION_KEY, updatedUser);

				request.setAttribute("user", updatedUser);
				request.setAttribute("districts", DropdownConstant.DISTRICT);
				request.setAttribute("successMessage", "Profile updated successfully!");
				request.getRequestDispatcher(PageConstant.PROFILE).forward(request, response);
			} else {
				User currentUser = userDao.getUserById(user.getId());
				request.setAttribute("user", currentUser);
				request.setAttribute("districts", DropdownConstant.DISTRICT);
				request.setAttribute("selectedNavItem", "profile");
				request.setAttribute("contentPage", PageConstant.PROFILE);
				request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.setAttribute("user", user);
			request.setAttribute("districts", DropdownConstant.DISTRICT);
			request.setAttribute("selectedNavItem", "profile");
			request.setAttribute("contentPage", PageConstant.PROFILE);
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
		}
	}

	private void handleUnlockUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Verify admin session
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
			return;
		}

		User currentUser = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);
		if (currentUser == null || currentUser.getRoleId() != 1) {
			response.sendRedirect(request.getContextPath() + ApiConstant.UNAUTHORIZED);
			return;
		}

		String email = request.getParameter("email");
		String successMessage = "";
		String errorMessage = "";

		try {
			if (email != null && !email.isEmpty()) {
				// Unlock the user account
				boolean success = userDao.unlockUserAccount(email);
				
				if (success) {
					successMessage = "User account has been unlocked successfully.";
				} else {
					errorMessage = "Failed to unlock user account. Please try again.";
				}
			} else {
				errorMessage = "Email parameter is missing.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "An error occurred: " + e.getMessage();
		}

		// Redirect back to user list with message
		java.util.List<User> users = userDao.getAllUsers();
		request.setAttribute("userList", users);
		request.setAttribute("selectedNavItem", "user");
		request.setAttribute("contentPage", PageConstant.USER_LIST);

		if (!successMessage.isEmpty()) {
			request.setAttribute("successMessage", successMessage);
		}
		if (!errorMessage.isEmpty()) {
			request.setAttribute("errorMessage", errorMessage);
		}

		request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
	}
}
