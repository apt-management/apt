package com.login.login.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    public JsonNode getCurrentWeather() {
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("api.openweathermap.org")
                .path("/data/2.5/weather")
                .queryParam("q", "Daejeon,kr")
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, JsonNode.class);
    }
}

