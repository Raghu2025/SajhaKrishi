<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail | Sajha Krishi</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/equipment-detail.css">
</head>
<body>
<jsp:include page="public-navbar.jsp" />
		<div class="container header-wrapper">
			<a href="/browse.html" class="back-link"> Back to Browse</a>

<!-- 			<div class="logo">
				<div class="logo-box">🌾</div>
				<div class="logo-text">Sajha Krishi</div>
			</div> -->
		</div>
	<div class="container">

		<div class="grid">

			<!-- LEFT -->
			<div>
				<!-- <div class="image-box">🚜</div> -->

				<h1 class="product-title">Tractor</h1>
				<div class="category">Heavy Equipment</div>
				<span class="badge">Excellent Condition</span>

				<p class="desc">High-performance tractor designed for plowing,
					hauling, and large-scale farming operations. Reliable,
					fuel-efficient, and easy to operate.</p>

				<h3>Key Features:</h3>
				<ul class="features">
					<li>High engine power</li>
					<li>Fuel efficient</li>
					<li>Easy handling</li>
					<li>Durable build</li>
				</ul>
			</div>

			<!-- RIGHT -->
			<div class="card">

				<div class="price">Rs. 1500 / day</div>
				<div class="availability"> Available for rent</div>

				<form>

					<div class="form-group">
						<label for="start-date">Start Date</label> <input id="start-date"
							type="date" required>
					</div>

					<div class="form-group">
						<label for="end-date">End Date</label> <input id="end-date"
							type="date" required>
					</div>

					<div class="price-box">
						Duration: 3 days <br> Rs. 1500 × 3 = Rs. 4500
					</div>

					<div class="total">
						<span>Total</span> <span style="color: var(--color-primary)">Rs.
							4500</span>
					</div>

					<div class="button-wrapper">
						<button class="btn btn-primary submit-btn">Confirm Rental</button>
					</div>

					<p class="note">Rental confirmation will be sent to your email</p>

				</form>
			</div>

		</div>

	</div>

</body>
</html>