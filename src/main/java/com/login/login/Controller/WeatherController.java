package com.login.login.Controller;

import com.login.login.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String getWeather(Model model) {
        JsonNode weatherData = weatherService.getCurrentWeather();

        double temperature = weatherData.path("main").path("temp").asDouble();
        int humidity = weatherData.path("main").path("humidity").asInt();

        model.addAttribute("temperature", temperature);
        model.addAttribute("humidity", humidity);

        return "index";
    }
}
