<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<!-- TITLE -->
		<div class="page-header">
			<h2>Browse Equipment</h2>
			<p>Find the right tools for your farm</p>
		</div>

		<!-- SEARCH BAR -->
		<div class="search-bar">
			<i class="fa-solid fa-magnifying-glass"></i> <input type="text"
				placeholder="Search equipment...">
		</div>

		<!-- MAIN -->
		<div class="layout">

			<!-- SIDEBAR -->
			<aside class="sidebar">

				<div class="filter-group">
					<p class="filter-title">Categories</p>

					<button class="cat active">All</button>
					<button class="cat">Tractor</button>
					<button class="cat">Tools</button>
					<button class="cat">Harvester</button>
				</div>

			</aside>

			<!-- CONTENT -->
			<section class="content">

				<p class="result">Showing 2 equipment</p>

				<div class="grid">

					<div class="product-card">
						<div class="product-image"></div>

						<div class="product-content">
							<h3 class="product-title">Tractor (John Deere)</h3>
							<p class="product-desc">Powerful tractor suitable for plowing
								and heavy-duty farming tasks.</p>

							<div class="product-meta">
								<span class="price">Rs. 1500/day</span> <span
									class="status available">Available</span>
							</div>

							<div class="product-actions">
								<button class="btn btn-primary">Rent Now</button>
								<button class="btn btn-outline">Details</button>
							</div>
						</div>
					</div>

					<div class="product-card">
						<div class="product-image"></div>

						<div class="product-content">
							<h3 class="product-title">Tractor (John Deere)</h3>
							<p class="product-desc">Powerful tractor suitable for plowing
								and heavy-duty farming tasks.</p>

							<div class="product-meta">
								<span class="price">Rs. 1500/day</span> <span
									class="status available">Available</span>
							</div>

							<div class="product-actions">
								<button class="btn btn-primary">Rent Now</button>
								<button class="btn btn-outline">Details</button>
							</div>
						</div>
					</div>

				</div>

			</section>

		</div>

	</div>


</body>
</html>