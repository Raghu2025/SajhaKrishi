<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<%@ page import="com.SajhaKrishi.constant.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forgot Password | Sajha Krishi</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/forget-password.css">
</head>
<body>
	<jsp:include page="public-navbar.jsp" />
	<div class="forget-password-container">
		<div class="header-label">
			<h6 class="welcome-message">Forgot Your Password?</h6>
			<p>No worries, we'll help you recover your account</p>
		</div>

		<div class="password-reset-wrapper">
			<!-- STEP 1: Email Verification -->
			<div id="step1" class="form-step active">
				<div class="form-header">
					<div class="step-indicator">
						<span class="step active">1</span>
						<span class="step">2</span>
						<span class="step">3</span>
					</div>
					<h6>Verify Your Email</h6>
					<p>Enter the email address associated with your account</p>
					<c:if test="${not empty error}">
						<div class="error show">${error}</div>
					</c:if>
					<c:if test="${not empty success}">
						<div class="success show">${success}</div>
					</c:if>
				</div>

				<form id="emailForm" action="${pageContext.request.contextPath}${ApiConstant.FORGET_PASSWORD}" method="post">
					<input type="hidden" name="step" value="1" />
					<div class="form-group">
						<label for="email">Email Address</label>
						<input type="email" id="email" placeholder="Enter your email"
							name="email" required />
						<span class="input-error">Please enter a valid email</span>
					</div>

					<div class="button-wrapper">
						<button type="submit" class="btn-primary">Send Recovery Code</button>
					</div>
				</form>

			</div>

			<!-- STEP 2: OTP Verification -->
			<div id="step2" class="form-step">
				<div class="form-header">
					<div class="step-indicator">
						<span class="step completed">1</span>
						<span class="step active">2</span>
						<span class="step">3</span>
					</div>
					<h6>Verify OTP</h6>
					<p>Enter the verification code sent to your email</p>
				</div>

				<form id="otpForm" action="${pageContext.request.contextPath}${ApiConstant.FORGET_PASSWORD}" method="post">
					<input type="hidden" name="step" value="2" />
					<div class="form-group">
						<label for="otp">Verification Code</label>
						<input type="text" id="otp" placeholder="Enter 6-digit code"
							name="otp" maxlength="6" pattern="[0-9]{6}" required />
						<span class="input-error">Please enter a valid 6-digit code</span>
					</div>

					<div class="otp-timer">
						<span>Didn't receive code? </span>
						<button type="button" id="resendBtn" class="resend-btn">Resend in 60s</button>
					</div>

					<div class="button-wrapper">
						<button type="submit" class="btn-primary">Verify Code</button>
					</div>
				</form>

				<div class="form-footer">
					<button type="button" class="btn-link" onclick="goBackToStep1()">
						<i class="fa fa-arrow-left"></i> Back to Email
					</button>
				</div>
			</div>

			<!-- STEP 3: Reset Password -->
			<div id="step3" class="form-step">
				<div class="form-header">
					<div class="step-indicator">
						<span class="step completed">1</span>
						<span class="step completed">2</span>
						<span class="step active">3</span>
					</div>
					<h6>Set New Password</h6>
					<p>Create a strong password for your account</p>
				</div>

				<form id="passwordForm" action="${pageContext.request.contextPath}${ApiConstant.FORGET_PASSWORD}" method="post">
					<input type="hidden" name="step" value="3" />
					<div class="form-group">
						<label for="newPassword">New Password</label>
						<input type="password" id="newPassword"
							placeholder="Enter new password" name="newPassword" required />
						<div class="password-strength">
						</div>
					</div>

					<div class="form-group">
						<label for="confirmPassword">Confirm Password</label>
						<input type="password" id="confirmPassword"
							placeholder="Confirm your password" name="confirmPassword"
							required />
						<span class="input-error">Passwords do not match</span>
					</div>

					<div class="button-wrapper">
						<button type="submit" class="btn-primary">Reset Password</button>
					</div>
				</form>
			</div>

			<!-- Success Message -->
			<div id="successMessage" class="form-step">
				<div class="success-content">
					<div class="success-icon">
						<i class="fa fa-check-circle"></i>
					</div>
					<h6>Password Reset Successfully</h6>
					<p>Your password has been reset. You can now login with your new
						password.</p>
					<div class="button-wrapper">
						<a href="${pageContext.request.contextPath}${ApiConstant.LOGIN}" class="btn-primary">Go to Login</a>
					</div>
				</div>
			</div>
		</div>

		<!-- Back to Login Link -->
		<div class="other-link">
			<p>
				Remember your password? <a href="${pageContext.request.contextPath}${ApiConstant.LOGIN}">Sign in here</a>
			</p>
		</div>

	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/js/all.min.js"></script>
	<script>
		// Resend OTP Timer
		let resendTimer = 60;
		const resendBtn = document.getElementById('resendBtn');
		// Activate correct step based on server-side state
		<% String _showStep = (String) request.getAttribute("showStep"); if (_showStep == null) _showStep = "1"; %>
		const initialStep = "<%= _showStep %>";
		document.querySelectorAll('.form-step').forEach(s => s.classList.remove('active'));
		if (initialStep === 'success') {
			document.getElementById('successMessage').classList.add('active');
		} else {
			const el = document.getElementById('step' + initialStep);
			if (el) el.classList.add('active');
			else document.getElementById('step1').classList.add('active');
		}
		function startResendTimer() {
			resendTimer = 60;
			resendBtn.disabled = true;
			resendBtn.classList.add('disabled');

			const timer = setInterval(() => {
				resendTimer--;
				resendBtn.textContent = 'Resend in ' + resendTimer + 's';

				if (resendTimer === 0) {
					clearInterval(timer);
					resendBtn.disabled = false;
					resendBtn.textContent = 'Resend Code';
					resendBtn.classList.remove('disabled');
				}
			}, 1000);
		}

		// Resend OTP Click
		resendBtn.addEventListener('click', function() {
			// Send resend request to server
			fetch('${pageContext.request.contextPath}${ApiConstant.FORGET_PASSWORD}?action=resendOTP', {
				method: 'POST'
			}).then(response => {
				if (response.ok) {
					startResendTimer();
					showAlert('Code sent to your email', 'success');
				}
			});
		});

		// Back to Step 1
		function goBackToStep1() {
			document.getElementById('step1').classList.add('active');
			document.getElementById('step2').classList.remove('active');
		}

		// Password Strength Indicator
		const passwordInput = document.getElementById('newPassword');
		const strengthBar = document.querySelector('.strength-bar');
		const strengthText = document.querySelector('.strength-text');

		passwordInput.addEventListener('input', function() {
			const password = this.value;
			let strength = 0;

			if (password.length >= 8)
				strength += 25;
			if (/[a-z]/.test(password))
				strength += 25;
			if (/[A-Z]/.test(password))
				strength += 25;
			if (/[0-9]/.test(password))
				strength += 25;

			strengthBar.style.width = strength + '%';

			if (strength < 50) {
				strengthBar.className = 'strength-bar weak';
				strengthText.textContent = 'Strength: Weak';
			} else if (strength < 75) {
				strengthBar.className = 'strength-bar fair';
				strengthText.textContent = 'Strength: Fair';
			} else {
				strengthBar.className = 'strength-bar strong';
				strengthText.textContent = 'Strength: Strong';
			}
		});

	
		// Show Alert
		function showAlert(message, type) {
			const alert = document.createElement('div');
			alert.className = 'alert alert-' + type;
			alert.textContent = message;
			document.body.prepend(alert);

			setTimeout(() => {
				alert.classList.add('show');
			}, 100);

			setTimeout(() => {
				alert.remove();
			}, 5000);
		}
	</script>
</body>
</html>
