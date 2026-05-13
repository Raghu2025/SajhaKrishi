<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bookings | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/booking-list.css">
</head>
<body>
	<div class="bookings-page">
		<div class="container">
			<!-- HEADER -->
			<div class="page-header">
				<h2>My Bookings</h2>
				<p>Track and manage all your equipment bookings</p>
			</div>

			<!-- FILTER SECTION -->
			<div class="filter-section">
				<div class="filter-group">
					<button class="filter-btn active" data-filter="all">
						<i class="fa fa-list"></i> All Bookings
					</button>
					<button class="filter-btn" data-filter="Pending">
						<i class="fa fa-hourglass-half"></i> Pending
					</button>
					<button class="filter-btn" data-filter="Confirmed">
						<i class="fa fa-check-circle"></i> Confirmed
					</button>
					<button class="filter-btn" data-filter="Completed">
						<i class="fa fa-checkmark"></i> Completed
					</button>
					<button class="filter-btn" data-filter="Cancelled">
						<i class="fa fa-times-circle"></i> Cancelled
					</button>
				</div>
			</div>

			<!-- TABLE CARD -->
			<div class="table-card">
				<c:if test="${empty bookingList}">
					<div class="empty-state">
						<div class="empty-icon">
							<i class="fa fa-inbox"></i>
						</div>
						<h3>No Bookings Yet</h3>
						<p>You haven't made any bookings. Browse available equipment to
							get started!</p>
						<a href="${pageContext.request.contextPath}/kisan/equipment/list"
							class="btn btn-primary">
							<i class="fa fa-search"></i> Browse Equipment
						</a>
					</div>
				</c:if>

				<c:if test="${not empty bookingList}">
					<div class="table-wrapper">
						<table>
							<thead>
								<tr>
									<th>Booking ID</th>
									<th>Equipment</th>
									<th>Dates</th>
									<th>Duration</th>
									<th>Total Price</th>
									<th>Status</th>
									<th>Payment Status</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="booking" items="${bookingList}">
									<tr class="booking-row" data-status="${booking.status}">
										<td class="booking-id">
											<span class="badge">#${booking.id}</span>
										</td>
										<td class="equipment-name">Equipment ${booking.equipmentId}</td>
										<td class="dates">
											<div class="date-range">
												<i class="fa fa-calendar-days"></i> ${booking.startDate}
												<br> <i class="fa fa-calendar-check"></i>
												${booking.endDate}
											</div>
										</td>
										<td class="duration">
											<span class="duration-badge">${booking.totalDays} days</span>
										</td>
										<td class="price">
											<strong>Rs. ${booking.totalPrice}</strong>
										</td>
										<td class="status">
											<span
												class="status-pill ${booking.status.toLowerCase()}">
												<c:choose>
													<c:when test="${booking.status == 'Pending'}">
														<i class="fa fa-hourglass-half"></i> ${booking.status}
													</c:when>
													<c:when test="${booking.status == 'Confirmed'}">
														<i class="fa fa-check-circle"></i> ${booking.status}
													</c:when>
													<c:when test="${booking.status == 'Completed'}">
														<i class="fa fa-checkmark"></i> ${booking.status}
													</c:when>
													<c:when test="${booking.status == 'Cancelled'}">
														<i class="fa fa-times-circle"></i> ${booking.status}
													</c:when>
													<c:otherwise>
														<i class="fa fa-info-circle"></i> ${booking.status}
													</c:otherwise>
												</c:choose>
											</span>
										</td>
										<td class="payment-status">
											<span
												class="payment-badge ${booking.paymentStatus.toLowerCase()}">
												<c:choose>
													<c:when test="${booking.paymentStatus == 'Paid'}">
														<i class="fa fa-credit-card"></i> ${booking.paymentStatus}
													</c:when>
													<c:when test="${booking.paymentStatus == 'Unpaid'}">
														<i class="fa fa-circle-xmark"></i> ${booking.paymentStatus}
													</c:when>
													<c:otherwise>
														<i class="fa fa-undo"></i> ${booking.paymentStatus}
													</c:otherwise>
												</c:choose>
											</span>
										</td>
										<td class="actions">
											<div class="action-buttons">
												<button
													onclick="location.href='${pageContext.request.contextPath}/booking/details?id=${booking.id}'"
													class="btn btn-outline btn-sm" title="View Details">
													<i class="fa fa-eye"></i> View
												</button>
												<c:if test="${booking.status != 'Cancelled' && booking.status != 'Completed'}">
													<button
														onclick="cancelBooking(${booking.id})"
														class="btn btn-danger btn-sm" title="Cancel Booking">
														<i class="fa fa-times"></i> Cancel
													</button>
												</c:if>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>

		</div>
	</div>

	<script>
		// Filter functionality
		document.querySelectorAll('.filter-btn').forEach(btn => {
			btn.addEventListener('click', function() {
				document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
				this.classList.add('active');

				const filter = this.getAttribute('data-filter');
				const rows = document.querySelectorAll('.booking-row');

				rows.forEach(row => {
					if (filter === 'all' || row.getAttribute('data-status') === filter) {
						row.style.display = '';
					} else {
						row.style.display = 'none';
					}
				});
			});
		});

		// Cancel booking function
		function cancelBooking(bookingId) {
			if (confirm('Are you sure you want to cancel this booking?')) {
				location.href = '${pageContext.request.contextPath}/booking/delete?id=' + bookingId;
			}
		}
	</script>
</body>
</html>
