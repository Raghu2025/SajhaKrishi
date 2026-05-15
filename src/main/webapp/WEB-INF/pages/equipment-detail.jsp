<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.SajhaKrishi.model.*"%>
<%@ page import="com.SajhaKrishi.constant.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<jsp:useBean id="api" class="com.SajhaKrishi.constant.ApiConstant" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${equipment.name} | SajhaKrishi</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/equipment-detail.css">
</head>
<body>

<%
  HttpSession userSession = request.getSession(false);
  User currentUser = (User) (userSession != null ? userSession.getAttribute(ApiConstant.USER_SESSION_KEY) : null);
  pageContext.setAttribute("currentUser", currentUser);

  /* Pull flash messages set via session, then remove them immediately */
  String flashError   = (String) request.getSession().getAttribute("error");
  String flashSuccess = (String) request.getSession().getAttribute("success");
  request.getSession().removeAttribute("error");
  request.getSession().removeAttribute("success");
  pageContext.setAttribute("flashError",   flashError);
  pageContext.setAttribute("flashSuccess", flashSuccess);
%>

<jsp:include page="public-navbar.jsp" />

<!-- ── FLASH MESSAGES (full-width, above everything) ──────── -->
<c:if test="${not empty flashError}">
  <div class="flash flash-error">
    <i class="fa fa-circle-exclamation"></i>
    <span>${flashError}</span>
    <button class="flash-close" onclick="this.parentElement.remove()"><i class="fa fa-xmark"></i></button>
  </div>
</c:if>
<c:if test="${not empty flashSuccess}">
  <div class="flash flash-success">
    <i class="fa fa-circle-check"></i>
    <span>${flashSuccess}</span>
    <button class="flash-close" onclick="this.parentElement.remove()"><i class="fa fa-xmark"></i></button>
  </div>
</c:if>

<!-- ── BREADCRUMB ──────────────────────────────────────────── -->
<div class="breadcrumb-bar">
  <div class="container">
    <a href="${pageContext.request.contextPath}<%= api.KISSAN_EQUIPMENT %><%= api.LIST %>" class="back-link">
      <i class="fa fa-arrow-left"></i> Back to Browse
    </a>
    <span class="breadcrumb-sep">/</span>
    <span class="breadcrumb-cat">${equipment.categoryName}</span>
    <span class="breadcrumb-sep">/</span>
    <span class="breadcrumb-current">${equipment.name}</span>
  </div>
</div>

<!-- ── MAIN CONTENT ───────────────────────────────────────── -->
<div class="main-wrapper">
  <div class="container main-grid">

    <!-- ══ LEFT COLUMN ══════════════════════════════════════ -->
    <div class="left-col">

      <!-- Image -->
      <div class="image-frame">
        <c:choose>
          <c:when test="${not empty equipment.imagePath}">
            <img src="${pageContext.request.contextPath}/${equipment.imagePath}"
                 alt="${equipment.name}" class="equipment-img"
                 onerror="this.src='${pageContext.request.contextPath}/assets/noImage.svg'" />
          </c:when>
          <c:otherwise>
            <div class="image-placeholder"><i class="fa fa-tractor"></i></div>
          </c:otherwise>
        </c:choose>

        <span class="condition-pill condition-${equipment.conditionClass}">
          <i class="fa fa-circle-check"></i> ${equipment.condition}
        </span>
      </div>

      <!-- Title -->
      <div class="title-block">
        <div class="category-tag"><i class="fa fa-tag"></i> ${equipment.categoryName}</div>
        <h1 class="product-title">${equipment.name}</h1>
      </div>

      <!-- Description -->
      <p class="desc">${equipment.description}</p>

      <!-- Specs -->
      <div class="specs-section">
        <h3 class="section-heading"><i class="fa fa-list-check"></i> Details</h3>
        <ul class="features-list">
          <c:if test="${not empty equipment.brand}">
            <li><i class="fa fa-industry"></i> Brand: <strong>${equipment.brand}</strong></li>
          </c:if>
          <c:if test="${equipment.manufactureYear > 0}">
            <li><i class="fa fa-calendar"></i> Year: <strong>${equipment.manufactureYear}</strong></li>
          </c:if>
          <c:if test="${not empty equipment.fuelType}">
            <li><i class="fa fa-gas-pump"></i> Fuel: <strong>${equipment.fuelType}</strong></li>
          </c:if>
          <c:if test="${not empty equipment.condition}">
            <li><i class="fa fa-star"></i> Condition: <strong>${equipment.condition}</strong></li>
          </c:if>
          <c:if test="${not empty equipment.specifications}">
            <li><i class="fa fa-screwdriver-wrench"></i> Specs: <strong>${equipment.specifications}</strong></li>
          </c:if>
          <c:if test="${not empty equipment.district}">
            <li>
              <i class="fa fa-location-dot"></i> Location:
              <strong>${equipment.district}<c:if test="${not empty equipment.municipality}">, ${equipment.municipality}</c:if></strong>
            </li>
          </c:if>
        </ul>
      </div>

      <!-- Address card -->
      <c:if test="${not empty equipment.address}">
        <div class="owner-card">
          <div class="owner-avatar"><i class="fa fa-map-pin"></i></div>
          <div class="owner-info">
            <div class="owner-name">
              ${equipment.district}<c:if test="${not empty equipment.municipality}">, ${equipment.municipality}</c:if>
            </div>
            <div class="owner-location"><i class="fa fa-location-dot"></i> ${equipment.address}</div>
          </div>
        </div>
      </c:if>

    </div><!-- /left-col -->

    <!-- ══ RIGHT COLUMN — Booking Card ══════════════════════ -->
    <div class="right-col">
      <div class="booking-card">

        <!-- Price header -->
        <div class="price-row">
          <span class="price-amount">Rs. ${equipment.pricePerDay}</span>
          <span class="price-unit">/ day</span>
        </div>
        <c:if test="${equipment.pricePerHour > 0}">
          <div class="price-alt">Also Rs. ${equipment.pricePerHour} / hour</div>
        </c:if>
        <c:if test="${equipment.depositAmount > 0}">
          <div class="price-deposit">
            <i class="fa fa-circle-info"></i> Deposit: Rs. ${equipment.depositAmount}
          </div>
        </c:if>

        <!-- Availability badge -->
        <div class="avail-row">
          <c:choose>
            <c:when test="${equipment.availabilityStatus == 'A'}">
              <span class="status-dot available"></span>
              <span class="status-text available-text">Available for Rent</span>
            </c:when>
            <c:otherwise>
              <span class="status-dot unavailable"></span>
              <span class="status-text unavailable-text">Currently Unavailable</span>
            </c:otherwise>
          </c:choose>
        </div>

        <!-- ── BOOKING FORM ─────────────────────────────── -->
        <c:choose>
          <c:when test="${not empty currentUser}">

            <%-- Inline form error (set by this page's own JS validation) --%>
            <div class="form-error-box" id="formErrorBox" style="display:none;">
              <i class="fa fa-triangle-exclamation"></i>
              <span id="formErrorMsg"></span>
            </div>

            <form id="rentalForm"
                  action="${pageContext.request.contextPath}${ApiConstant.BOOKING}"
                  method="post"
                  onsubmit="return validateForm()">

              <input type="hidden" name="equipmentId" value="${equipment.id}" />

              <!-- Dates -->
              <div class="form-row">
                <div class="form-group">
                  <label for="start-date"><i class="fa fa-calendar-days"></i> Start Date</label>
                  <input id="start-date" name="startDate" type="date"
                         required min="${today}" oninput="calcTotal()" />
                </div>
                <div class="form-group">
                  <label for="end-date"><i class="fa fa-calendar-check"></i> End Date</label>
                  <input id="end-date" name="endDate" type="date"
                         required min="${today}" oninput="calcTotal()" />
                </div>
              </div>

              <!-- Pickup address — required by controller -->
              <div class="form-group">
                <label for="pickupAddress"><i class="fa fa-truck"></i> Pickup Address</label>
                <input id="pickupAddress" name="pickupAddress" type="text"
                       placeholder="Where should the equipment be delivered?"
                       required maxlength="255" />
              </div>

              <!-- Notes — optional, sent as empty string if blank -->
              <div class="form-group">
                <label for="notes"><i class="fa fa-note-sticky"></i> Notes <span class="label-optional">(optional)</span></label>
                <textarea id="notes" name="notes" rows="2"
                          placeholder="Any special instructions for the owner…"
                          maxlength="500"></textarea>
              </div>

              <!-- Summary box -->
              <div class="summary-box" id="summaryBox" style="display:none;">
                <div class="summary-row">
                  <span>Duration</span>
                  <span id="durationText">—</span>
                </div>
                <div class="summary-row">
                  <span>Rate</span>
                  <span>Rs. ${equipment.pricePerDay} × <span id="daysCount">0</span> days</span>
                </div>
                <div class="summary-divider"></div>
                <div class="summary-row total-row">
                  <span>Rental Total</span>
                  <span class="total-price" id="totalPrice">Rs. 0</span>
                </div>
                <c:if test="${equipment.depositAmount > 0}">
                  <div class="summary-row">
                    <span>+ Deposit</span>
                    <span>Rs. ${equipment.depositAmount}</span>
                  </div>
                </c:if>
              </div>

              <!-- Submit / disabled -->
              <c:choose>
                <c:when test="${equipment.availabilityStatus == 'A'}">
                  <button type="submit" class="btn-book">
                    <i class="fa fa-handshake"></i> Confirm Rental
                  </button>
                </c:when>
                <c:otherwise>
                  <button type="button" class="btn-book btn-disabled" disabled>
                    <i class="fa fa-clock"></i> Not Available
                  </button>
                </c:otherwise>
              </c:choose>

              <p class="note"><i class="fa fa-envelope"></i> Confirmation will be sent to your email</p>
            </form>

          </c:when>
          <c:otherwise>
            <%-- Not logged in — show login prompt instead of form --%>
            <div class="login-prompt">
              <i class="fa fa-lock fa-2x"></i>
              <p>Please log in to book this equipment</p>
              <a href="${pageContext.request.contextPath}/login?returnUrl=${encodedReturnUrl}"
                 class="btn-book" style="text-decoration:none; display:flex; align-items:center; justify-content:center; gap:8px;">
                <i class="fa fa-right-to-bracket"></i> Login to Book
              </a>
            </div>
          </c:otherwise>
        </c:choose>

        <!-- Policy (always shown) -->
        <div class="policy-row">
          <div class="policy-item">
            <i class="fa fa-rotate-left"></i>
            <span>Free cancellation<br><small>Up to 24hrs before</small></span>
          </div>
          <div class="policy-item">
            <i class="fa fa-shield"></i>
            <span>Insured rental<br><small>Covered by Sajha</small></span>
          </div>
        </div>

      </div><!-- /booking-card -->
    </div><!-- /right-col -->

  </div><!-- /main-grid -->
</div><!-- /main-wrapper -->

<script>
  const pricePerDay = ${equipment.pricePerDay};

  /* ── Price calculator ── */
  function calcTotal() {
    const start = document.getElementById('start-date').value;
    const end   = document.getElementById('end-date').value;
    const box   = document.getElementById('summaryBox');

    if (!start || !end) { box.style.display = 'none'; return; }

    const s    = new Date(start);
    const e    = new Date(end);
    const diff = Math.ceil((e - s) / (1000 * 60 * 60 * 24));

    if (diff <= 0) { box.style.display = 'none'; return; }

    document.getElementById('daysCount').textContent    = diff;
    document.getElementById('durationText').textContent = diff + (diff === 1 ? ' day' : ' days');
    document.getElementById('totalPrice').textContent   = 'Rs. ' + (pricePerDay * diff).toLocaleString();
    box.style.display = 'block';
  }

  /* ── Client-side validation (mirrors controller checks) ── */
  function showFormError(msg) {
    const box = document.getElementById('formErrorBox');
    document.getElementById('formErrorMsg').textContent = msg;
    box.style.display = 'flex';
    box.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
  }

  function validateForm() {
    const start   = document.getElementById('start-date').value;
    const end     = document.getElementById('end-date').value;
    const pickup  = document.getElementById('pickupAddress').value.trim();
    const today   = new Date(); today.setHours(0,0,0,0);

    document.getElementById('formErrorBox').style.display = 'none';

    if (!start || !end) {
      showFormError('Please select both start and end dates.');
      return false;
    }
    if (new Date(start) < today) {
      showFormError('Start date cannot be in the past.');
      return false;
    }
    if (new Date(end) <= new Date(start)) {
      showFormError('End date must be after start date.');
      return false;
    }
    if (!pickup) {
      showFormError('Pickup address is required.');
      return false;
    }
    return true;
  }

  /* ── Auto-dismiss flash messages after 5 s ── */
  document.querySelectorAll('.flash').forEach(function(el) {
    setTimeout(function() {
      el.style.opacity = '0';
      el.style.transition = 'opacity 0.4s';
      setTimeout(function() { el.remove(); }, 400);
    }, 5000);
  });
</script>

</body>
</html>
