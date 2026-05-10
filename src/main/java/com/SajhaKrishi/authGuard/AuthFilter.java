package com.SajhaKrishi.authGuard;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

import com.SajhaKrishi.constant.ApiConstant;
import com.SajhaKrishi.model.User;

//@WebFilter("/*")
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String path = request.getServletPath();

		boolean isPublicPage = isPublicPage(path);
		 chain.doFilter(request, response);
		 return;

//		// Public pages
//		if (isPublicPage) {
//			chain.doFilter(request, response);
//			return;
//		}
//
//		HttpSession session = request.getSession(false);
//
//		User user = null;
//
//		if (session != null) {
//			user = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);
//		}
//
//		boolean isLoggedIn = (user != null);
////        
////        User  user = (User) request.getSession(false) != null
////                ? (User) request.getSession().getAttribute(ApiConstant.USER_SESSION_KEY)
////                : null;
//
//		// 2. Not logged in
//		if (!isLoggedIn) {
//			response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
//			return;
//		}
////
////        // ── 3. Logged in — check role matches URL ──
////        int role = user.getRole(); // 1=Admin, 2=Owner, 3=Kisan
////
////        if (path.startsWith("/admin/") && role != 1) {
////            response.sendRedirect(request.getContextPath() + "/unauthorized");
////            return;
////        }
////
////        if (path.startsWith("/owner/") && role != 2) {
////            response.sendRedirect(request.getContextPath() + "/unauthorized");
////            return;
////        }
////
////        if (path.startsWith("/kisan/") && role != 3) {
////            response.sendRedirect(request.getContextPath() + "/unauthorized");
////            return;
////        }
//
//		// 4. All checks passed — let through
//		chain.doFilter(request, response);
	}

	// Pages anyone can access without login
	private boolean isPublicPage(String path) {
		return path.equals(ApiConstant.LOGIN) || path.equals(ApiConstant.REGISTER) || path.equals(ApiConstant.HOME)
				|| path.equals("/") || path.startsWith(ApiConstant.CSS) || path.startsWith(ApiConstant.JS)
				|| path.startsWith(ApiConstant.IMAGES) || path.equals(ApiConstant.KISSAN_EQUIPMENT)
				|| path.equals(ApiConstant.UNAUTHORIZED);
	}
}