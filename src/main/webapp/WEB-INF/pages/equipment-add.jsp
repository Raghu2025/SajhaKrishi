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
			<c:if test="${not empty error}">
				<div class="error show">${error}</div>
			</c:if>

			<form action="add" method="post" class="equipment-form"
				enctype="multipart/form-data">

				<!-- Row 1: Name & Category -->
				<div class="form-row">
					<div class="form-group">
						<label>Equipment Name</label> <input type="text" name="name"
							placeholder="e.g., Excavator" required>
					</div>
					<div class="form-group">
						<label>Category</label> <select name="category" required>
							<option value="">Select Category</option>
							<c:forEach var="category" items="${categoryList}">
								<option value="${category.id}">${category.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<!-- Row 2: Brand & Year -->
				<div class="form-row">
					<div class="form-group">
						<label>Brand</label> <input type="text" name="brand"
							placeholder="e.g., Caterpillar" required>
					</div>
					<div class="form-group">
						<label>Manufacture Year</label> <input type="number"
							name="manufactureYear" placeholder="2024" required>
					</div>
				</div>

				<!-- Row 3: Pricing -->
				<div class="form-row">
					<div class="form-group">
						<label>Price (per day)</label> <input type="number"
							name="pricePerDay" placeholder="NPR 0" required>
					</div>
					<div class="form-group">
						<label>Price (per hour)</label> <input type="number"
							name="pricePerHour" placeholder="NPR 0" required>
					</div>
				</div>
				<div class="form-group">
					<label>Deposit Amount</label> <input type="number"
						name="depositAmount" placeholder="NPR 0" required>
				</div>
				<!-- Row 4: Dates -->
				<div class="form-row">
					<div class="form-group">
						<label>Available From</label> <input type="date"
							name="availableFrom" required>
					</div>
					<div class="form-group">
						<label>Available To</label> <input type="date" name="availableTo"
							required>
					</div>
				</div>

				<!-- Row 5: Location -->
				<div class="form-row">
					<div class="form-group">
						<label>District</label> <select name="district" required>
							<option value="">Select District</option>
							<c:forEach var="name" items="${district}">
								<option value="${name}">${name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label>Municipality</label> <input type="text" name="municipality"
							placeholder="City" required>
					</div>
				</div>

				<!-- Row 6: Description & Specs (Full Width or Split) -->
				<div class="form-row">
					<div class="form-group">
						<label>Description</label>
						<textarea name="description" rows="3" required></textarea>
					</div>
					<div class="form-group">
						<label>Specifications</label>
						<textarea name="specifications" rows="3" required></textarea>
					</div>
				</div>

				<div class="form-group image-picker-container">
					<label>Equipment Photo</label>
					<div class="upload-card" id="uploadCard">
						<div class="image-preview-wrapper"
							onclick="document.getElementById('fileInput').click()">
							<img src="" id="previewImg" alt="Preview">
							<div class="upload-placeholder" id="placeholder">
								<i class="fa-solid fa-cloud-arrow-up"></i> <span>Click to
									Upload</span> <small>PNG, JPG up to 5MB</small>
							</div>
						</div>

						<button type="button" class="remove-img-btn" id="removeBtn"
							onclick="clearImage(event)">
							<i class="fa-solid fa-xmark"></i>
						</button>
					</div>

					<input type="file" name="image" id="fileInput" accept="image/*"
						onchange="previewImage(this)" style="display: none;" required>
				</div>

				<div class="button-wrapper">
					<button type="submit" class="btn btn-primary">Save
						Equipment</button>
				</div>
			</form>
		</div>

	</div>

	<script type="text/javascript">
		function previewImage(input) {
			const card = document.getElementById('uploadCard');
			const preview = document.getElementById('previewImg');

			if (input.files && input.files[0]) {
				const reader = new FileReader();

				reader.onload = function(e) {
					preview.src = e.target.result;
					// Simply add the 'has-image' class to the parent
					card.classList.add('has-image');
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		function clearImage(event) {
			event.stopPropagation();
			const input = document.getElementById('fileInput');
			const card = document.getElementById('uploadCard');
			const preview = document.getElementById('previewImg');

			input.value = "";
			preview.src = "";
			// Remove the class to reset the UI
			card.classList.remove('has-image');
		}
	</script>

</body>
</html>