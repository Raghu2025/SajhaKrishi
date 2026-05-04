<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Equipment | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/equipment-add.css">
</head>
</head>
<body>
	<div class="container" style="margin-top: 2rem;">

		<div class="form-card">
			<div class="form-header">
				<h6>Add New Equipment</h6>
				<p>Add a new rental tool to your inventory</p>
			</div>

			<form>

				<!-- Equipment Name -->
				<div class="form-group">
					<label>Equipment Name</label> <input type="text"
						placeholder="e.g., Hydraulic Excavator" required>
				</div>

				<!-- Category -->
				<div class="form-group">
					<label>Category</label> <input type="text"
						placeholder="e.g., Excavator" required>
				</div>

				<!-- Rental Price -->
				<div class="form-group">
					<label>Rental Price (per day)</label> <input type="number"
						placeholder="Rs. 0" required>
				</div>

				<!-- Submit -->
				<div class="button-wrapper">
					<button type="submit" class="btn btn-primary">Add
						Equipment</button>
				</div>

			</form>
		</div>

	</div>

</body>
</html>