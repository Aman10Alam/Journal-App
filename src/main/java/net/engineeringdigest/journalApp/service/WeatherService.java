package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api-key}")
    private String apiKey ;// to use @Value do not make them static as spring will not interfere with static variables

//    @Value("${weather.url}")
    private String API ;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        API=appCache.appCacheMap.get(AppCache.keys.weather_api.toString());
        String finalApi= API.replace("<city>",city).replace("<apiKey>",apiKey);
        ResponseEntity<WeatherResponse> weatherCondition= restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);// deserialization of json to POJO
        return weatherCondition.getBody();
    }
}
