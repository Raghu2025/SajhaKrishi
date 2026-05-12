<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="api" class="com.SajhaKrishi.constant.ApiConstant"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Browse | Sajha Krishi</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/browse.css">
</head>
<body>
	<jsp:include page="public-navbar.jsp" />

	<div class="browse-page">
		<div class="page-header">
			<h2>Browse Equipment</h2>
			<p>Find the right tools for your farm</p>
		</div>

		<form action="${pageContext.request.contextPath}<%= api.KISSAN_EQUIPMENT %><%= api.LIST %>"
			method="GET" id="filterForm">
			<div class="search-bar-wrapper">
				<div class="search-bar">
					<i class="fa-solid fa-magnifying-glass"></i> <input type="text"
						name="keyword" value="${keyword}"
						placeholder="Search equipment by name or brand...">
				</div>
				<button type="submit" class="btn btn-primary">Search</button>
			</div>


			<div class="layout">
				<aside class="sidebar">
					<div class="filter-group">
						<p class="filter-title">Categories</p>

						<input type="hidden" name="category" id="categoryInput"
							value="${selectedCategory}">
						<button type="button"
							class="cat ${empty selectedCategory ? 'active' : ''}"
							onclick="filterByCategory('')">All</button>

						<c:forEach var="category" items="${categoryList}">
							<button type="button"
								class="cat ${selectedCategory == category.id ? 'active' : ''}"
								onclick="filterByCategory(${category.id})">${category.name}</button>
						</c:forEach>
					</div>

					<div class="form-group mt-4">
						<p class="filter-title">Location (District)</p>
						<select name="district" onchange="this.form.submit()"
							class="filter-select">
							<option value="">Anywhere</option>
							<c:forEach var="name" items="${district}">
								<option value="${name}"
									${selectedDistrict == name ? 'selected' : ''}>${name}</option>
							</c:forEach>
						</select>
					</div>
				</aside>

				<section class="content">
					<p class="result">Showing ${totalCount} equipment</p>

					<div class="grid">
						<c:forEach var="equipment" items="${equipmentList}">
							<div class="product-card">
								<div class="product-image">
									<img
										src="${pageContext.request.contextPath}${not empty equipment.imagePath ? equipment.imagePath : '/assets/noImage.svg'}"
										onerror="this.src='${pageContext.request.contextPath}/assets/noImage.svg';">
								</div>

								<div class="product-content">
									<h3 class="product-title">${equipment.name}</h3>
									<p class="product-desc">${equipment.description}</p>

									<div class="product-meta">

										<span class="price">Rs. ${equipment.pricePerDay}/day</span> <span
											class="status ${equipment.availabilityStatus == 'Available' ? 'available' : 'unavailable'}">
											${equipment.availabilityStatus} </span>
									</div>

									<div class="product-actions">
											<a
											href="${pageContext.request.contextPath}<%= api.KISSAN_EQUIPMENT %><%= api.DETAIL %>?id=${equipment.id}"
											class="btn btn-outline">Details</a>
									</div>
								</div>
							</div>
						</c:forEach>

						<c:if test="${empty equipmentList}">
							<div class="no-results">
								<p>No equipment found matching your criteria.</p>
								<a
									href="${pageContext.request.contextPath}<%= api.KISSAN_EQUIPMENT %><%= api.LIST %>">Clear
									all filters</a>
							</div>
						</c:if>
					</div>
				</section>
			</div>
		</form>
	</div>

	<script>
        function filterByCategory(catId) {
            document.getElementById('categoryInput').value = catId;
            document.getElementById('filterForm').submit();
        }
    </script>
</body>
</html>