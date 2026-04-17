<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
	<div class="container">
		<div class="header">
	 

			<h1>Join Sajha Krishi</h1>
			<p class="subtitle">Create an account to start renting
				agriculturer equipment</p>
		</div>

		<div class="card">
			<h2>Create Account</h2>
			<p>Sign up to get started with Sajha Krishi</p>
			<c:if test="${not empty error}">
				<div class="error show">${error}</div>
			</c:if>
			<form action="register" method="post">
				<div class="form-group">
					<label for="name">Full Name</label> <input type="text" id="name"
						name="fullName" placeholder="John Farmer" required>
				</div>

				<div class="form-group">
					<label for="phoneNumber">Phone Number</label> <input type="text"
						id="phoneNumber" name="phoneNumber" placeholder="9800000000" required>
				</div>

				<div class="form-group">
					<label for="email">Email Address</label> <input type="email"
						id="email" name="email" placeholder="you@example.com" required>
				</div>

				<div class="form-group">
					<label for="district">District</label> <select id="district"
						name="district" required>
						<option value="">Select district</option>
						<c:forEach var="name" items="${district}">
							<option value="${name}">${name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group">
					<label for="address">Full Address</label> <input type="text"
						id="address" name="address" placeholder="eg: Koteshwor" required>
				</div>

				<div class="form-group">
					<label for="password">Password</label> <input type="password"
						id="password" name="password" placeholder="••••••••" required>
				</div>

				<div class="form-group">
					<label for="confirmPassword">Confirm Password</label> <input
						type="password" name="confirmPassword" id="confirmPassword" placeholder="••••••••"
						required>
				</div>

				<button type="submit" class="btn">Create Account</button>
			</form>

			<div class="footer">
				Already have an account? <a href="/login">Sign in here</a>
			</div>
		</div>
	</div>
</body>
</html>

		<%-- <a href="/" class="logo">
				<div class="logo-icon">🌾</div> <span class="logo-text">Sajha
					Krishi</span>
			</a> --%>