package com.login.login.Controller;

import com.login.login.Model.User;
import com.login.login.Service.ResidentService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/residents")
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    private void setPaginationAndModel(int page, int totalResidents, int pageSize, Model model, String searchQuery) {
        int totalPages = totalResidents > 0 ? (int) Math.ceil((double) totalResidents / pageSize) : 1;

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

    @GetMapping
    public String residentList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "number", required = false) String number,
                               Model model) {

        int pageSize = 10;

        page = (page < 1) ? 0 : page - 1;

        Page<User> residents;

        if (name == null && number == null) {
            residents = residentService.getResidents(page, pageSize);
        } else {
            residents = residentService.searchResidents(name, number, page, pageSize);
        }

        long totalResidents = residentService.getTotalResidentCount();

        setPaginationAndModel(page + 1, (int) residents.getTotalElements(), pageSize, model, name + number);

        model.addAttribute("residents", residents.getContent());
        model.addAttribute("totalResidents", totalResidents);
        return "admin/resident_info";
    }

    @GetMapping("/search")
    public String searchResidents(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "number", required = false) String number,
                                  @RequestParam(defaultValue = "1") int page,
                                  Model model) {

        int pageSize = 10;
        Page<User> residents;

        page = page - 1;

        if ((name == null || name.isEmpty()) && (number == null || number.isEmpty())) {
            residents = residentService.getResidents(page, pageSize);
            setPaginationAndModel(page + 1, (int) residents.getTotalElements(), pageSize, model, null);
        } else {
            residents = residentService.searchResidents(name, number, page, pageSize);
            setPaginationAndModel(page + 1, (int) residents.getTotalElements(), pageSize, model, name + number);
        }

        model.addAttribute("residents", residents.getContent());
        return "admin/resident_info";
    }

    @GetMapping("/add")
    public String addResidentForm() {
        return "admin/resident_add";
    }

    @PostMapping("/add")
    public String addResident(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (residentService.existsByNumber(user.getNumber())) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 존재하는 전화번호입니다.");
            return "redirect:/admin/residents/add";
        } else {
            residentService.addResident(user);
            redirectAttributes.addFlashAttribute("AddComplete", "정상적으로 입주민이 추가 되었습니다.");
            return "redirect:/admin/residents";
        }
    }

    @GetMapping("/detail")
    public String residentDetail(@RequestParam(value = "number", required = false) String number, Model model) {
        if (number == null || number.isEmpty()) {
            return "admin/resident_detail";
        }

        User resident = residentService.findByNumber(number);
        if (resident == null) {
            return "redirect:/admin/residents";
        }
        model.addAttribute("resident", resident);
        return "admin/resident_detail";
    }

    @GetMapping("/update")
    public String updateResidentForm(@RequestParam(value = "number", required = false) String number, Model model) {
        if (number == null || number.isEmpty()) {
            return "redirect:/admin/residents";
        }

        User resident = residentService.findByNumber(number);
        if (resident == null) {
            return "redirect:/admin/residents";
        }

        model.addAttribute("resident", resident);
        return "admin/resident_update";
    }

    @PostMapping("/update")
    public String updateResident(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        User existingResident = residentService.findByNumber(user.getNumber());

        if (existingResident == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 입주민이 존재하지 않습니다.");
            return "redirect:/admin/residents";
        }

        existingResident.setName(user.getName());
        existingResident.setAddress(user.getAddress());

        residentService.updateResident(existingResident);

        redirectAttributes.addFlashAttribute("successMessage", "입주민 정보가 수정되었습니다.");
        return "redirect:/admin/residents";
    }

    @GetMapping("/delete")
    public String deleteResident(@RequestParam("number") String number, RedirectAttributes redirectAttributes) {
        residentService.deleteResident(number);
        redirectAttributes.addFlashAttribute("message", "입주민 정보가 삭제되었습니다.");
        return "redirect:/admin/residents";
    }


}
