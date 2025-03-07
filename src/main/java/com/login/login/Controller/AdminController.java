package com.login.login.Controller;

import com.login.login.Model.Admin;
import com.login.login.Service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/main")
    public String adminMain(HttpServletRequest request, HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");  // Admin 객체로 캐스팅

        if (admin == null) {
            return "redirect:/login";
        }

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("admin", admin);  // admin 객체를 모델에 추가
        return "admin/main";
    }


    @GetMapping("/admin/new_delivery")
    public String newDelivery(HttpServletRequest request, Model model) {

        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/new_delivery";
    }

    @GetMapping("/admin/delivering")
    public String delivering(HttpServletRequest request, Model model) {

        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/delivering";
    }

    @GetMapping("/admin/delivered")
    public String delivered(HttpServletRequest request, Model model) {

        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/delivered";
    }

    @GetMapping("/admin/patrol_issue")
    public String patrolIssue(HttpServletRequest request, Model model) {

        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/patrol_issue";
    }

    @GetMapping("/admin/test")
    public String testPage() {
        return "/admin/test";
    }

    @GetMapping("/admin/rosPost")
    public String rosPost() {
        return "/admin/ros_post";
    }

    @GetMapping("/adminLog")
    public String adminLogin() {
        return "/admin/admin_login";
    }
    @PostMapping("/adminLog")
    public String login(@RequestParam String number,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        System.out.println(number + " " + password);

        Admin admin = adminService.getAdminByNumber(number);

        if (adminService.isValidAdmin(number, password)) {
            session.setAttribute("admin", admin); // number로 세션에 저장
            System.out.println("valid!");

            return "redirect:/admin/main"; // 로그인 성공 시 관리자 메인 이동
        } else {
            model.addAttribute("error", "전화번호 또는 비밀번호가 잘못되었습니다.");
            System.out.println("invalid!");
            return "admin/admin_login"; // 로그인 실패 시 다시 로그인 페이지로
        }
    }

}
