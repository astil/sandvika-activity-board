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

@Component
public class StravaSlurper {
    private static final String BASE_PATH = "https://www.strava.com/api/v3/clubs/";
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
        for (Club club : clubRepository.findAll()) {
            updateClubMembers(club.getId());
            List<StravaActivity> stravaActivities = getStravaActivities(club.getId().toString(), pages);
            addMissingAthletes(stravaActivities);

            List<Activity> activities = new ArrayList<>();
            stravaActivities.forEach(stravaActivity ->
                    activities.add(createActivity(stravaActivity)));

            activityRepository.save(activities
                    .stream()
                    .filter(activity -> activity.getPoints() > 0)
                    .collect(Collectors.toList()));
        }
    }

    private void updateClubMembers(int clubId) {
        List<StravaAthlete> stravaAthletes = getClubMembersFromStrava(clubId);
        Club club = clubRepository.findById(clubId);
        club.setMemberIds(stravaAthletes.stream().map(StravaAthlete::getId).collect(Collectors.toList()));
        clubRepository.save(club);
    }

    public void createClub(Club club) {
        StravaClub stavaClub = getClubFromStrava(club.getId());
        club.setName(stavaClub.getName());
        clubRepository.save(club);
        updateClubMembers(club.getId());
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

    private List<StravaAthlete> getClubMembersFromStrava(Integer clubId) {
        StravaAthlete[] clubMemberFromStrava = restTemplate.getForObject(BASE_PATH + clubId
                + "/members?access_token=" + STRAVA_CLIENT_TOKEN, StravaAthlete[].class);
        return Arrays.asList(clubMemberFromStrava);
    }

    private StravaClub getClubFromStrava(int clubId) {
        StravaClub clubFromStrava = restTemplate.getForObject(BASE_PATH + clubId
                + "?access_token=" + STRAVA_CLIENT_TOKEN, StravaClub.class);
        return clubFromStrava;
    }

}



