<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.SajhaKrishi.model.*"%>
<jsp:useBean id="api" class="com.SajhaKrishi.constant.ApiConstant"
	scope="application" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
	integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<jsp:include page="alert.jsp" />
<% 
HttpSession userSession = request.getSession(false);
            User currentUser = (User) (userSession != null ? userSession.getAttribute(api.USER_SESSION_KEY) : null);
            pageContext.setAttribute("currentUser", currentUser);
        %>

<div class="layout">
	<aside class="sidebar" id="mainSidebar">

		<div class="sidebar-top">
			<div class="logo-row">
				<button class="collapse-btn" onclick="toggleSidebar()">
					<i class="fa-solid fa-bars"></i>
				</button>
			</div>

			<button
				onclick="location.href='${pageContext.request.contextPath}<%= api.OWNER_EQUIPMENT %><%= api.ADD %>'"
				class="new-btn">
				<i class="fa-solid fa-plus"></i> <span class="nav-text">New
					Equipment</span>
			</button>

			<div class="nav-section">
				<a href="${pageContext.request.contextPath}<%= api.DASHBOARD %>"
					class="nav-item ${selectedNavItem == 'dashboard' ? 'active' : ''}">
					<i class="fa-solid fa-chart-line"></i> <span class="nav-text">Overview</span>
				</a> <a
					href="${pageContext.request.contextPath}<%= api.OWNER_EQUIPMENT %><%= api.LIST %>"
					class="nav-item ${selectedNavItem == 'equipment' ? 'active' : ''}">
					<i class="fa-solid fa-tractor"></i> <span class="nav-text">Equipment</span>
				</a> <a href="#"
					class="nav-item ${selectedNavItem == 'profile' ? 'active' : ''}">
					<i class="fa-solid fa-user-gear"></i> <span class="nav-text">Profile</span>
				</a>
			</div>
		</div>

		<div class="sidebar-bottom">
			<div class="user-box">
				<i class="fa-solid fa-circle-user"></i>
				<div class="nav-text">
					<p>Welcome back</p>
					<small>Owner Portal</small>
				</div>
			</div>
			<a href="${pageContext.request.contextPath}<%= api.LOGOUT %>" class="nav-item logout-item">
				<i class="fa-solid fa-right-from-bracket"></i> 
				<span class="nav-text">Logout</span>
			</a>
		</div>
	</aside>

	<main class="main">
		<jsp:include page="${contentPage}" />
	</main>
</div>

<script>
    function toggleSidebar() {
        document.getElementById('mainSidebar').classList.toggle('collapsed');
    }
</script>