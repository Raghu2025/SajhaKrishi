<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.SajhaKrishi.model.*" %>
<%@ page import="com.SajhaKrishi.constant.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/public-navbar.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<% 
HttpSession userSession = request.getSession(false);
            User currentUser = (User) (userSession != null ? userSession.getAttribute(ApiConstant.USER_SESSION_KEY) : null);
            pageContext.setAttribute("currentUser", currentUser);
        %>
<header>
	<div class="container nav">
		<%--
                    <div class="logo-wrapper">
                <div class="logo-box">🌾</div>
                <div class="logo-text">Sajha Krishi</div>
            </div>
         --%>

		<div class="nav-links">
			<a href="${pageContext.request.contextPath}/home#features">Features</a>
			<a href="${pageContext.request.contextPath}/home#how">How It
				Works</a> <a href="${pageContext.request.contextPath}/kisan/equipment">Browse</a>
			<c:choose>
				<c:when test="${not empty currentUser}">
					<button class="btn btn-outline"
						onclick="location.href='${pageContext.request.contextPath}/logout'">Logout</button>
				</c:when>
				<c:otherwise>
					<button class="btn btn-outline"
						onclick="location.href='${pageContext.request.contextPath}/login'">Login</button>
					<button class="btn btn-primary"
						onclick="location.href='${pageContext.request.contextPath}/register'">Sign Up</button>
				</c:otherwise>
			</c:choose>

		</div>
	</div>
</header>