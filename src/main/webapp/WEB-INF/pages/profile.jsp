<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.SajhaKrishi.constant.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Profile | Sajha Krishi</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
	integrity="sha512-iecdLmaskl7CVJkEZSMUkrQ6usznuy8+hQAF0o+w0nfr/ogefZeZl+0fhgwvZcvCelRur4NEO0rUfGlsvTJ40g=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
	<div class="profile-wrapper">
		<!-- SUCCESS ALERT -->
		<c:if test="${not empty successMessage}">
			<div class="alert-success show">
				<i class="fas fa-check-circle"></i> ${successMessage}
			</div>
		</c:if>

		<!-- ERROR ALERT -->
		<c:if test="${not empty error}">
			<div class="error show">${error}</div>
		</c:if>

		<!-- PROFILE FORM -->
		<div class="profile-form-section">
			<h2>
				<i class="fas fa-edit"></i> Edit Profile Information
			</h2>

			<form method="POST" action="${pageContext.request.contextPath}/profile">
				<div class="form-row full">
					<div class="form-group">
						<label for="fullName">Full Name</label> <input type="text"
							id="fullName" name="fullName" value="${user.fullName}"
							required>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="email">Email Address</label> <input type="email"
							id="email" name="email" value="${user.email}" readonly
							style="background-color: #f5f5f5; cursor: not-allowed;">
						<small style="color: var(--color-text-muted);">Email cannot
							be changed</small>
					</div>
					<div class="form-group">
						<label for="phoneNumber">Phone Number</label> <input
							type="tel" id="phoneNumber" name="phoneNumber"
							value="${user.phoneNumber}" required>
					</div>
				</div>

				<div class="form-row full">
					<div class="form-group">
						<label for="address">Address</label> <input type="text"
							id="address" name="address" value="${user.address}" required>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="district">District</label> <select id="district"
							name="district" required>
							<option value="">Select District</option>
							<c:forEach var="dist" items="${districts}">
								<option value="${dist}" ${dist == user.district ? 'selected' : ''}>
									${dist}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="password">New Password (Leave blank to keep
							current)</label> <input type="password" id="password" name="password"
							placeholder="Enter new password if you want to change it">
					</div>
				</div>

				<div class="form-actions">
					<button type="submit" class="btn btn-primary">
						<i class="fas fa-save"></i> Save Changes
					</button>
				</div>
			</form>
		</div>

		<!-- READ-ONLY INFO SECTION -->
		<div style="margin-top: 32px;">
			<div class="profile-form-section">
				<h2>
					<i class="fas fa-info-circle"></i> Account Information
				</h2>
				<div class="info-grid">
					<div class="info-item">
						<div class="info-label">User ID</div>
						<div class="info-value">#${user.id}</div>
					</div>
					<div class="info-item">
						<div class="info-label">Account Status</div>
						<div class="info-value">${user.status}</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>