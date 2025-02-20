package com.login.login.Controller;

import com.login.login.Model.Notice;
import com.login.login.Service.NoticeService;
import com.login.login.Service.WeatherService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    private final NoticeService noticeService;

    public WeatherController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/")
    public String getWeather(Model model, Authentication authentication) {

         model.addAttribute("isAuthenticated", authentication != null);

        List<Notice> recentNotices = noticeService.getRecentNotices(5);
        model.addAttribute("recentNotices", recentNotices);

        JsonNode weatherData = weatherService.getCurrentWeather();

        double temperature = weatherData.path("main").path("temp").asDouble();
        int humidity = weatherData.path("main").path("humidity").asInt();

        model.addAttribute("temperature", temperature);
        model.addAttribute("humidity", humidity);

        return "index";
    }
}
