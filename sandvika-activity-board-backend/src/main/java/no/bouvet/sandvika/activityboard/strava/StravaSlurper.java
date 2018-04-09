package no.bouvet.sandvika.activityboard.strava;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.StravaActivity;
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

@Component
public class StravaSlurper {
    private static final String BASE_PATH = "https://www.strava.com/api/v3/athlete/";
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
        updateActivities(1);
    }

    public void updateActivities(int pages) {
        log.info("Updating activities");
        for (Athlete athlete : athleteRepository.findAllByTokenIsNotNull()) {
            List<StravaActivity> stravaActivities = getActivitiesFromStrava(athlete, pages);
            List<Activity> activities = new ArrayList<>();
            stravaActivities.forEach(stravaActivity ->
                    activities.add(createActivity(stravaActivity, athlete)));
            activityRepository.save(activities
                    .stream()
                    .filter(activity -> activity.getPoints() > 0)
                    .collect(Collectors.toList()));
        }

//        for (Club club : clubRepository.findAll()) {
//            updateClubMembers(club.getId());
//            List<StravaActivity> stravaActivities = getStravaActivities(club.getId().toString(), pages);
//            addMissingAthletes(stravaActivities);
//
//            List<Activity> activities = new ArrayList<>();
//            stravaActivities.forEach(stravaActivity ->
//                    activities.add(createActivity(stravaActivity)));
//
//            activityRepository.save(activities
//                    .stream()
//                    .filter(activity -> activity.getPoints() > 0)
//                    .collect(Collectors.toList()));
//        }
    }


    private Activity createActivity(StravaActivity stravaActivity, Athlete athlete) {
        Activity activity = new Activity();
        activity.setAthletefirstName(athlete.getFirstName());
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
        log.debug("Created activity: " + activity.toString());
        return activity;
    }

    private List<StravaActivity> getActivitiesFromStrava(Athlete athlete, int page) {
        String url = BASE_PATH
                + "/activities?page=" + page + "&per_page=200&access_token=" + athlete.getToken();
        log.info(url);
        StravaActivity[] activitiesFromStrava = restTemplate.getForObject(url, StravaActivity[].class);
        return Arrays.asList(activitiesFromStrava);
    }


}



