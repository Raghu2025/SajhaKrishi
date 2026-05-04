<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Landing Page | Sajha Krishi</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
        integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/landing-page.css">
    
</head>
<body>
 	<jsp:include page="public-navbar.jsp" />
    <section class="container hero">
        <div>
            <h1>Professional Agricultural Tools, <span>Affordable Rentals</span></h1>
            <p>Rent or buy farming equipment without heavy investment.</p>
            <button class="btn btn-primary">Browse Tools</button>
            <button class="btn btn-outline">Get Started</button>
        </div>

        <div class="hero-box" style="background-image: url('${pageContext.request.contextPath}/assets/tractor.jpg');">
            <%-- <div>
                <p>Equipment</p>
            </div>--%>
        </div>
    </section>

    <section id="features" class="container section">
        <div class="section-title">
            <h2>Why Choose Sajha Krishi?</h2>
            <p>Everything you need for farming</p>
        </div>

        <div class="grid-3">
            <div class="card">
                <h3>Wide Selection</h3>
                <p>Many tools available</p>
            </div>
            <div class="card">
                <h3>Flexible Rental</h3>
                <p>Short or long term</p>
            </div>
            <div class="card">
                <h3>Quality</h3>
                <p>Maintained equipment</p>
            </div>
            <div class="card">
                <h3>Affordable</h3>
                <p>Save money</p>
            </div>
            <div class="card">
                <h3> Easy Booking</h3>
                <p>Quick system</p>
            </div>
            <div class="card">
                <h3>Support</h3>
                <p>Expert help</p>
            </div>
        </div>
    </section>

    <section id="how" class="container section">
        <div class="how">
            <div class="section-title">
                <h2>How It Works</h2>
            </div>

            <div class="grid-4">
                <div class="step">
                    <div class="step-circle">1</div>
                    <p>Browse</p>
                </div>
                <div class="step">
                    <div class="step-circle">2</div>
                    <p>Book</p>
                </div>
                <div class="step">
                    <div class="step-circle">3</div>
                    <p>Pickup</p>
                </div>
                <div class="step">
                    <div class="step-circle">4</div>
                    <p>Return</p>
                </div>
            </div>
        </div>
    </section>

    <section class="container section">
        <div class="cta">
            <h2>Ready to Rent?</h2>
            <p>Create an account today</p>
            <div>
                <button class="btn btn-primary" onclick="location.href='register'">Create Account</button>
                <button class="btn btn-outline" onclick="location.href='browse'">Browse</button>
            </div>
        </div>
    </section>

    <footer>
        <div class="container footer-grid">
            <div>
                <h4>Sajha Krishi</h4>
                <p>Farm rental platform</p>
            </div>
            <div>
                <h4>Browse</h4>
                <a href="browse?category=tool">Tools</a>
            </div>
            <div>
                <h4>Account</h4>
                <a href="login">Login</a>
            </div>
            <div>
                <h4>Support</h4>
                <a href="contact">Contact</a>
            </div>
        </div>

        <div class="copyright">
            © 2026 Sajha Krishi
        </div>
    </footer>

</body>
</html>