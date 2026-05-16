<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.SajhaKrishi.constant.*"%>
<!DOCTYPE html>
<html lang="en">
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
					<a href="?type=${currentType}"
						class="btn-tab ${empty currentStatus ? 'active' : ''}"> <i
						class="fa fa-list"></i> All
					</a> <a href="?type=${currentType}&status=PENDING"
						class="btn-tab ${currentStatus == 'PENDING' ? 'active' : ''}">
						<i class="fa fa-hourglass-half"></i> Pending
					</a> <a href="?type=${currentType}&status=CONFIRMED"
						class="btn-tab ${currentStatus == 'CONFIRMED' ? 'active' : ''}">
						<i class="fa fa-check-circle"></i> Confirmed
					</a> <a href="?type=${currentType}&status=COMPLETED"
						class="btn-tab ${currentStatus == 'COMPLETED' ? 'active' : ''}">
						<i class="fa fa-check"></i> Completed
					</a> <a href="?type=${currentType}&status=CANCELLED"
						class="btn-tab ${currentStatus == 'CANCELLED' ? 'active' : ''}">
						<i class="fa fa-times-circle"></i> Cancelled
					</a>
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
						<p>You haven't made any bookings. Browse available equipment
							to get started!</p>
						<a
							href="${pageContext.request.contextPath}${ApiConstant.KISSAN_EQUIPMENT}"
							class="btn btn-primary"> <i class="fa fa-search"></i> Browse
							Equipment
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
									<tr class="booking-row" data-status="${booking.statusFlag}">
										<td class="booking-id"><span class="badge">#${booking.id}</span>
										</td>
										<td class="equipment-name">${booking.equipmentName}</td>
										<td class="dates">
											<div class="date-range">
												<div>
													<i class="fa fa-calendar-days"></i> ${booking.startDate}
												</div>
												<div>
													<i class="fa fa-calendar-check"></i> ${booking.endDate}
												</div>

											</div>
										</td>
										<td class="duration"><span class="duration-badge">${booking.totalDays}
												days</span></td>
										<td class="price"><strong>Rs.
												${booking.totalPrice}</strong></td>
										<td class="status"><span
											class="status-pill ${not empty booking.statusFlag ? booking.statusFlag.toLowerCase() : ''}">
												<c:choose>
													<c:when test="${booking.statusFlag == 'PENDING'}">
														<i class="fa fa-hourglass-half"></i> Pending
            </c:when>
													<c:when test="${booking.statusFlag == 'CONFIRMED'}">
														<i class="fa fa-check-circle"></i> Confirmed
            </c:when>
													<c:when test="${booking.statusFlag == 'COMPLETED'}">
														<i class="fa fa-check-circle"></i> Completed
            </c:when>
													<c:when test="${booking.statusFlag == 'CANCELLED'}">
														<i class="fa fa-times-circle"></i> Cancelled
            </c:when>
													<c:otherwise>
														<i class="fa fa-info-circle"></i> ${booking.statusFlag}
            </c:otherwise>
												</c:choose>
										</span></td>
										<td class="payment-status"><span
											class="payment-badge ${booking.paymentStatus.toLowerCase()}">
												<c:choose>
													<c:when test="${booking.paymentStatus == 'PAID'}">
														<i class="fa fa-credit-card"></i> ${booking.paymentStatus}
													</c:when>
													<c:when test="${booking.paymentStatus == 'UNPAID'}">
														<i class="fa fa-circle-xmark"></i> ${booking.paymentStatus}
													</c:when>
													<c:otherwise>
														<i class="fa fa-undo"></i> ${booking.paymentStatus}
													</c:otherwise>
												</c:choose>
										</span></td>
										<td class="actions">
											<div class="action-buttons">
												<%-- View button (always) --%>
												<button
													onclick="location.href='${pageContext.request.contextPath}/booking/details?id=${booking.id}'"
													class="btn btn-outline btn-sm">
													<i class="fa fa-eye"></i> View
												</button>

												<%-- PENDING: can Confirm or Cancel --%>
												<c:if test="${booking.statusFlag == 'PENDING'}">
													<c:if test="${currentType == 'owner'}">
														<button data-booking-id="${booking.id}" data-status="CONFIRMED" onclick="updateStatus(this)"
															class="btn btn-primary btn-sm">
															<i class="fa fa-check"></i> Confirm
														</button>
													</c:if>
													<button data-booking-id="${booking.id}" data-status="CANCELLED" onclick="updateStatus(this)"
														class="btn btn-danger btn-sm">
														<i class="fa fa-times"></i> Cancel
													</button>
												</c:if>

												<c:if test="${booking.statusFlag == 'CONFIRMED'}">
													<c:if test="${currentType == 'owner'}">
														<button data-booking-id="${booking.id}" data-status="COMPLETED" onclick="updateStatus(this)"
															class="btn btn-primary btn-sm">
															<i class="fa fa-check-circle"></i> Complete
														</button>
														<button data-booking-id="${booking.id}" data-status="CANCELLED" onclick="updateStatus(this)"
															class="btn btn-danger btn-sm">
															<i class="fa fa-times"></i> Cancel
														</button>
													</c:if>
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
		function updateStatus(button) {
			const bookingId = button.getAttribute('data-booking-id');
			const newStatus = button.getAttribute('data-status');
			const actionLabel = newStatus.charAt(0) + newStatus.slice(1).toLowerCase();
			if (!confirm('Are you sure you want to mark this booking as ' + actionLabel + '?')) {
				return;
			}

			const type = '${currentType}';
			location.href = '${pageContext.request.contextPath}/booking/update?bookingId=' + bookingId
					+ '&status=' + encodeURIComponent(newStatus)
					+ '&type=' + encodeURIComponent(type);
		}
	</script>
</body>
</html>
