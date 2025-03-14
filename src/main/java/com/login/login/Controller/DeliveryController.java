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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/add")
    public String addDelivery(Model model) {
        List<Delivery> deliveryList = deliveryService.getAllDeliveries();

        if (deliveryList == null) {
            deliveryList = new ArrayList<>();
        }

        model.addAttribute("deliveries", deliveryList);
        return "admin/delivery_add";
    }

    @PostMapping("/add")
    public String addDelivery(
            @RequestParam String name,
            @RequestParam String number,
            @RequestParam String address,
            @RequestParam String trackingNumber,
            @RequestParam String status,
            RedirectAttributes redirectAttributes, Model model) {

        try {
            Delivery delivery = new Delivery(name, number, address, trackingNumber, status);

            deliveryService.addDelivery(delivery);

            redirectAttributes.addFlashAttribute("newDelivery", delivery);

            return "redirect:/admin/new_delivery";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/delivery/add";
        }
    }

    @GetMapping("/list")
    public String getDeliveries(
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Delivery> deliveryPage = deliveryService.getDeliveries(pageable);

        System.out.println("Deliveries fetched: " + deliveryPage.getContent());

        model.addAttribute("deliveries", deliveryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", deliveryPage.getTotalPages());
        model.addAttribute("totalItems", deliveryPage.getTotalElements());

        return "admin/new_delivery";
    }
}
