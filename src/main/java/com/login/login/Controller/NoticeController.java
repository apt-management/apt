package com.login.login.Controller;


import com.login.login.Model.Notice;
import com.login.login.Service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public String noticeList(@RequestParam(defaultValue = "1") int page, HttpServletRequest request, Model model) {
        int pageSize = 5;
        int totalNotices = noticeService.getTotalCount();
        int totalPages = totalNotices > 0 ? (int) Math.ceil((double) totalNotices / pageSize) : 1;

        if (page < 1) {
            return "redirect:/notice?page=1";
        } else if (page > totalPages) {
            return "redirect:/notice?page=" + totalPages;
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
        model.addAttribute("requestURI", request.getRequestURI());

        return "notice";
    }

    @GetMapping("/add")
    public String addNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "notice_add";
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
                    return "notice_add";
                }
            } else {
                model.addAttribute("errorMessage", "파일이 이미 존재합니다.");
                return "notice_add";
            }
        }

        noticeService.addNotice(notice);
        return "redirect:/notice";
    }

    @GetMapping("/view/{id}")
    public String viewNotice(@PathVariable("id") int id, Model model) {
        try {
            noticeService.incrementViewer(id);
            Notice notice = noticeService.getNoticeById(id);
            Integer prevId = noticeService.getPrevNoticeId(id);
            Integer nextId = noticeService.getNextNoticeId(id);

            model.addAttribute("notice", notice);
            model.addAttribute("prevId", prevId);
            model.addAttribute("nextId", nextId);
            return "공지사항 상세페이지";
        } catch (RuntimeException e) {
            int totalNotices = noticeService.getTotalCount();
            int pageSize = 5;
            int totalPages = totalNotices > 0 ? (int) Math.ceil((double) totalNotices / pageSize) : 1;
            return "redirect:/notice?page=" + totalPages;
        }
    }
}
