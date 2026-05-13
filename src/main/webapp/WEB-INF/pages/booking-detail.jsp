<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<jsp:useBean id="api" class="com.SajhaKrishi.constant.ApiConstant"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Booking Details | SajhaKrishi</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link
	href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=DM+Sans:wght@300;400;500;600&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/booking-detail.css">
</head>
<body>

	<!-- BREADCRUMB -->
	<div class="breadcrumb-bar">
		<div class="container">
			<a href="${pageContext.request.contextPath}/booking/list"
				class="back-link"> <i class="fa fa-arrow-left"></i> Back to
				Bookings
			</a> <span class="breadcrumb-sep">/</span> <span class="breadcrumb-cat">Booking
				#${booking.id}</span>
		</div>
	</div>

	<!-- MAIN CONTENT -->
	<div class="main-wrapper">
		<div class="container main-grid">

			<!-- LEFT COLUMN — Booking Summary -->
			<div class="left-col">

				<!-- Header Card -->
				<div class="booking-header-card">
					<div class="booking-title-section">
						<h1 class="booking-title">Booking #${booking.id}</h1>
						<p class="booking-date">Booked on: ${booking.bookedAt}</p>
					</div>

					<div class="status-badges">
						<span
							class="booking-status status-${booking.status.toLowerCase()}">
							<i class="fa fa-info-circle"></i> ${booking.status}
						</span>
						<span
							class="payment-status payment-${booking.paymentStatus.toLowerCase()}">
							<i
							class="fa ${booking.paymentStatus == 'Paid' ? 'fa-check-circle' : 'fa-hourglass-half'}"></i>
							${booking.paymentStatus}
						</span>
					</div>
				</div>

				<!-- Dates & Duration Card -->
				<div class="info-card">
					<h3 class="card-title">
						<i class="fa fa-calendar"></i> Rental Period
					</h3>
					<div class="info-grid">
						<div class="info-item">
							<span class="info-label">Start Date</span>
							<span class="info-value">${booking.startDate}</span>
						</div>
						<div class="info-item">
							<span class="info-label">End Date</span>
							<span class="info-value">${booking.endDate}</span>
						</div>
						<div class="info-item">
							<span class="info-label">Duration</span>
							<span class="info-value">${booking.totalDays} days</span>
						</div>
					</div>
				</div>

				<!-- Equipment Details Card -->
				<div class="info-card">
					<h3 class="card-title">
						<i class="fa fa-tractor"></i> Equipment Details
					</h3>
					<div class="equipment-info">
						<c:if test="${not empty equipment}">
							<div class="equipment-header">
								<c:if test="${not empty equipment.imagePath}">
									<div class="equipment-image">
										<img
											src="${pageContext.request.contextPath}/${equipment.imagePath}"
											onerror="this.src='${pageContext.request.contextPath}/assets/noImage.svg';"
											alt="${equipment.name}" />
									</div>
								</c:if>
								<div class="equipment-basic">
									<h4>${equipment.name}</h4>
									<p class="category">
										<i class="fa fa-tag"></i> ${equipment.categoryName}
									</p>
								</div>
							</div>

							<div class="equipment-details">
								<div class="detail-row">
									<span class="detail-label">Brand</span>
									<span class="detail-value">${equipment.brand}</span>
								</div>
								<div class="detail-row">
									<span class="detail-label">Condition</span>
									<span class="detail-value">${equipment.condition}</span>
								</div>
								<div class="detail-row">
									<span class="detail-label">Location</span>
									<span class="detail-value">${equipment.district}, ${equipment.municipality}</span>
								</div>
								<div class="detail-row">
									<span class="detail-label">Address</span>
									<span class="detail-value">${equipment.address}</span>
								</div>
								<c:if test="${not empty equipment.description}">
									<div class="detail-row">
										<span class="detail-label">Description</span>
										<span class="detail-value">${equipment.description}</span>
									</div>
								</c:if>
							</div>
						</c:if>
					</div>
				</div>

				<!-- Pickup Details Card -->
				<div class="info-card">
					<h3 class="card-title">
						<i class="fa fa-location-dot"></i> Pickup Details
					</h3>
					<div class="pickup-info">
						<p class="pickup-address">
							<strong>Pickup Address:</strong><br> ${booking.pickupAddress}
						</p>
						<c:if test="${not empty booking.notes}">
							<p class="booking-notes">
								<strong>Special Notes:</strong><br> ${booking.notes}
							</p>
						</c:if>
					</div>
				</div>

			</div>

			<!-- RIGHT COLUMN — Pricing & Actions -->
			<div class="right-col">

				<!-- Price Summary Card -->
				<div class="price-summary-card">
					<h3 class="card-title">
						<i class="fa fa-calculator"></i> Price Summary
					</h3>

					<div class="price-breakdown">
						<div class="price-row">
							<span class="price-label">Rental Rate (per day)</span>
							<span class="price-value">Rs. ${booking.pricePerDay}</span>
						</div>
						<div class="price-row">
							<span class="price-label">Duration</span>
							<span class="price-value">${booking.totalDays} days</span>
						</div>
						<div class="price-divider"></div>
						<div class="price-row">
							<span class="price-label">Subtotal</span>
							<span class="price-value">Rs. ${booking.totalPrice}</span>
						</div>
						<c:if test="${booking.depositAmount > 0}">
							<div class="price-row">
								<span class="price-label">Deposit (Refundable)</span>
								<span class="price-value">${booking.depositAmount}</span>
							</div>
						</c:if>
						<div class="price-divider"></div>
						<div class="price-row total">
							<span class="price-label">Total Amount</span>
							<span class="price-value-total">Rs.
								${booking.totalPrice + booking.depositAmount}</span>
						</div>
					</div>

					<!-- Status Information -->
					<div class="status-section">
						<c:choose>
							<c:when test="${booking.paymentStatus == 'Paid'}">
								<div class="status-info success">
									<i class="fa fa-check-circle"></i>
									<div>
										<strong>Payment Confirmed</strong>
										<p>Your payment has been received and processed</p>
									</div>
								</div>
							</c:when>
							<c:when test="${booking.paymentStatus == 'Unpaid'}">
								<div class="status-info warning">
									<i class="fa fa-exclamation-triangle"></i>
									<div>
										<strong>Payment Pending</strong>
										<p>Please complete your payment to confirm the booking</p>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="status-info info">
									<i class="fa fa-undo"></i>
									<div>
										<strong>Refunded</strong>
										<p>Your booking has been refunded</p>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>

					<!-- Actions -->
					<div class="action-buttons">
						<c:choose>
							<c:when
								test="${booking.status == 'Pending' && booking.paymentStatus == 'Unpaid'}">
								<button class="btn btn-primary" onclick="proceedToPayment()">
									<i class="fa fa-credit-card"></i> Pay Now
								</button>
								<button class="btn btn-outline" onclick="cancelBooking()">
									<i class="fa fa-times"></i> Cancel Booking
								</button>
							</c:when>
							<c:when test="${booking.status != 'Completed' && booking.status != 'Cancelled'}">
								<button class="btn btn-outline" onclick="cancelBooking()">
									<i class="fa fa-times"></i> Cancel Booking
								</button>
								<button class="btn btn-primary">
									<i class="fa fa-print"></i> Print Booking
								</button>
							</c:when>
							<c:otherwise>
								<button class="btn btn-primary" onclick="newBooking()">
									<i class="fa fa-plus"></i> New Booking
								</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<!-- Timeline Card -->
				<div class="timeline-card">
					<h3 class="card-title">
						<i class="fa fa-clock"></i> Booking Timeline
					</h3>
					<div class="timeline">
						<div
							class="timeline-item ${booking.status != 'Pending' ? 'completed' : 'active'}">
							<div class="timeline-marker"></div>
							<div class="timeline-content">
								<strong>Booking Created</strong>
								<p>${booking.bookedAt}</p>
							</div>
						</div>
						<div
							class="timeline-item ${booking.status == 'Confirmed' || booking.status == 'Completed' ? 'completed' : ''}">
							<div class="timeline-marker"></div>
							<div class="timeline-content">
								<strong>Confirmed</strong>
								<p>
									<c:if test="${booking.status == 'Confirmed'}">Ready for pickup</c:if>
									<c:if test="${booking.status == 'Completed'}">Completed</c:if>
									<c:if test="${booking.status == 'Pending'}">Awaiting confirmation</c:if>
									<c:if test="${booking.status == 'Cancelled'}">Cancelled</c:if>
								</p>
							</div>
						</div>
						<div
							class="timeline-item ${booking.status == 'Completed' ? 'completed' : ''}">
							<div class="timeline-marker"></div>
							<div class="timeline-content">
								<strong>Rental Period</strong>
								<p>${booking.startDate} to ${booking.endDate}</p>
							</div>
						</div>
						<div
							class="timeline-item ${booking.status == 'Completed' ? 'completed' : ''}">
							<div class="timeline-marker"></div>
							<div class="timeline-content">
								<strong>Completed</strong>
								<p>
									<c:if test="${booking.status != 'Completed'}">Pending</c:if>
									<c:if test="${booking.status == 'Completed'}">Booking finished</c:if>
								</p>
							</div>
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>

	<script>
		function proceedToPayment() {
			alert('Payment processing will be integrated here');
			// window.location.href = '${pageContext.request.contextPath}/booking/payment?id=${booking.id}';
		}

		function cancelBooking() {
			if (confirm('Are you sure you want to cancel this booking? This action cannot be undone.')) {
				window.location.href = '${pageContext.request.contextPath}/booking/delete?id=${booking.id}';
			}
		}

		function newBooking() {
			window.location.href = '${pageContext.request.contextPath}/kisan/equipment/list';
		}
	</script>
</body>
</html>
