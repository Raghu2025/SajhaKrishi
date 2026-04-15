<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
	<!-- ══ LEFT PANEL ══ -->
<div class="left-panel">

  <!-- Logo -->
  <a href="#" class="logo">
    <div class="logo-icon">🌾</div>
    <span class="logo-text">SajhaKrishi</span>
  </a>


  <!-- Panel tagline -->
  <div class="panel-tagline">
    <h2>Grow more.<br/>Rent smart.</h2>
    <p>Quality farm equipment at your doorstep.</p>
  </div>
  <img src="${pageContext.request.contextPath}/assets/register-page.png" />


</div>

<!-- ══ RIGHT PANEL (FORM) ══ -->
<div class="right-panel">
  <div class="dots"></div>
  <div class="dots-bl"></div>

  <div class="form-box">
    <div class="form-eyebrow">New account</div>
    <h1 class="form-title">Join <span>SajhaKrishi</span></h1>
    <p class="form-sub">Create your account and start renting equipment today.</p>

    <!-- Role toggle -->
    <div class="role-toggle" id="roleToggle">
      <button class="role-btn active" onclick="setRole(this, 'farmer')">🌾 Farmer</button>
      <button class="role-btn" onclick="setRole(this, 'owner')">🚜 Equipment Owner</button>
    </div>

    <form action="RegisterServlet" method="post" onsubmit="return validateForm()">
      <input type="hidden" name="role" id="roleInput" value="farmer"/>

      <div class="form-row">
        <div class="form-group">
          <label for="firstName">First name</label>
          <div class="input-wrap">
            <span class="input-icon">👤</span>
            <input type="text" id="firstName" name="firstName" placeholder="Ram" required/>
          </div>
        </div>
        <div class="form-group">
          <label for="lastName">Last name</label>
          <div class="input-wrap">
            <span class="input-icon">👤</span>
            <input type="text" id="lastName" name="lastName" placeholder="Sharma" required/>
          </div>
        </div>
      </div>

      <div class="form-group">
        <label for="email">Email address</label>
        <div class="input-wrap">
          <span class="input-icon">✉️</span>
          <input type="email" id="email" name="email" placeholder="ram@example.com" required/>
        </div>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label for="phone">Phone number</label>
          <div class="input-wrap">
            <span class="input-icon">📱</span>
            <input type="tel" id="phone" name="phone" placeholder="98XXXXXXXX" required/>
          </div>
        </div>
        <div class="form-group">
          <label for="district">District</label>
          <div class="input-wrap">
            <span class="input-icon">📍</span>
            <select id="district" name="district" required>
              <option value="" disabled selected>Select district</option>
              <option>Kathmandu</option>
              <option>Lalitpur</option>
              <option>Bhaktapur</option>
              <option>Chitwan</option>
              <option>Pokhara</option>
              <option>Butwal</option>
              <option>Dharan</option>
              <option>Biratnagar</option>
              <option>Hetauda</option>
              <option>Nepalgunj</option>
            </select>
          </div>
        </div>
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <div class="input-wrap">
          <span class="input-icon">🔒</span>
          <input type="password" id="password" name="password" placeholder="Min. 8 characters" required oninput="checkStrength(this.value)"/>
          <button type="button" class="password-toggle" onclick="togglePw('password', this)">👁</button>
        </div>
        <div class="strength-bar">
          <div class="strength-seg" id="s1"></div>
          <div class="strength-seg" id="s2"></div>
          <div class="strength-seg" id="s3"></div>
          <div class="strength-seg" id="s4"></div>
        </div>
      </div>

      <div class="form-group">
        <label for="confirmPassword">Confirm password</label>
        <div class="input-wrap">
          <span class="input-icon">🔒</span>
          <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Re-enter password" required/>
          <button type="button" class="password-toggle" onclick="togglePw('confirmPassword', this)">👁</button>
        </div>
      </div>

      <div class="terms-row">
        <input type="checkbox" id="terms" name="terms" required/>
        <span class="terms-text">
          I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a> of SajhaKrishi.
        </span>
      </div>

      <button type="submit" class="btn-submit">Create my account</button>
      <p class="login-link">Already have an account? <a href="login.jsp">Sign in</a></p>
    </form>
  </div>
</div>

</body>
</html>

