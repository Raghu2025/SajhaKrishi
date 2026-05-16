<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.SajhaKrishi.constant.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/user-list.css">
<style>

</style>
</head>
<body>
	<div class="inventory-page">
		<div class="container">
			<div class="page-header">
				<h2>User List</h2>
				<p>Manage application users and permissions</p>
			</div>

			<c:if test="${not empty successMessage}">
				<div class="alert-message success">${successMessage}</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert-message error">${errorMessage}</div>
			</c:if>

			<div class="table-card">
				<div class="table-wrapper">
					<table>
						<thead>
							<tr>
								<th>User ID</th>
								<th>Full Name</th>
								<th>Email</th>
								<th>District</th>
								<th>Contact</th>
								<th>Role</th>
								<th>Status</th>
								<th>Lock Status</th>
								<th>Failed Attempts</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="u" items="${userList}">
								<tr>
									<td class="id-tag"><strong>#${u.id}</strong></td>
									<td class="name">${u.fullName}</td>
									<td class="district">${u.email}</td>
									<td class="district">${u.district}</td>
									<td class="location"><small
										style="color: var(--color-text-light);">${u.phoneNumber}</small>
									</td>
									<td class="category">${u.roleName}</td>
									<td><span class="status-pill ${u.status.toLowerCase()}">${u.status}</span></td>
									<td>
										<c:choose>
											<c:when test="${u.isLocked == 'Y'}">
												<span class="lock-status-locked">Locked</span>
											</c:when>
											<c:otherwise>
												<span class="lock-status-active">Active</span>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:if test="${u.failedLoginAttempts > 0}">
											<span class="failed-attempts">${u.failedLoginAttempts}/${5}</span>
										</c:if>
										<c:if test="${u.failedLoginAttempts == 0}">
											<span>0/5</span>
										</c:if>
									</td>
									<td>
										<div class="action-buttons">
											<c:if test="${u.isLocked == 'Y'}">
												<button class="btn-unlock" onclick="unlockUser(${u.id}, '${u.email}')">Unlock Account</button>
											</c:if>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script>
		function unlockUser(userId, email) {
			if (confirm('Are you sure you want to unlock this account?')) {
				const form = document.createElement('form');
				form.method = 'POST';
				form.action = '${pageContext.request.contextPath}${ApiConstant.ADMIN_USERS}';

				const actionInput = document.createElement('input');
				actionInput.type = 'hidden';
				actionInput.name = 'action';
				actionInput.value = 'unlock';

				const userIdInput = document.createElement('input');
				userIdInput.type = 'hidden';
				userIdInput.name = 'userId';
				userIdInput.value = userId;

				const emailInput = document.createElement('input');
				emailInput.type = 'hidden';
				emailInput.name = 'email';
				emailInput.value = email;

				form.appendChild(actionInput);
				form.appendChild(userIdInput);
				form.appendChild(emailInput);
				document.body.appendChild(form);
				form.submit();
			}
		}
	</script>
</body>
</html>