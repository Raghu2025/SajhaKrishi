<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard | Sajha Krishi</title>
</head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/dashboard.css">
<body>
	<div class="dashboard-wrapper">
		<!-- MAIN CONTENT AREA -->
		<main class="main-content">
			<!-- Topbar -->
			<header class="topbar">
				<div class="welcome-text">
					<h1>
						Namaste, <span id="username">John Farmer</span>!
					</h1>
					<p class="subtitle">Welcome to your agricultural command
						center.</p>
				</div>
				<div class="role-badge">
					<!-- Dynamic Role: Kisan or Owner -->
					<span class="badge badge-available">Role: Kisan</span>
				</div>
			</header>

			<!-- STATS GRID -->
			<section class="stats-grid">
				<div class="stat-card">
					<h3>Active Rentals</h3>
					<p class="stat-value">2</p>
				</div>
				<div class="stat-card">
					<h3>Pending Requests</h3>
					<p class="stat-value">1</p>
				</div>
			</section>

			<!-- RECENT ACTIVITY TABLE -->
			<section class="activity-section">
				<div class="card">
					<div class="card-header">
						<h2>Recent Transactions</h2>
						<button class="btn btn-primary">View All</button>
					</div>
					<table class="dashboard-table">
						<thead>
							<tr>
								<th>Item</th>
								<th>Date</th>
								<th>Status</th>
								<th>Amount</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Mini Tractor</td>
								<td>May 01, 2026</td>
								<td><span class="status-tag confirmed">Confirmed</span></td>
								<td>NPR 1,200</td>
							</tr>
							<tr>
								<td>Power Tiller</td>
								<td>Apr 28, 2026</td>
								<td><span class="status-tag pending">Pending</span></td>
								<td>NPR 800</td>
							</tr>
						</tbody>
					</table>
				</div>
			</section>
		</main>
	</div>

</body>
</html>