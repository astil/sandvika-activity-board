package no.bouvet.sandvika.activityboard.utils;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherUtil {
    @Autowired
    RestTemplate restTemplate;

    private static String BASE_URL = "https://api.darksky.net/forecast/47e94f7f05778e2791d856944c298954/";
    private static String OPTIONS = "?units=si&exclude=hourly";


    public Weather getWeatherForActivity(Activity activity) {
        double[] startLatLong = activity.getStartLatLng();
        String url = BASE_URL + startLatLong[0] + "," + startLatLong[1] + "," + activity.getStartDateLocal();
        return restTemplate.getForObject(url, Weather.class);

    }



}
