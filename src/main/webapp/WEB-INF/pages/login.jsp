<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
	<div class="header-label">

		<h6 class="welcome-message">Welcome to Sajha Krishi</h6>
		<p>Sign in to your account to continue</p>
	</div>
	<div class="login-form-wrapper">
		<div class="form-header">
			<h6>Login</h6>
			<p>Enter your email and password to access your account</p>
			<c:if test="${not empty error}">
				<div class="error show">${error}</div>
			</c:if>
		</div>	
		<form action="login" method="post">
			<div class="form-group">
				<label for="email">Email Address</label> <input type="email"
					placeholder="Email" name="email" required /> <span class="input-error">Error
					Message</span>
			</div>

			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					placeholder="*******" name="password" required /> <span class="input-error">Error
					Message</span>
			</div>

			<div class="button-wrapper">
				<button type="submit">Sign In</button>
			</div>
		</form>
		<div class="other-link">
			<p>
				Don't have an account? <a href="/register.html">Sign up here</a>
			</p>
		</div>
	</div>

</body>	
</html>

		<%-- 
		<div class="app-name-logo-wrapper">
			<img src="/logo.png" alt="Sajha Krishi Logo" class="app-logo" />
			<h6 class="app-name">Sajha Krishi</h6>
		</div>aa
		--%>