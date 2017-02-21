package no.bouvet.sandvika.activityboard.strava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationResponseType;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ClubAPI;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.points.CalculatePoints;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;

@Component
public class StravaSlurper
{
    private static final AuthorisationResponseType DEFAULT_RESPONSE_TYPE = AuthorisationResponseType.CODE;
    private static final String DEFAULT_REDIRECT_URI = "http://localhost/redirects";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final int STRAVA_CLUB_ID = 259508;
    public static String USERNAME = "sondrewe@gmail.com";
    public static String PASSWORD = "passordForSandvika";
    public static Integer STRAVA_APPLICATION_ID = 3034;
    public static String STRAVA_CLIENT_SECRET = "506d1d0ed30af56b74a458a26419dd6ead8e910d";
    private static Logger log = LoggerFactory.getLogger(StravaSlurper.class);
    @Autowired
    ActivityRepository repository;

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void updateActivities() {
        log.info("Updating activities");
        ClubAPI api = getApi();
        List<Activity> activities = new ArrayList<>();
        Arrays.asList(api.listRecentClubActivities(STRAVA_CLUB_ID, null, null)).forEach(stravaActivity -> activities.add(createActivity(stravaActivity)));
        repository.save(activities);
    }

    private Activity createActivity(StravaActivity stravaActivity) {
        Activity activity = new Activity();
        activity.setAthletefirstName(stravaActivity.getAthlete().getFirstname());
        activity.setAthleteLastName(stravaActivity.getAthlete().getLastname());
        activity.setType(stravaActivity.getType().getValue());
        activity.setId(stravaActivity.getId());
        activity.setName(stravaActivity.getName());
//        activity.setCalories(stravaActivity.getCalories());
        activity.setMovingTimeInSeconds(stravaActivity.getMovingTime());
        activity.setDistanceInMeters(stravaActivity.getDistance());
        activity.setStartDateLocal(stravaActivity.getStartDateLocal());
        activity.setPoints(CalculatePoints.getPointsForActivity(activity));
        return activity;
    }

    private ClubAPI getApi() {
        return API.instance(ClubAPI.class, getValidToken());
    }


    private Token getValidToken(final AuthorisationScope... scopes) {
        Token token = TokenManager.instance().retrieveTokenWithExactScope(USERNAME, scopes);
        if (token == null) {
            try {
                token = StravaUtils.getStravaAccessToken(USERNAME, PASSWORD, scopes);
                TokenManager.instance().storeToken(token);
            } catch (BadRequestException | UnauthorizedException e) {
                return null;
            }
        }
        return token;
    }
}



