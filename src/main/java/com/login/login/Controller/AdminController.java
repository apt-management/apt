package com.login.login.Controller;

import com.login.login.Model.Admin;
import com.login.login.Model.Delivery;
import com.login.login.Service.AdminService;
import com.login.login.Service.DeliveryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/admin/main")
    public String adminMain(HttpServletRequest request, HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");

        if (admin == null) {
            return "redirect:/login";
        }

        List<Delivery> deliveryList = deliveryService.getAllDeliveries();

        List<Delivery> newDeliveryList = new ArrayList<>();
        List<Delivery> deliveringList = new ArrayList<>();
        List<Delivery> deliveredList = new ArrayList<>();

        for (Delivery d : deliveryList) {
            switch (d.getStatus()) {
                case "배송 전":
                    newDeliveryList.add(d);
                    break;
                case "배송 중":
                    deliveringList.add(d);
                    break;
                case "배송 완료":
                    deliveredList.add(d);
                    break;
            }
        }

        long totalDelivery = newDeliveryList.size();
        long totalDelivering = deliveringList.size();
        long totalDelivered = deliveredList.size();

        model.addAttribute("admin", admin);
        model.addAttribute("requestURI", request.getRequestURI());

        model.addAttribute("totalDelivery", totalDelivery);
        model.addAttribute("totalDelivering", totalDelivering);
        model.addAttribute("totalDelivered", totalDelivered);

        return "admin/main";
    }

    @GetMapping("/admin/delivering")
    public String delivering(HttpServletRequest request, Model model) {
        List<Delivery> deliveryList = deliveryService.getAllDeliveries();

        List<Delivery> deliveringList = new ArrayList<>();
        for (Delivery d : deliveryList) {
            if (d.getStatus().equals("배송 중")) {
                deliveringList.add(d);
            }
        }

        long totalDelivering = deliveringList.size();

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("deliveries", deliveringList);
        model.addAttribute("totalDelivering", totalDelivering);
        return "admin/delivering";
    }

    @GetMapping("/admin/delivered")
    public String delivered(HttpServletRequest request, Model model) {
        List<Delivery> deliveryList = deliveryService.getAllDeliveries();

        List<Delivery> deliveredList = new ArrayList<>();
        for (Delivery d : deliveryList) {
            if (d.getStatus().equals("배송 완료")) {
                deliveredList.add(d);
            }
        }

        long totalDelivered = deliveredList.size();

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("deliveries", deliveredList);
        model.addAttribute("totalDelivered", totalDelivered);
        return "admin/delivered";
    }


    @GetMapping("/admin/patrol_issue")
    public String patrolIssue(HttpServletRequest request, Model model) {

        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/patrol_issue";
    }

    @GetMapping("/admin/rosPolice")
    public String testPage() {
        return "admin/ros_police";
    }

    @GetMapping("/admin/rosPost")
    public String rosPost(Model model) {

        List<Delivery> deliveryList = deliveryService.getAllDeliveries();
        List<Delivery> deliveries = new ArrayList<>();

        Set<String> uniqueAddresses = new HashSet<>();

        for (Delivery d : deliveryList) {
            if (d.getStatus().equals("배송 전") &&
                    (d.getAddress().equals("101동") || d.getAddress().equals("102동") || d.getAddress().equals("103동"))) {

                if (uniqueAddresses.add(d.getAddress())) {
                    deliveries.add(d);
                }
            }
        }

        model.addAttribute("deliveries", deliveries);

        return "/admin/ros_post";
    }

    @GetMapping("/adminLog")
    public String adminLogin() {
        return "admin/admin_login";
    }

    @PostMapping("/adminLog")
    public String login(@RequestParam String number,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Admin admin = adminService.getAdminByNumber(number);

        if (adminService.isValidAdmin(number, password)) {
            session.setAttribute("admin", admin);

            return "redirect:/admin/main";
        } else {
            model.addAttribute("error", "전화번호 또는 비밀번호가 잘못되었습니다.");
            return "admin/admin_login";
        }
    }

    @GetMapping("/admin/issueDetail")
    public String issueDetail() {
        return "admin/patrol_issue_detail";
    }

}
