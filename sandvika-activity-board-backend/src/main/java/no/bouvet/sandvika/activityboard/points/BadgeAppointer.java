package no.bouvet.sandvika.activityboard.points;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.Badge;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.repository.BadgeRepository;

@Component
public class BadgeAppointer
{
    private static Logger log = org.slf4j.LoggerFactory.getLogger(BadgeAppointer.class);
    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    AthleteRepository athleteRepository;

    public List<String> getBadgesForActivity(Activity activity)
    {
        log.info("Checking activity " + activity.getName() + " for badges.");
        List<Badge> allBadges = badgeRepository.findBadgeByActivityTypeIn(Arrays.asList(activity.getType(), "all"));
        List<String> awardedBadges = new ArrayList<>();

        for (Badge badge : allBadges)
        {
            if (eligibleForDistanceBadge(activity, badge) || eligibleForClimbBadge(activity, badge) || eligibleForTimeBadge(activity, badge))
            {
                log.info("Appointing badge " + badge.getName() + " to " + activity.getName());
                appointBadge(activity, awardedBadges, badge);
            }
        }
        return awardedBadges;
    }

    private void appointBadge(Activity activity, List<String> awardedBadges, Badge badge)
    {
        awardedBadges.add(badge.getName());
        badge.getActivities().add(activity);
        badgeRepository.save(badge);
        Athlete athlete = athleteRepository.findById(activity.getAthleteId());
        athlete.addBadge(badge, activity);
        athleteRepository.save(athlete);
    }

    private boolean eligibleForTimeBadge(Activity activity, Badge badge)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (badge.getType().equalsIgnoreCase("time"))
        {
            LocalTime timeCriteria = LocalTime.parse(badge.getTimeCriteria(), formatter);

            if (badge.getBeforeOrAfter().equalsIgnoreCase("before") && timeCriteria.isAfter(LocalDateTime.ofInstant(activity.getStartDateLocal().toInstant(), ZoneId.systemDefault()).toLocalTime()))
            {
                return true;
            } else if (badge.getBeforeOrAfter().equalsIgnoreCase("after") && timeCriteria.isBefore(LocalDateTime.ofInstant(activity.getStartDateLocal().toInstant(), ZoneId.systemDefault()).toLocalTime()))
            {
                return true;
            }
        }
        return false;
    }

    private boolean eligibleForClimbBadge(Activity activity, Badge badge)
    {
        return badge.getType().equalsIgnoreCase("climb") && activity.getTotalElevationGaininMeters() > badge.getDistanceCriteria();
    }

    private boolean eligibleForDistanceBadge(Activity activity, Badge badge)
    {
        return badge.getType().equalsIgnoreCase("distance") && activity.getDistanceInMeters() > badge.getDistanceCriteria();
    }
}
