<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
	integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<div class="layout">

	<!-- SIDEBAR -->
	<aside class="sidebar">

		<!-- TOP -->
		<div class="sidebar-top">

			<div class="logo-row">
				<!-- <div class="logo">🌾</div> -->
				<button class="collapse-btn">☰</button>
			</div>

			<button class="new-btn">＋ New Equipment</button>

			<div class="nav-section">
				<a href="#"
					class="nav-item ${selectedNavItem == 'dashboard' ? 'active' : ''}">
					<i class="fa-solid fa-chart-line"></i> <span>Overview</span>
				</a> <a href="#"
					class="nav-item ${selectedNavItem == 'equipment' ? 'active' : ''}">
					<i class="fa-solid fa-toolbox"></i> <span>Equipment</span>
				</a> <a href="#"
					class="nav-item ${selectedNavItem == 'profile' ? 'active' : ''}">
					<i class="fa-solid fa-user"></i> <span>Profile</span>
				</a>
			</div>

		</div>

		<!-- BOTTOM -->
		<div class="sidebar-bottom">
			<div class="user-box">
				<p>Welcome back</p>
				<small>Manage your inventory</small>
				<!-- <button class="login-btn">Log in</button> -->
			</div>
		</div>

	</aside>

	<!-- MAIN -->
	<main class="main">
		<jsp:include page="${contentPage}" />
	</main>

</div>