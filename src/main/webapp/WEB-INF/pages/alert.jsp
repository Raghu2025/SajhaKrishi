<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/alert.css">

<c:if test="${not empty sessionScope.success}">
	<div class="alert alert-success alert-dismissible fade show"
		role="alert">
		<strong>Success! </strong> &nbsp;${sessionScope.success}
		<button type="button" class="btn-close" data-bs-dismiss="alert"
			aria-label="Close"></button>
	</div>

	<%-- Remove the message so it only shows once --%>
	<c:remove var="success" scope="session" />
</c:if>

<c:if test="${not empty sessionScope.error}">
	<div class="alert alert-danger alert-dismissible fade show"
		role="alert">
		<strong>Error! </strong> &nbsp;${sessionScope.error}
		<button type="button" class="btn-close" data-bs-dismiss="alert"
			aria-label="Close"></button>
	</div>
	<c:remove var="error" scope="session" />
</c:if>

<script>
   setTimeout(function() {
        let alert = document.querySelector('.alert');
        if (alert) {
            alert.classList.remove('show');
            setTimeout(() => alert.remove(), 500);
        }
    }, 5000);
</script>