<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.SajhaKrishi.constant.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard | Sajha Krishi</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
	integrity="sha512-iecdLmaskl7CVJkEZSMUkrQ6usznuy8+hQAF0o+w0nfr/ogefZeZl+0fhgwvZcvCelRur4NEO0rUfGlsvTJ40g=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
	<div class="dashboard-wrapper">
		<main class="main-content">
			<header class="topbar">
				<div class="welcome-text">
					<h1>
						Namaste, <span id="username">${user.fullName}</span>!
					</h1>
					<p class="subtitle">Welcome to your agricultural command center</p>
				</div>
			</header>

			<!-- STATS GRID -->
			<section class="stats-grid">
				<div class="stat-card">
					<div class="stat-header">
						<h3>Active Rentals</h3>
						<i class="fas fa-check-circle"></i>
					</div>
					<p class="stat-value">${activeRentals}</p>
					<p class="stat-label">Equipment in use</p>
				</div>

				<div class="stat-card">
					<div class="stat-header">
						<h3>Pending Requests</h3>
						<i class="fas fa-hourglass-half"></i>
					</div>
					<p class="stat-value">${pendingRequests}</p>
					<p class="stat-label">Awaiting confirmation</p>
				</div>
				<c:if test="${isAdmin}">
					<div class="stat-card">
						<div class="stat-header">
							<h3>Total User</h3>
							<i class="fa-solid fa-user"></i>
						</div>
						<p class="stat-value">${totalUser}</p>
					</div>
					<div class="stat-card">
						<div class="stat-header">
							<h3>Total Equipment</h3>
							<i class="fa-solid fa-tractor"></i>
						</div>
						<p class="stat-value">${totalEquipment}</p>
					</div>
				</c:if>
			</section>

			<!-- RECENT ACTIVITY SECTION -->
			<section class="activity-section">
				<div class="card">
					<div class="card-header">
						<h2>
							<i class="fas fa-clock"></i> Recent Bookings
						</h2>
						<a href="${pageContext.request.contextPath}/booking/list"
							class="btn btn-primary"> <i class="fas fa-list"></i> View All
							Bookings
						</a>
					</div>

					<c:if test="${empty recentTransactions}">
						<div class="empty-state">
							<i
								style="font-size: 48px; color: #ccc; display: block; margin-bottom: 20px;"
								class="fas fa-inbox"></i>
							<p>No recent bookings. Start by browsing available equipment!</p>
							<a
								href="${pageContext.request.contextPath}${ApiConstant.KISSAN_EQUIPMENT}"
								class="btn btn-primary"> <i class="fas fa-search"></i>
								Browse Equipment
							</a>
						</div>
					</c:if>

					<c:if test="${not empty recentTransactions}">
						<div style="overflow-x: auto;">
							<table class="dashboard-table">
								<thead>
									<tr>
										<th>Booking ID</th>
										<th>Equipment</th>
										<th>Date</th>
										<th>Status</th>
										<th>Amount</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="booking" items="${recentTransactions}">
										<tr>
											<td><strong>#${booking.id}</strong></td>
											<td>${booking.equipmentName}</td>
											<td>${booking.startDate}</td>
											<td><c:choose>
													<c:when test="${booking.statusFlag == 'PENDING'}">
														<span class="status-tag pending"><i
															class="fas fa-hourglass-half"></i> Pending</span>
													</c:when>
													<c:when test="${booking.statusFlag == 'CONFIRMED'}">
														<span class="status-tag confirmed"><i
															class="fas fa-check-circle"></i> Confirmed</span>
													</c:when>
													<c:when test="${booking.statusFlag == 'COMPLETED'}">
														<span class="status-tag completed"><i
															class="fas fa-check"></i> Completed</span>
													</c:when>
													<c:when test="${booking.statusFlag == 'CANCELLED'}">
														<span class="status-tag cancelled"><i
															class="fas fa-times"></i> Cancelled</span>
													</c:when>
												</c:choose></td>
											<td><strong>Rs. ${booking.totalPrice}</strong></td>
											<td><a
												href="${pageContext.request.contextPath}/booking/details?id=${booking.id}"
												class="btn btn-sm btn-outline"> <i class="fas fa-eye"></i>
													View
											</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
				</div>
			</section>

			<!-- QUICK ACTIONS -->
			<section class="quick-actions">
				<div class="action-card">
					<div class="action-icon">
						<i class="fas fa-plus-circle"></i>
					</div>
					<h3>New Booking</h3>
					<p>Rent equipment for your farm</p>
					<a href="${pageContext.request.contextPath}/kisan/equipment"
						class="btn btn-primary"> <i class="fas fa-search"></i> Browse
					</a>
				</div>

				<div class="action-card">
					<div class="action-icon">
						<i class="fas fa-list"></i>
					</div>
					<h3>View Bookings</h3>
					<p>Manage all your bookings</p>
					<a
						href="${pageContext.request.contextPath}${ApiConstant.BOOKING}${ApiConstant.LIST}"
						class="btn btn-primary"> <i class="fas fa-arrow-right"></i> Go
						to Bookings
					</a>
				</div>
			</section>
		</main>
	</div>

</body>
</html>