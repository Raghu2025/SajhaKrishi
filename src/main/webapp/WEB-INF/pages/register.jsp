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

  <!-- Cloud SVGs -->
  <div class="cloud cloud-1">
    <svg width="90" height="36" viewBox="0 0 90 36" fill="none">
      <ellipse cx="45" cy="28" rx="42" ry="10" fill="white" fill-opacity="0.85"/>
      <ellipse cx="30" cy="22" rx="22" ry="16" fill="white" fill-opacity="0.85"/>
      <ellipse cx="55" cy="18" rx="28" ry="20" fill="white" fill-opacity="0.85"/>
      <ellipse cx="72" cy="24" rx="18" ry="13" fill="white" fill-opacity="0.85"/>
    </svg>
  </div>
  <div class="cloud cloud-2">
    <svg width="70" height="28" viewBox="0 0 70 28" fill="none">
      <ellipse cx="35" cy="22" rx="32" ry="8" fill="white" fill-opacity="0.7"/>
      <ellipse cx="22" cy="17" rx="16" ry="12" fill="white" fill-opacity="0.7"/>
      <ellipse cx="44" cy="14" rx="20" ry="14" fill="white" fill-opacity="0.7"/>
    </svg>
  </div>

  <!-- Birds -->
  <div class="birds">
    <svg width="60" height="20" viewBox="0 0 60 20" fill="none">
      <path d="M0 10 Q5 4 10 10" stroke="#2D5016" stroke-width="1.5" fill="none" stroke-linecap="round"/>
      <path d="M18 7 Q23 1 28 7" stroke="#2D5016" stroke-width="1.5" fill="none" stroke-linecap="round"/>
      <path d="M36 12 Q41 6 46 12" stroke="#3B4A2E" stroke-width="1.2" fill="none" stroke-linecap="round"/>
    </svg>
  </div>

  <!-- Panel tagline -->
  <div class="panel-tagline">
    <h2>Grow more.<br/>Rent smart.</h2>
    <p>Quality farm equipment at your doorstep.</p>
  </div>

  <!-- Full landscape SVG illustration -->
  <svg class="landscape" viewBox="0 0 620 380" fill="none" xmlns="http://www.w3.org/2000/svg">

    <!-- Rolling hills back -->
    <ellipse cx="310" cy="370" rx="380" ry="130" fill="#3D6B1E"/>
    <ellipse cx="80"  cy="360" rx="160" ry="80"  fill="#2D5016"/>
    <ellipse cx="540" cy="355" rx="140" ry="70"  fill="#2D5016"/>

    <!-- Fields / ground -->
    <rect x="0" y="295" width="620" height="85" fill="#4A7A20"/>
    <rect x="0" y="330" width="620" height="50" fill="#3D6B1E"/>

    <!-- Field rows (crop lines) -->
    <line x1="60"  y1="298" x2="60"  y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="80"  y1="298" x2="80"  y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="100" y1="298" x2="100" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="120" y1="298" x2="120" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="140" y1="298" x2="140" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="420" y1="298" x2="420" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="440" y1="298" x2="440" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="460" y1="298" x2="460" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="480" y1="298" x2="480" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>
    <line x1="500" y1="298" x2="500" y2="308" stroke="#A8CC6A" stroke-width="1.5" opacity="0.6"/>

    <!-- Road -->
    <path d="M270 380 L310 290 L350 380Z" fill="#D4A050" opacity="0.5"/>
    <path d="M280 380 L308 310 L336 380Z" fill="#E8C88A" opacity="0.4"/>

    <!-- Farmhouse -->
    <rect x="255" y="248" width="80" height="52" fill="#F5F0E8"/>
    <rect x="263" y="255" width="18" height="22" fill="#A8CC6A" opacity="0.8"/> <!-- window -->
    <rect x="293" y="255" width="18" height="22" fill="#A8CC6A" opacity="0.8"/>
    <rect x="278" y="268" width="16" height="32" fill="#7A4B1A" opacity="0.85"/> <!-- door -->
    <polygon points="248,252 335,252 335,238 291,210 248,238" fill="#C47A18"/>
    <polygon points="248,252 270,252 270,238 248,238" fill="#8B5210" opacity="0.4"/>
    <!-- chimney -->
    <rect x="316" y="212" width="12" height="22" fill="#8B5210"/>
    <!-- smoke puffs -->
    <circle cx="322" cy="204" r="6" fill="white" opacity="0.55"/>
    <circle cx="326" cy="196" r="5" fill="white" opacity="0.4"/>
    <circle cx="320" cy="188" r="4" fill="white" opacity="0.3"/>

    <!-- Windmill -->
    <rect x="164" y="218" width="8" height="82" fill="#8B5210"/>
    <circle cx="168" cy="218" r="5" fill="#E8A020"/>
    <!-- Blades -->
    <line x1="168" y1="218" x2="168" y2="185" stroke="#EAF3DE" stroke-width="4" stroke-linecap="round"/>
    <line x1="168" y1="218" x2="200" y2="222" stroke="#EAF3DE" stroke-width="4" stroke-linecap="round"/>
    <line x1="168" y1="218" x2="168" y2="250" stroke="#EAF3DE" stroke-width="4" stroke-linecap="round"/>
    <line x1="168" y1="218" x2="136" y2="214" stroke="#EAF3DE" stroke-width="4" stroke-linecap="round"/>

    <!-- Barn (right) -->
    <rect x="420" y="258" width="70" height="44" fill="#C47A18"/>
    <polygon points="410,260 500,260 455,230" fill="#8B5210"/>
    <rect x="437" y="270" width="36" height="32" fill="#7A4B1A" opacity="0.7"/>
    <rect x="440" y="258" width="13" height="14" rx="1" fill="#FFD580" opacity="0.55"/>
    <rect x="460" y="258" width="13" height="14" rx="1" fill="#FFD580" opacity="0.55"/>
    <!-- X on barn door -->
    <line x1="437" y1="270" x2="473" y2="302" stroke="#5A3510" stroke-width="1.2" opacity="0.6"/>
    <line x1="473" y1="270" x2="437" y2="302" stroke="#5A3510" stroke-width="1.2" opacity="0.6"/>

    <!-- Trees left group -->
    <rect x="52" y="258" width="7" height="42" fill="#5A3A10"/>
    <ellipse cx="55" cy="250" rx="18" ry="24" fill="#2D5016"/>
    <ellipse cx="55" cy="244" rx="13" ry="18" fill="#3D6B1E"/>

    <rect x="92" y="265" width="6" height="35" fill="#5A3A10"/>
    <ellipse cx="95" cy="258" rx="15" ry="20" fill="#2D5016"/>

    <!-- Trees right group -->
    <rect x="548" y="255" width="7" height="45" fill="#5A3A10"/>
    <ellipse cx="551" cy="246" rx="19" ry="25" fill="#2D5016"/>
    <ellipse cx="551" cy="240" rx="13" ry="17" fill="#3D6B1E"/>

    <rect x="578" y="262" width="6" height="38" fill="#5A3A10"/>
    <ellipse cx="581" cy="255" rx="15" ry="20" fill="#2D5016"/>

    <!-- Tractor (small, distant) -->
    <rect x="195" y="287" width="32" height="18" rx="2" fill="#E8A020"/>
    <circle cx="200" cy="307" r="7" fill="#3B4A2E"/>
    <circle cx="200" cy="307" r="3.5" fill="#6B7A5A"/>
    <circle cx="220" cy="308" r="5.5" fill="#3B4A2E"/>
    <circle cx="220" cy="308" r="2.5" fill="#6B7A5A"/>
    <rect x="195" y="282" width="18" height="8" rx="1" fill="#C47A18"/> <!-- cab -->
    <rect x="198" y="283" width="7" height="5" rx="1" fill="#b8d9f0" opacity="0.8"/> <!-- window -->
    <!-- exhaust pipe -->
    <rect x="225" y="279" width="3" height="10" fill="#3B4A2E"/>
    <circle cx="226" cy="277" r="3" fill="#6B7A5A" opacity="0.5"/>

    <!-- Hay bales -->
    <ellipse cx="376" cy="306" rx="14" ry="10" fill="#E8C88A"/>
    <ellipse cx="376" cy="306" rx="14" ry="10" fill="none" stroke="#C47A18" stroke-width="1" opacity="0.5"/>
    <ellipse cx="376" cy="300" rx="10" ry="7" fill="#F5DFB8"/>
    <ellipse cx="404" cy="308" rx="12" ry="8" fill="#E8C88A"/>

    <!-- Wheat stalks foreground -->
    <line x1="30"  y1="380" x2="36"  y2="315" stroke="#A8CC6A" stroke-width="2"/>
    <ellipse cx="36" cy="310" rx="5" ry="10" fill="#A8CC6A"/>
    <line x1="44"  y1="380" x2="50"  y2="320" stroke="#A8CC6A" stroke-width="2"/>
    <ellipse cx="50" cy="315" rx="4" ry="9" fill="#A8CC6A"/>
    <line x1="570" y1="380" x2="575" y2="318" stroke="#A8CC6A" stroke-width="2"/>
    <ellipse cx="575" cy="313" rx="5" ry="10" fill="#A8CC6A"/>
    <line x1="585" y1="380" x2="589" y2="325" stroke="#A8CC6A" stroke-width="2"/>
    <ellipse cx="589" cy="320" rx="4" ry="8" fill="#A8CC6A"/>
    <line x1="597" y1="380" x2="601" y2="322" stroke="#A8CC6A" stroke-width="2"/>
    <ellipse cx="601" cy="317" rx="3.5" ry="8" fill="#A8CC6A"/>

    <!-- Fence posts -->
    <rect x="345" y="290" width="4" height="20" rx="1" fill="#8B5210"/>
    <rect x="365" y="290" width="4" height="20" rx="1" fill="#8B5210"/>
    <rect x="385" y="290" width="4" height="20" rx="1" fill="#8B5210"/>
    <line x1="345" y1="296" x2="389" y2="296" stroke="#8B5210" stroke-width="1.5"/>
    <line x1="345" y1="303" x2="389" y2="303" stroke="#8B5210" stroke-width="1.5"/>

    <!-- Watering can (decorative, front) -->
    <ellipse cx="530" cy="318" rx="14" ry="9" fill="#7A4B1A" opacity="0.75"/>
    <rect x="517" y="312" width="26" height="14" rx="4" fill="#8B5210" opacity="0.75"/>
    <path d="M543 316 Q558 312 558 320" stroke="#8B5210" stroke-width="2.5" fill="none" stroke-linecap="round" opacity="0.75"/>
    <!-- water drops -->
    <circle cx="558" cy="324" r="2" fill="#b8d9f0" opacity="0.8"/>
    <circle cx="562" cy="327" r="1.5" fill="#b8d9f0" opacity="0.8"/>
    <circle cx="555" cy="328" r="1.5" fill="#b8d9f0" opacity="0.8"/>
    <!-- handle -->
    <path d="M524 312 Q520 304 528 302" stroke="#5A3A10" stroke-width="2" fill="none" stroke-linecap="round" opacity="0.75"/>

  </svg>

  <!-- Stats -->
  <div class="stats-row">
    <div class="stat-pill">
      <div class="stat-number">2,400+</div>
      <div class="stat-label">Farmers joined</div>
      <a href="#" class="stat-btn">Join as farmer</a>
    </div>
    <div class="stat-pill">
      <div class="stat-number">850+</div>
      <div class="stat-label">Equipment listed</div>
      <a href="#" class="stat-btn">Browse tools</a>
    </div>
  </div>

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

