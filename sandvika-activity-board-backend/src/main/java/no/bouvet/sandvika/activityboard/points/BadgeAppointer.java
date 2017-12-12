package no.bouvet.sandvika.activityboard.points;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.Badge;
import no.bouvet.sandvika.activityboard.repository.BadgeRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;

@Component
public class BadgeAppointer
{
    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    AthleteRepository athleteRepository;

    public List<String> getBadgesForActivity(Activity activity)
    {
        List<Badge> allBadges = badgeRepository.findBadgeByActivityTypeIn(Arrays.asList(activity.getType(), "all"));

        List<String> awardedBadges = new ArrayList<>();

        for (Badge badge : allBadges)
        {
            if (eligibleForDistanceBadge(activity, badge) || eligibleForClimbBadge(activity, badge))
            {
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

    private boolean eligibleForClimbBadge(Activity activity, Badge badge)
    {
        return badge.getType().equalsIgnoreCase("climb") && activity.getTotalElevationGaininMeters() > badge.getDistanceCriteria();
    }

    private boolean eligibleForDistanceBadge(Activity activity, Badge badge)
    {
        return badge.getType().equalsIgnoreCase("distance") && activity.getDistanceInMeters() > badge.getDistanceCriteria();
    }
}
