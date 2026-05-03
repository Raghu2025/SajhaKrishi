package com.SajhaKrishi.authGuard;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.SajhaKrishi.model.User;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getServletPath();
        User   user = (User) request.getSession(false) != null
                    ? (User) request.getSession().getAttribute("loggedInUser")
                    : null;

        boolean isLoggedIn    = (user != null);
        boolean isPublicPage  = isPublicPage(path);

        // ── 1. Public pages — let through always ──
        if (isPublicPage) {
            chain.doFilter(request, response);
            return;
        }

        // ── 2. Not logged in — send to login ──
        if (!isLoggedIn) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ── 3. Logged in — check role matches URL ──
        int role = user.getRole(); // 1=Admin, 2=Owner, 3=Kisan

        if (path.startsWith("/admin/") && role != 1) {
            response.sendRedirect(request.getContextPath() + "/unauthorized");
            return;
        }

        if (path.startsWith("/owner/") && role != 2) {
            response.sendRedirect(request.getContextPath() + "/unauthorized");
            return;
        }

        if (path.startsWith("/kisan/") && role != 3) {
            response.sendRedirect(request.getContextPath() + "/unauthorized");
            return;
        }

        // ── 4. All checks passed — let through ──
        chain.doFilter(request, response);
    }

    // Pages anyone can access without login
    private boolean isPublicPage(String path) {
        return path.equals("/login")
            || path.equals("/register")
            || path.equals("/")
            || path.startsWith("/css/")
            || path.startsWith("/js/")
            || path.startsWith("/images/")
            || path.equals("/unauthorized");
    }
}