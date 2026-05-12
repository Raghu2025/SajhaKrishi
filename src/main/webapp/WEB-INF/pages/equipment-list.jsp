<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Equipment | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/equipment-list.css">
</head>
<body>
	<div class="inventory-page">
		<div class="container">
			<!-- HEADER -->
			<div class="page-header">
				<h2>Equipment Inventory</h2>
				<p>Manage your rental tools and stock</p>
			</div>

			<!-- TABLE CARD -->
			<div class="table-card">

				<div class="table-wrapper">

					<table>
						<thead>
							<tr>
								<th>Image</th>
								<th>Name</th>
								<th>Category</th>
								<th>Rental Price (per day)</th>
								<th>Rental Price (per hour)</th>
							<!-- 	<th>Status</th> -->
								<th>Location</th>
								<th>Actions</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="equipment" items="${equipmentList}">
								<tr>
									<td>
										<div class="img-box">
											<img
												src="${pageContext.request.contextPath}${equipment.imagePath}"
												onerror="this.src='${pageContext.request.contextPath}/assets/noImage.svg';" />
										</div>
									</td>

									<td class="name">${equipment.name }</td>
									<td class="category">${equipment.categoryName }</td>

									<td class="price">Rs. ${equipment.pricePerDay} /day</td>
									<td class="price">Rs. ${equipment.pricePerHour} /hour</td>
<%-- 									<td><span
										class="status-pill ${equipment.availabilityStatus.toLowerCase()}">
											${equipment.availabilityStatus} </span></td> --%>
									<td class="location">${equipment.municipality},
										${equipment.district}</td>

									<td>
										<div class="actions">
											<button
												onclick="location.href='${pageContext.request.contextPath}/owner/equipment/edit?id=${equipment.id}'"
												class="btn btn-outline">Edit</button>
											<button
												onclick="location.href='${pageContext.request.contextPath}/owner/equipment/delete?id=${equipment.id}'"
												class="btn btn-danger">Remove</button>
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

</body>
</html>