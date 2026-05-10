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
								<th>Stock</th>
								<th>Rental Price</th>
								<th>Actions</th>
							</tr>
						</thead>

						<tbody>

							<!-- ROW -->
							<tr>
								<td>
									<!-- <div class="img-box">🚜</div> -->
								</td>

								<td class="name">Tractor</td>

								<td class="category">Heavy</td>

								<td><input type="number" value="5" min="0"
									class="stock-input"></td>

								<td class="price">Rs. 1500/day</td>

								<td>
									<div class="actions">
										<button class="btn btn-outline">Edit</button>
										<button class="btn btn-danger">Remove</button>
									</div>
								</td>
							</tr>

							<!-- ROW -->
							<tr>
								<td>
									<!-- <div class="img-box">🚜</div> -->
								</td>

								<td class="name">Mini Tiller</td>
								<td class="category">Small Tools</td>

								<td><input type="number" value="8" min="0"
									class="stock-input"></td>

								<td class="price">Rs. 500/day</td>

								<td>
									<div class="actions">
										<button class="btn btn-outline">Edit</button>
										<button class="btn btn-danger">Remove</button>
									</div>
								</td>
							</tr>

							<c:forEach var="name" items="${district}">
								<td>
									<!-- <div class="img-box">🚜</div> -->
								</td>

								<td class="name">Mini Tiller</td>
								<td class="category">Small Tools</td>

								<td><input type="number" value="8" min="0"
									class="stock-input"></td>

								<td class="price">Rs. 500/day</td>

								<td>
									<div class="actions">
										<button class="btn btn-outline">Edit</button>
										<button class="btn btn-danger">Remove</button>
									</div>
								</td>
							</c:forEach>

						</tbody>
					</table>

				</div>

			</div>

		</div>

	</div>

</body>
</html>