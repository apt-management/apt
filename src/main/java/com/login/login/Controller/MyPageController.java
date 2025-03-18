package com.login.login.Controller;

import com.login.login.Service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public String myPage(Model model) {

        long totalDelivery = deliveryService.getTotalDeliveryCount();
        model.addAttribute("totalDelivery", totalDelivery);

        return "mypage";
    }

    @GetMapping("/deliver")
    public String deliver() {
        return "/mypage/deliver";
    }

    @GetMapping("/deliver_completed")
    public String deliverCompleted() {
        return "/mypage/deliver_completed";
    }

    @GetMapping("/modify_info")
    public String modifyInfo() {
        return "/mypage/modify_info";
    }

    @GetMapping("/new_parcel")
    public String newParcel() {
        return "/mypage/new_parcel";
    }

    @GetMapping("/notify")
    public String notifyPage() {
        return "/mypage/notify";
    }

    @GetMapping("/withdrawal")
    public String withdrawal() {
        return "/mypage/withdrawal";
    }

    @GetMapping("/notice")
    public String notice() {
        return "/notice";
    }
}
