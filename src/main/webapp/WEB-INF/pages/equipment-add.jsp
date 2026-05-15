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
				<h6>${empty equipment ? 'Add New Equipment' : 'Edit Equipment'}</h6>
				<p>${empty equipment ? 'Add a new rental tool to your inventory' : 'Update your equipment details'}</p>
			</div>

			<c:if test="${not empty error}">
				<div class="error show">${error}</div>
			</c:if>
			<form action="${pageContext.request.contextPath}/owner/equipment"
				method="post" class="equipment-form" enctype="multipart/form-data">

				<%-- Hidden fields --%>
				<input type="hidden" name="action"
					value="${empty equipment ? 'add' : 'edit'}"> <input
					type="hidden" name="id" value="${equipment.id}">


				<!-- Title changes based on add or edit -->


				<div class="form-row">
					<div class="form-group">
						<label>Equipment Name</label> <input type="text" name="name"
							value="${equipment.name}" placeholder="e.g., Excavator" required>
					</div>
					<div class="form-group">
						<label>Category</label> <select name="category" required>
							<option value="">Select Category</option>
							<c:forEach var="category" items="${categoryList}">
								<option value="${category.id}"
									${category.id == equipment.categoryId ? 'selected' : ''}>
									${category.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label>Brand</label> <input type="text" name="brand"
							value="${equipment.brand}" placeholder="e.g., Caterpillar"
							required>
					</div>
					<div class="form-group">
						<label>Manufacture Year</label> <input type="number"
							name="manufactureYear" value="${equipment.manufactureYear}"
							placeholder="2024" required>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label>Price (per day)</label> <input type="number"
							name="pricePerDay" value="${equipment.pricePerDay}"
							placeholder="NPR 0" required>
					</div>
					<div class="form-group">
						<label>Price (per hour)</label> <input type="number"
							name="pricePerHour" value="${equipment.pricePerHour}"
							placeholder="NPR 0" required>
					</div>
				</div>

				<div class="form-group">
					<label>Deposit Amount</label> <input type="number"
						name="depositAmount" value="${equipment.depositAmount}"
						placeholder="NPR 0" required>
				</div>

<%-- 				<div class="form-row">
					<div class="form-group">
						<label>Available From</label> <input type="date"
							name="availableFrom" value="${equipment.availableFrom}" required>
					</div>
					<div class="form-group">
						<label>Available To</label> <input type="date" name="availableTo"
							value="${equipment.availableTo}" required>
					</div>
				</div>
 --%>
				<div class="form-row">
					<div class="form-group">
						<label>District</label> <select name="district" required>
							<option value="">Select District</option>
							<c:forEach var="name" items="${district}">
								<option value="${name}"
									${name == equipment.district ? 'selected' : ''}>
									${name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label>Municipality</label> <input type="text" name="municipality"
							value="${equipment.municipality}" placeholder="City" required>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label>Description</label>
						<textarea name="description" rows="3" required>${equipment.description}</textarea>
					</div>
					<div class="form-group">
						<label>Specifications</label>
						<textarea name="specifications" rows="3" required>${equipment.specifications}</textarea>
					</div>
				</div>

				<!-- Image -->
				<div class="form-group image-picker-container">
					<label>Equipment Photo</label>
					<div
						class="upload-card ${ not empty equipment.imagePath ? 'has-image' : '' }"
						id="uploadCard">
						<div class="image-preview-wrapper"
							onclick="document.getElementById('fileInput').click()">
							<img
								src="${ not empty equipment.imagePath ? pageContext.request.contextPath.concat(equipment.imagePath) : '' }"
								id="previewImg" alt="Preview">
							<div class="upload-placeholder" id="placeholder">
								<i class="fa-solid fa-cloud-arrow-up"></i> <span>${empty equipment ? 'Click to Upload' : 'Click to Change Image'}</span>
								<small>PNG, JPG up to 5MB ${not empty equipment ? '(optional)' : ''}</small>
							</div>
						</div>
						<button type="button" class="remove-img-btn" id="removeBtn"
							onclick="clearImage(event)">
							<i class="fa-solid fa-xmark"></i>
						</button>
					</div>

					<%-- Required only for Add, optional for Edit --%>
					<input type="file" name="image" id="fileInput" accept="image/*"
						onchange="previewImage(this)" style="display: none;"
						>
						<%-- ${empty equipment ? 'required' : ''} --%>
				</div>

				<div class="button-wrapper">
					<button type="submit" class="btn btn-primary">${empty equipment ? 'Save Equipment' : 'Update Equipment'}
					</button>
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