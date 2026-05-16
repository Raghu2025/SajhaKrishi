<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/category-add.css">
</head>
<body>
	<div class="container" style="margin-top: 2rem;">
		<div class="form-card">
			<div class="form-header">
				<h6>${empty category ? 'Add New Category' : 'Edit Category'}</h6>
				<p>${empty category ? 'Add a new equipment category' : 'Update category details'}</p>
			</div>

			<!-- Error Message -->
			<c:if test="${not empty error}">
				<div class="alert alert-danger show">
					<i class="fa-solid fa-exclamation-circle"></i>
					${error}
				</div>
			</c:if>

			<!-- Form -->
			<form action="${pageContext.request.contextPath}/admin/category" method="post"
				class="category-form">

				<!-- Hidden ID field for edit -->
				<input type="hidden" name="id" value="${category.id}">

				<!-- Category Name -->
				<div class="form-group">
					<label for="name">Category Name <span class="required">*</span></label>
					<input type="text" id="name" name="name" 
						value="${category.name}" 
						placeholder="e.g., Tractors, Harvesters, Plows" 
						required>
					<small>Enter a unique category name</small>
				</div>

				<!-- Description (optional) -->
				<div class="form-group">
					<label for="description">Description</label>
					<textarea id="description" name="description" rows="4"
						placeholder="Describe this category..."></textarea>
					<small>Optional description for this category</small>
				</div>

				<!-- Form Actions -->
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">
						${empty category ? 'Create' : 'Update'}
					</button>
				</div>

			</form>

		</div>

	</div>

</body>
</html>
