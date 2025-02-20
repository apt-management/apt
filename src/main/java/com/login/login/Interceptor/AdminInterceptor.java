/*
package com.login.login.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;s

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String number = (String) session.getAttribute("number");

        // 관리자인 경우
        if (number != null && "01000000000".equals(number)) {
            // 계속 진행
            return true;
        } else {
            // 관리자가 아닐 경우 로그인 페이지로 리다이렉트
            response.sendRedirect("/login");
            return false;
        }
    }
}
*/