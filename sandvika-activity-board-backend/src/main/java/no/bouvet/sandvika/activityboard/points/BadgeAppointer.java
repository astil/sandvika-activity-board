package no.bouvet.sandvika.activityboard.points;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.Badge;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.repository.BadgeRepository;

@Component
public class BadgeAppointer {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(BadgeAppointer.class);
    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    AthleteRepository athleteRepository;

    public Set<Badge> getBadgesForActivity(Activity activity) {
        Set<Badge> allBadges = badgeRepository.findBadgeByActivityTypeIn(Arrays.asList(activity.getType(), "all"));
        Set<Badge> awardedBadges = new HashSet<>();

        for (Badge badge : allBadges) {
            if (eligibleForDistanceBadge(activity, badge) || eligibleForClimbBadge(activity, badge) || eligibleForTimeBadge(activity, badge) || eligibleForWeatherBadge(activity, badge)) {
                log.info("Appointing badge " + badge.getName() + " to " + activity.getName());
                appointBadge(activity, awardedBadges, badge);
            }
        }
        return awardedBadges;
    }

    private void appointBadge(Activity activity, Set<Badge> awardedBadges, Badge badge) {
        awardedBadges.add(badge);
       // badge.getActivities().add(activity);
        log.info("Saving Badge " + badge.toString());
        badgeRepository.save(badge);
        Athlete athlete = athleteRepository.findById(activity.getAthleteId());
        athlete.addBadge(badge, activity);
        log.info("Saving Athlete " + athlete.toString());
        athleteRepository.save(athlete);
    }

    private boolean eligibleForTimeBadge(Activity activity, Badge badge) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (badgeTypeIsTime(badge)) {
            LocalTime timeCriteria = LocalTime.parse(badge.getTimeCriteria(), formatter);

            if (badge.getLessOrMore().equalsIgnoreCase("less") && timeCriteria.isAfter(getStartDateAsLocalDateTime(activity))) {
                return true;
            } else if (badge.getLessOrMore().equalsIgnoreCase("more") && timeCriteria.isBefore(getStartDateAsLocalDateTime(activity))) {
                return true;
            }
        }
        return false;
    }

    private boolean eligibleForWeatherBadge(Activity activity, Badge badge) {
        if (badgeTypeIsTempBadge(badge)) {
            if (badge.getLessOrMore().equalsIgnoreCase("less")) {
                return activity.getWeather().getCurrently().getTemperature() < badge.getValueCriteria();
            } else {
                return activity.getWeather().getCurrently().getTemperature() > badge.getValueCriteria();
            }
        } else if (badgeTypeIsPrecipitationBadge(badge) && activity.getWeather().getCurrently().getPrecipIntensity() > 1 ) {
            return true;
        }
        return false;
    }

    private LocalTime getStartDateAsLocalDateTime(Activity activity) {
        return LocalDateTime.ofInstant(activity.getStartDateLocal().toInstant(), ZoneId.systemDefault()).toLocalTime();
    }

    private boolean eligibleForClimbBadge(Activity activity, Badge badge) {
        return badgeTypeIsClimb(badge) && activity.getTotalElevationGaininMeters() >= badge.getValueCriteria();
    }

    private boolean eligibleForDistanceBadge(Activity activity, Badge badge) {
        return badgeTypeIsDistance(badge) && activity.getDistanceInMeters() >= badge.getValueCriteria();
    }

    private boolean badgeTypeIsTime(Badge badge) {
        return badge.getType().equalsIgnoreCase("time");
    }

    private boolean badgeTypeIsClimb(Badge badge) {
        return badge.getType().equalsIgnoreCase("climb");
    }

    private boolean badgeTypeIsTempBadge(Badge badge) {
        return badge.getType().equalsIgnoreCase("temp");
    }

    private boolean badgeTypeIsPrecipitationBadge(Badge badge) {
        return badge.getType().equalsIgnoreCase("precipitation");
    }

    private boolean badgeTypeIsDistance(Badge badge) {
        return badge.getType().equalsIgnoreCase("distance");
    }
}
