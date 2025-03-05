package com.login.login.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Object userObj = session.getAttribute("user");

        if (userObj == null) {
            return "redirect:/login";
        }

        model.addAttribute("message", "관리자 페이지에 오신 것을 환영합니다");
        return "admin/admin_dashboard";
    }

    // 입주민 정보
    @GetMapping("/admin/resident_info")
    public String residentInfo() {
        return "admin/resident_info";
    }

    // 공지사항 페이지
    @GetMapping("/admin/admin_notice")
    public String notice() {
        return "admin/notice";
    }

    // 신규 택배 페이지
    @GetMapping("/admin/new_delivery")
    public String newDelivery() {
        return "admin/new_delivery";
    }

    // 배송 중 페이지
    @GetMapping("/admin/delivering")
    public String delivering() {
        return "admin/delivering";
    }

    // 배송 완료 페이지
    @GetMapping("/admin/delivered")
    public String delivered() {
        return "admin/delivered";
    }

    // 순찰 중 이상사항 페이지
    @GetMapping("/admin/patrol_issue")
    public String patrolIssue() {
        return "admin/patrol_issue";
    }

    @GetMapping("/admin/main")
    public String Main() {
        return "admin/main";
    }

}
