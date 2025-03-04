package com.login.login.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.login.login.Model.User;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    private static final String ADMIN_NUMBER = "01000000000";
    private static final String ADMIN_PASSWORD = "admin1234";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && ADMIN_NUMBER.equals(user.getNumber()) && ADMIN_PASSWORD.equals(user.getPassword())) {
            if (!request.getRequestURI().startsWith("/admin")) {
                response.sendRedirect("/admin/dashboard");
                return false;
            }
        }
        return true;
    }
}
