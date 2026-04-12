package com.SajhaKrishi.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.SajhaKrishi.constant.*;

/**
 * Servlet implementation class AuthController
 */
@WebServlet({ ApiConstant.LOGIN, ApiConstant.REGISTER, ApiConstant.LOGOUT })
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthController() {
		super();
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
			request.getRequestDispatcher(PageConstant.REGISTER_PAGE).forward(request, response);
		} else if (path.equals(ApiConstant.LOGOUT)) {
			handleLogout(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void handleLogin(HttpServletRequest request, HttpServletResponse response) {

	}
	
	protected void handleRegister(HttpServletRequest request, HttpServletResponse response) {

	}
	
	protected void handleLogout(HttpServletRequest request, HttpServletResponse response) {

	}

}
