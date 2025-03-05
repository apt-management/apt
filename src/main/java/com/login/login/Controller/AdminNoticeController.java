package com.login.login.Controller;

import com.login.login.Service.NoticeService;
import com.login.login.Model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public String noticeList(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        int totalNotices = noticeService.getTotalCount();
        int totalPages = totalNotices > 0 ? (int) Math.ceil((double) totalNotices / pageSize) : 1;

        if (page < 1) {
            return "redirect:/admin/notice?page=1";
        } else if (page > totalPages) {
            return "redirect:/admin/notice?page=" + totalPages;
        }

        List<Notice> notices = noticeService.getNoticesByPage(page, pageSize);
        model.addAttribute("notices", notices);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalNotices", totalNotices);

        int startPage = ((page - 1) / pageSize) * pageSize + 1;
        int endPage = Math.min(startPage + pageSize - 1, totalPages);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/admin/notice";
    }

    @GetMapping("/detail/{id}")
    public String getNoticeDetail(@PathVariable("id") int id, Model model) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            model.addAttribute("errorMessage", "해당 공지사항을 찾을 수 없습니다.");
            return "redirect:/admin/notice";
        }
        model.addAttribute("notice", notice);
        return "admin/notice_detail";
    }

    @GetMapping("/add")
    public String addNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "admin/notice_add";
    }

    @PostMapping("/add")
    public String addNotice(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam(value = "attach", required = false) MultipartFile attach,
                            Model model) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);

        if (attach != null && !attach.isEmpty()) {
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            String filePath = uploadDir + "/" + attach.getOriginalFilename();
            File destinationFile = new File(filePath);

            if (!destinationFile.exists()) {
                try {
                    attach.transferTo(destinationFile);
                    notice.setAttach("/uploads/" + attach.getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("errorMessage", "파일 업로드 실패");
                    return "admin/notice_add";
                }
            } else {
                model.addAttribute("errorMessage", "파일이 이미 존재합니다.");
                return "admin/notice_add";
            }
        }

        noticeService.addNotice(notice);
        return "redirect:/admin/notice";
    }

    @GetMapping("/edit/{id}")
    public String editNoticeForm(@PathVariable("id") int id, Model model) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            model.addAttribute("errorMessage", "해당 공지사항을 찾을 수 없습니다.");
            return "redirect:/admin/notice";
        }
        model.addAttribute("notice", notice);
        return "admin/notice_edit";
    }

    @PostMapping("/edit/{id}")
    public String editNotice(@PathVariable("id") int id,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam(value = "attach", required = false) MultipartFile attach,
                             Model model) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            model.addAttribute("errorMessage", "해당 공지사항을 찾을 수 없습니다.");
            return "redirect:/admin/notice";
        }

        notice.setTitle(title);
        notice.setContent(content);

        if (attach != null && !attach.isEmpty()) {
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            String filePath = uploadDir + "/" + attach.getOriginalFilename();
            File destinationFile = new File(filePath);

            if (!destinationFile.exists()) {
                try {
                    attach.transferTo(destinationFile);
                    notice.setAttach("/uploads/" + attach.getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("errorMessage", "파일 업로드 실패");
                    return "admin/notice_edit";
                }
            } else {
                model.addAttribute("errorMessage", "파일이 이미 존재합니다.");
                return "admin/notice_edit";
            }
        }

        noticeService.updateNotice(notice);
        return "redirect:/admin/notice";
    }

    @GetMapping("/delete/{id}")
    public String deleteNotice(@PathVariable("id") int id) {
        noticeService.deleteNotice(id);
        return "redirect:/admin/notice";
    }
}
