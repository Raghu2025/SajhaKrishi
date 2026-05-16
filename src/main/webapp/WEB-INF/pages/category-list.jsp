<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categories | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/category-list.css">
</head>
<body>
	<div class="category-page">
		<div class="container">
			<!-- HEADER -->
			<div class="page-header">
				<div class="header-top">
					<div>
						<h2>Equipment Categories</h2>
						<p>Manage equipment categories</p>
					</div>
					<a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-primary">
						<i class="fa-solid fa-plus"></i> Add New Category
					</a>
				</div>
			</div>

			<!-- SUCCESS/ERROR MESSAGES -->
			<c:if test="${not empty successMessage}">
				<div class="alert alert-success show">
					<i class="fa-solid fa-check-circle"></i>
					${successMessage}
					<%
					session.removeAttribute("successMessage");
					%>
				</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger show">
					<i class="fa-solid fa-exclamation-circle"></i>
					${errorMessage}
					<%
					session.removeAttribute("errorMessage");
					%>
				</div>
			</c:if>

			<!-- TABLE CARD -->
			<div class="table-card">
				<div class="table-wrapper">
					<c:if test="${empty categoryList}">
						<div class="empty-state">
							<i class="fa-solid fa-inbox"></i>
							<p>No categories found</p>
							<a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-primary">
								Create First Category
							</a>
						</div>
					</c:if>

					<c:if test="${not empty categoryList}">
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Status</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="category" items="${categoryList}">
									<tr>
										<td class="name">${category.name}</td>
										<td>
											<span
												class="status-pill ${category.status == 'A' ? 'active' : 'inactive'}">
												${category.status == 'A' ? 'Active' : 'Inactive'}
											</span>
										</td>
										<td>
											<div class="actions">
												<button
													onclick="location.href='${pageContext.request.contextPath}/admin/category/edit?id=${category.id}'"
													class="btn btn-outline" title="Edit">
													<i class="fa-solid fa-pen-to-square"></i> Edit
												</button>
												<button
													onclick="if(confirm('Are you sure you want to delete this category?')) location.href='${pageContext.request.contextPath}/admin/category/delete?id=${category.id}'"
													class="btn btn-danger" title="Delete">
													<i class="fa-solid fa-trash"></i> Delete
												</button>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>

		</div>

	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/js/all.min.js"></script>
	<script>
		// Auto-dismiss alerts after 5 seconds
		const alerts = document.querySelectorAll('.alert');
		alerts.forEach(alert => {
			setTimeout(() => {
				alert.classList.remove('show');
				setTimeout(() => alert.remove(), 300);
			}, 5000);
		});
	</script>
</body>
</html>
