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

    public List<Badge> getBadgesForActivity(Activity activity)
    {
        List<Badge> allBadges = badgeRepository.findBadgeByActivityTypeIn(Arrays.asList(activity.getType(), "all"));

        List<Badge> awardedBadges = new ArrayList<>();

        for (Badge badge : allBadges)
        {
            if (badge.getType().equalsIgnoreCase("length") && activity.getDistanceInMeters() > badge.getDistanceCriteria())
            {
                awardedBadges.add(badge);
                badge.getActivities().add(activity);
                badgeRepository.save(badge);
                Athlete athlete = athleteRepository.findById(activity.getAthleteId());
                athlete.addBadge(badge);
                athleteRepository.save(athlete);
            }
        }
        return awardedBadges;
    }
}
