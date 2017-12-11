package no.bouvet.sandvika.activityboard.strava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ClubAPI;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.points.HandicapCalculator;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;

@Component
public class StravaSlurper
{
    private static final int STRAVA_CLUB_ID = 259508;
    public static String USERNAME = "sondrewe@gmail.com";
    public static String PASSWORD = "passordForSandvika";
    public static Integer STRAVA_APPLICATION_ID = 3034;
    public static String STRAVA_CLIENT_SECRET = "506d1d0ed30af56b74a458a26419dd6ead8e910d";
    private static Logger log = LoggerFactory.getLogger(StravaSlurper.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    HandicapCalculator handicapCalculator;

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void updateActivities()
    {
        log.info("Updating activities");
        List<Activity> activities = Arrays.asList(getStravaActivities());
        addMissingAthletes(activities);

        activityRepository.save(activities
            .stream()
            .filter(activity -> activity.getPoints() > 0)
            .collect(Collectors.toList()));
    }

    private void addMissingAthletes(List<Activity> activities)
    {
        activities
            .stream()
            .map(Activity::getAthlete)
            .filter(a -> !athleteRepository.exists(a.getId()))
            .forEach(this::saveAthlete);
    }

    public void saveAthlete(Athlete stravaAthlete)
    {
        Athlete athlete = new Athlete();
        athlete.setLastName(stravaAthlete.getLastname());
        athlete.setFirstName(stravaAthlete.getFirstname());
        athlete.setProfile(stravaAthlete.getProfile());
        athlete.setId(stravaAthlete.getId());
        athleteRepository.save(athlete);
    }

    private ClubAPI getApi()
    {
        return API.instance(ClubAPI.class, getValidToken());
    }

    private Token getValidToken(final AuthorisationScope... scopes)
    {
        Token token = TokenManager.instance().retrieveTokenWithExactScope(USERNAME, scopes);
        if (token == null)
        {
            try
            {
                token = StravaUtils.getStravaAccessToken(USERNAME, PASSWORD, scopes);
                TokenManager.instance().storeToken(token);
            } catch (BadRequestException | UnauthorizedException e)
            {
                return null;
            }
        }
        return token;
    }


    private Activity[] getStravaActivities()
    {
        return restTemplate.getForObject("https://www.strava.com/api/v3/clubs/259508/activities?page=1&per_page=2&access_token=43cef4065b62813502a456d39508702f3d74ad61", Activity[].class);
    }
}



