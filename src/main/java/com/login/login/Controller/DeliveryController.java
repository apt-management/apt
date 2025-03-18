package com.login.login.Controller;

import com.login.login.Model.Delivery;
import com.login.login.Model.DeliveryRequest;
import com.login.login.Service.DeliveryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
                              /*@RequestParam LocalDateTime delivered_at,*/
                              RedirectAttributes redirectAttributes) {

        try {
            Delivery delivery = new Delivery(name, number, address, trackingNumber, status); /*delivered_at*/
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

        List<Delivery> newDeliveryList = new ArrayList<>();
        for(Delivery d : deliveryList) {
            if(d.getStatus().equals("배송 전")){
                newDeliveryList.add(d);
            }
        }

        long totalDelivery = newDeliveryList.size();

        setPaginationAndModel(page + 1, (int) delivery.getTotalElements(), pageSize, model, name + number);

        model.addAttribute("delivery", delivery.getContent());
        model.addAttribute("deliveries", newDeliveryList);
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
        long totalDelivery = deliveryService.getTotalDeliveryCount();

        model.addAttribute("delivery", delivery.getContent());
        model.addAttribute("totalDelivery", totalDelivery);
        model.addAttribute("deliveries", deliveryList);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/new_delivery";
    }

    @PostMapping("/rosPost/{address}")
    public ResponseEntity<String> startDelivery(@PathVariable String address) {
        deliveryService.changePendingToInProgress(address);
        return ResponseEntity.ok(address + " 지역의 배송이 시작되었습니다.");
    }

    @PostMapping("/rosPost")
    public ResponseEntity<String> rosPost(@RequestBody DeliveryRequest request) {
        boolean isUpdated = deliveryService.rosPost(request.getAddress(), request.getStatus());

        System.out.println(request.getAddress());
        System.out.println(request.getStatus());

        if (isUpdated) {
            return ResponseEntity.ok("배송 상태가 업데이트 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("배송 상태 업데이트 실패");
        }
    }
}
