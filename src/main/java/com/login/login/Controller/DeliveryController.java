package com.login.login.Controller;

import com.login.login.Model.Delivery;
import com.login.login.Service.DeliveryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    @Autowired
    private final DeliveryService deliveryService;

    @GetMapping("/add")
    public String addDelivery(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/delivery_add";
    }

    @PostMapping("/add")
    public String addDelivery(@RequestParam String name,
                              @RequestParam String number,
                              @RequestParam String address,
                              @RequestParam String trackingNumber,
                              @RequestParam String status,
                              RedirectAttributes redirectAttributes) {

        try {
            Delivery delivery = new Delivery(name, number, address, trackingNumber, status);
            deliveryService.addDelivery(delivery);
            redirectAttributes.addFlashAttribute("newDelivery", delivery);
            return "redirect:/admin/delivery";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/delivery/add";
        }
    }

    @GetMapping
    public String newDelivery(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "number", required = false) String number,
                              HttpServletRequest request, Model model) {

        int pageSize = 10;
        page = (page < 1) ? 0 : page - 1;

        Page<Delivery> delivery;
        if (name == null && number == null) {
            delivery = deliveryService.getDelivery(page, pageSize);
        } else {
            delivery = deliveryService.searchDelivery(name, number, page, pageSize);
        }

        List<Delivery> deliveryList = deliveryService.getAllDeliveries();
        long totalDelivery = deliveryService.getTotalDeliveryCount();

        setPaginationAndModel(page + 1, (int) delivery.getTotalElements(), pageSize, model, name + number);

        model.addAttribute("delivery", delivery.getContent());
        model.addAttribute("deliveries", deliveryList);
        model.addAttribute("totalDelivery", totalDelivery);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/new_delivery";
    }

    private void setPaginationAndModel(int page, int totalDelivery, int pageSize, Model model, String searchQuery) {
        int totalPages = totalDelivery > 0 ? (int) Math.ceil((double) totalDelivery / pageSize) : 1;

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        int startPage = ((page - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPages);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if (searchQuery != null) {
            model.addAttribute("searchQuery", searchQuery);
        }
    }

    @GetMapping("/search")
    public String searchDelivery(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "number", required = false) String number,
                                 @RequestParam(defaultValue = "1") int page,
                                 HttpServletRequest request, Model model) {

        int pageSize = 10;
        Page<Delivery> delivery;

        page = page - 1;

        if ((name == null || name.isEmpty()) && (number == null || number.isEmpty())) {
            delivery = deliveryService.getDelivery(page, pageSize);
            setPaginationAndModel(page + 1, (int) delivery.getTotalElements(), pageSize, model, null);
        } else {
            delivery = deliveryService.searchDelivery(name, number, page, pageSize);
            setPaginationAndModel(page + 1, (int) delivery.getTotalElements(), pageSize, model, name + number);
        }

        List<Delivery> deliveryList = deliveryService.getAllDeliveries();

        model.addAttribute("delivery", delivery.getContent());
        model.addAttribute("deliveries", deliveryList);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/new_delivery";
    }
}
