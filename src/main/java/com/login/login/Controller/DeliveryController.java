package com.login.login.Controller;

import com.login.login.Model.Delivery;
import com.login.login.Service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/add")
    public String addDelivery(){
        return "admin/delivery_add";
    }

    @PostMapping("/add")
    public String addDelivery(
            @RequestParam String name,
            @RequestParam String number,
            @RequestParam String address,
            @RequestParam String trackingNumber,
            @RequestParam String status,
            Model model) {

        try {
            // 새로운 Delivery 객체 생성
            Delivery delivery = new Delivery(name, number, address, trackingNumber, status);

            // Delivery 서비스로 저장
            deliveryService.addDelivery(delivery);

            // 리다이렉트할 URL을 설정
            return "redirect:/admin/new_delivery";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/delivery_add";
        }
    }

}
