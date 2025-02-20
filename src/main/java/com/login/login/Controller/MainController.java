package com.login.login.Controller;

import com.login.login.Model.Notice;
import com.login.login.Service.NoticeService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    private final NoticeService noticeService;

    public MainController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @RequestMapping("/")
    public String mainPage(Model model, Authentication authentication) {
        model.addAttribute("isAuthenticated", authentication != null);

        List<Notice> recentNotices = noticeService.getRecentNotices(5);
        model.addAttribute("recentNotices", recentNotices);

        return "index";
    }
}
