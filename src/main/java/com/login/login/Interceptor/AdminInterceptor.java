package com.login.login.Interceptor;

import com.login.login.Model.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import io.github.cdimascio.dotenv.Dotenv;

public class AdminInterceptor implements HandlerInterceptor {

    private static final String ADMIN_NUMBER = Dotenv.load().get("ADMIN_NUMBER");
    private static final String ADMIN_PASSWORD = Dotenv.load().get("ADMIN_PASSWORD");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin admin = (Admin) request.getSession().getAttribute("admin");

        boolean isAdmin = (admin != null && ADMIN_NUMBER.equals(admin.getNumber()) && ADMIN_PASSWORD.equals(admin.getPassword()));
        boolean isAdminPage = request.getRequestURI().startsWith("/admin");

        if (isAdminPage && !isAdmin) {
            response.sendRedirect("/");
            return false;
        }

        if (isAdmin && !isAdminPage) {
            response.sendRedirect("/admin/main");
            return false;
        }

        return true;
    }

}
