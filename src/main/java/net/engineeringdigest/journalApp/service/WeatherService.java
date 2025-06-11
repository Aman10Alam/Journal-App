package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String apiKey = "416566652e8746e98fb154822251106";

    private static final String API = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalApi=API.replace("CITY",city).replace("API_KEY",apiKey);
        ResponseEntity<WeatherResponse> weatherCondition= restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);// deserialization of json to POJO
        return weatherCondition.getBody();
    }
}
