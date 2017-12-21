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

    public Set<String> getBadgesForActivity(Activity activity) {
        log.info("Checking activity " + activity.getName() + " for badges.");
        Set<Badge> allBadges = badgeRepository.findBadgeByActivityTypeIn(Arrays.asList(activity.getType(), "all"));
        Set<String> awardedBadges = new HashSet<>();

        for (Badge badge : allBadges) {
            if (eligibleForDistanceBadge(activity, badge) || eligibleForClimbBadge(activity, badge) || eligibleForTimeBadge(activity, badge)) {
                log.info("Appointing badge " + badge.getName() + " to " + activity.getName());
                appointBadge(activity, awardedBadges, badge);
            }
        }
        return awardedBadges;
    }

    private void appointBadge(Activity activity, Set<String> awardedBadges, Badge badge) {
        awardedBadges.add(badge.getName());
        badge.getActivities().add(activity);
        badgeRepository.save(badge);
        Athlete athlete = athleteRepository.findById(activity.getAthleteId());
        athlete.addBadge(badge, activity);
        athleteRepository.save(athlete);
    }

    private boolean eligibleForTimeBadge(Activity activity, Badge badge) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (badgeTypeIsTime(badge)) {
            LocalTime timeCriteria = LocalTime.parse(badge.getTimeCriteria(), formatter);

            if (badge.getBeforeOrAfter().equalsIgnoreCase("before") && timeCriteria.isAfter(getStartDateAsLocalDateTime(activity))) {
                return true;
            } else if (badge.getBeforeOrAfter().equalsIgnoreCase("after") && timeCriteria.isBefore(getStartDateAsLocalDateTime(activity))) {
                return true;
            }
        }
        return false;
    }

    private LocalTime getStartDateAsLocalDateTime(Activity activity) {
        return LocalDateTime.ofInstant(activity.getStartDateLocal().toInstant(), ZoneId.systemDefault()).toLocalTime();
    }

    private boolean eligibleForClimbBadge(Activity activity, Badge badge) {
        return badgeTypeIsClimb(badge) && activity.getTotalElevationGaininMeters() > badge.getDistanceCriteria();
    }

    private boolean eligibleForDistanceBadge(Activity activity, Badge badge) {
        return badgeTypeIsDistance(badge) && activity.getDistanceInMeters() > badge.getDistanceCriteria();
    }

    private boolean badgeTypeIsTime(Badge badge) {
        return badge.getType().equalsIgnoreCase("time");
    }

    private boolean badgeTypeIsClimb(Badge badge) {
        return badge.getType().equalsIgnoreCase("climb");
    }

    private boolean badgeTypeIsDistance(Badge badge) {
        return badge.getType().equalsIgnoreCase("distance");
    }
}
