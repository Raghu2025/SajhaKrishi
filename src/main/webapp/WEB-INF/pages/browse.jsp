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

					<div class="card">
						<!-- <div class="img">🚜</div> -->
						<h4>Tractor</h4>
						<p class="price">Rs. 1500/day</p>
					</div>

					<div class="card">
						<!-- <div class="img">🛠</div> -->
						<h4>Tiller</h4>
						<p class="price">Rs. 500/day</p>
					</div>

				</div>

			</section>

		</div>

	</div>


</body>
</html>