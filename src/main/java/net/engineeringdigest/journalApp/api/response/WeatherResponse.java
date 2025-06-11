package net.engineeringdigest.journalApp.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherResponse {
    private Current current;

    @Data
    @NoArgsConstructor
    public class Current {
        private String last_updated;
        private double temp_c;
        private int is_day;
        private int humidity;
        private int cloud;
        private double feelslike_c;
        private double windchill_c;
        private double uv;
    }

}
