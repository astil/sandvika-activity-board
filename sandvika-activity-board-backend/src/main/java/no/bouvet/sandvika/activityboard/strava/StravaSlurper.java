package no.bouvet.sandvika.activityboard.strava;

import no.bouvet.sandvika.activityboard.domain.*;
import no.bouvet.sandvika.activityboard.points.BadgeAppointer;
import no.bouvet.sandvika.activityboard.points.HandicapCalculator;
import no.bouvet.sandvika.activityboard.points.PointsCalculator;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import no.bouvet.sandvika.activityboard.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StravaSlurper {
    private static final String BASE_PATH = "https://www.strava.com/api/v3/";
    public static String STRAVA_CLIENT_TOKEN = "43cef4065b62813502a456d39508702f3d74ad61";
    private static Logger log = LoggerFactory.getLogger(StravaSlurper.class);


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    HandicapCalculator handicapCalculator;

    @Autowired
    BadgeAppointer badgeAppointer;


    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void updateLatestActivities() {
        updateActivities(1, DateUtil.getEpochDaysAgo(10));
    }

    public UpdateSummary updateActivities(int pages, long after) {
        log.info("Updating activities");
        UpdateSummary updateSummary = new UpdateSummary();
        for (Athlete athlete : athleteRepository.findAllByTokenIsNotNull()) {
            List<StravaActivity> stravaActivities = new ArrayList<>();

            IntStream.rangeClosed(1, pages).forEach(i -> stravaActivities.addAll(getActivitiesFromStrava(athlete, i, after)));
            List<Activity> activities = new ArrayList<>();
            stravaActivities.forEach(stravaActivity ->
                    activities.add(createActivity(stravaActivity, athlete)));
            activityRepository.save(activities
                    .stream()
                    .filter(activity -> activity.getPoints() > 0)
                    .collect(Collectors.toList()));
            updateSummary.addNumberOfActivities(athlete.getLastName(), activities.size());
        }
        return updateSummary;
    }


    private Activity createActivity(StravaActivity stravaActivity, Athlete athlete) {
        Activity activity = new Activity();
        activity.setAthleteFirstName(athlete.getFirstName());
        activity.setAthleteLastName(athlete.getLastName());
        activity.setAthleteId(athlete.getId());
        activity.setType(stravaActivity.getType());
        activity.setId(stravaActivity.getId());
        activity.setName(stravaActivity.getName());
        if (stravaActivity.getKilojoules() != null) {
            activity.setKiloJoules(stravaActivity.getKilojoules());
        }
        if (stravaActivity.getSufferScore() != null) {
            activity.setSufferScore(stravaActivity.getSufferScore());
        }
        activity.setElapsedTimeInSeconds(stravaActivity.getElapsedTime());
        if (stravaActivity.getAchievementCount() != null) {
            activity.setAchievementCount(stravaActivity.getAchievementCount());
        }
        if (stravaActivity.getTotalElevationGain() != null) {
            activity.setTotalElevationGaininMeters(stravaActivity.getTotalElevationGain());
        }
        activity.setMovingTimeInSeconds(stravaActivity.getMovingTime());
        activity.setDistanceInMeters(stravaActivity.getDistance());
        activity.setStartDateLocal(DateUtil.getDateFromLocalDateTimeString(stravaActivity.getStartDateLocal()));
        activity.setHandicap(handicapCalculator.getHandicapForActivity(activity));
        activity.setBadges(badgeAppointer.getBadgesForActivity(activity));
        activity.setPoints(PointsCalculator.getPointsForActivity(activity, handicapCalculator.getHandicapForActivity(activity)));
        activity.setPhotos(getPhotosFromActivity(activity, athlete.getToken()));
        log.debug("Created activity: " + activity.toString());
        return activity;
    }

    private List<Photo> getPhotosFromActivity(Activity activity, String athleteToken) {
        StravaActivityFull stravaActivityFull = getActivityFromStrava(activity.getId(), athleteToken);
        return Arrays.asList(createPhoto(activity, stravaActivityFull));
    }

    private Photo createPhoto(Activity activity, StravaActivityFull stravaActivityFull) {
        return new Photo(activity.getSummary(), stravaActivityFull.getStravaPhotos().getStravaPrimaryPhoto().getStravaUrls().get600());

    }

    private StravaActivityFull getActivityFromStrava(int activityId, String token) {
        String url = BASE_PATH
                + "activities/" + activityId + "?access_token=" + token;
        log.info(url);
        return restTemplate.getForObject(url, StravaActivityFull.class);
    }

    private List<StravaActivity> getActivitiesFromStrava(Athlete athlete, int page, long after) {
        String url = BASE_PATH
                + "athlete/activities?" + (after == 0 ? "" : "after=" + after + "&") + "page=" + page + "&per_page=200&access_token=" + athlete.getToken();
        log.info(url);
        StravaActivity[] activitiesFromStrava = restTemplate.getForObject(url, StravaActivity[].class);
        return Arrays.asList(activitiesFromStrava);
    }


}



