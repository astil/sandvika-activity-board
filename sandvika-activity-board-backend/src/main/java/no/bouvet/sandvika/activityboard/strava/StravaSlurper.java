package no.bouvet.sandvika.activityboard.strava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.StravaActivity;
import no.bouvet.sandvika.activityboard.domain.StravaAthlete;
import no.bouvet.sandvika.activityboard.points.BadgeAppointer;
import no.bouvet.sandvika.activityboard.points.HandicapCalculator;
import no.bouvet.sandvika.activityboard.points.PointsCalculator;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.utils.DateUtil;

@Component
public class StravaSlurper {
    private static final String BASE_PATH = "https://www.strava.com/api/v3/clubs/";
    private static final String STRAVA_CLUB_ID = "259508";
    public static String STRAVA_CLIENT_TOKEN = "43cef4065b62813502a456d39508702f3d74ad61";
    private static Logger log = LoggerFactory.getLogger(StravaSlurper.class);


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    HandicapCalculator handicapCalculator;

    @Autowired
    BadgeAppointer badgeAppointer;

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void updateLatestActivities()
    {
        updateActivities(1);
    }

    public void updateActivities(int pages) {
        log.info("Updating activities");
        List<StravaActivity> stravaActivities = getStravaActivities(STRAVA_CLUB_ID, pages);
        addMissingAthletes(stravaActivities);

        List<Activity> activities = new ArrayList<>();
        stravaActivities.forEach(stravaActivity ->
                activities.add(createActivity(stravaActivity)));

        activityRepository.save(activities
                .stream()
                .filter(activity -> activity.getPoints() > 0)
                .collect(Collectors.toList()));
    }

    private void addMissingAthletes(List<StravaActivity> activities) {
        activities
                .stream()
                .map(StravaActivity::getAthlete)
                .filter(a -> !athleteRepository.exists(a.getId()))
                .forEach(this::saveAthlete);
    }

    public void saveAthlete(StravaAthlete stravaAthlete) {
        Athlete athlete = new Athlete();
        athlete.setLastName(stravaAthlete.getLastname());
        athlete.setFirstName(stravaAthlete.getFirstname());
        athlete.setProfile(stravaAthlete.getProfile());
        athlete.setId(stravaAthlete.getId());

        athleteRepository.save(athlete);
    }

    private Activity createActivity(StravaActivity stravaActivity) {
        Activity activity = new Activity();
        activity.setAthletefirstName(stravaActivity.getAthlete().getFirstname());
        activity.setAthleteLastName(stravaActivity.getAthlete().getLastname());
        activity.setAthleteId(stravaActivity.getAthlete().getId());
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
        log.debug("Created activity: " + activity.toString());
        return activity;
    }

    private List<StravaActivity> getStravaActivities(String clubId, int pages) {
        ArrayList<StravaActivity> activities = new ArrayList<>();
        for (int i = 1; i <= pages; i++) {
            log.info("Getting activities from Strava. Page " + i);
            activities.addAll((getActivitiesFromStrava(clubId, i)));
        }
        log.info("Got " + activities.size() + " activities from Strava");
        return activities;
    }

    private List<StravaActivity> getActivitiesFromStrava(String clubId, int page) {
        StravaActivity[] activitiesFromStrava = restTemplate.getForObject(BASE_PATH + clubId
                + "/activities?page=" + page + "&per_page=200&access_token=" + STRAVA_CLIENT_TOKEN, StravaActivity[].class);
        return Arrays.asList(activitiesFromStrava);
    }
}



