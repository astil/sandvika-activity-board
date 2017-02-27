package no.bouvet.sandvika.activityboard.points;

import org.springframework.stereotype.Component;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.ActivityType;
import no.bouvet.sandvika.activityboard.utils.Utils;

/**
 * This is a static class used for points calculation.
 */
@Component
public final class PointsCalculator
{
    static final int KILOMETER_VALUE = 3;
    static final int MINUTE_VALUE = 1;
    static final int SECONDS_IN_MINUTE = 60;

    private PointsCalculator()
    {
    }

    public static double getPointsForActivity(Activity activity, double handicap)
    {
        ActivityType activityType = ActivityType.toActivityType(activity.getType());
        if (activityType == null)
        {
            return 0;
        } else
        {
            int achievementPoints = activity.getAchievementCount();
            int durationPoints = getPointsForDuration(activity.getMovingTimeInSeconds() / SECONDS_IN_MINUTE);
            int distancePoints = getPointsForDistance(activity.getDistanceInMeters() / 1000, activityType);
            return Utils.scaledDouble((durationPoints + distancePoints + achievementPoints) * handicap);
        }
    }

    private static int getPointsForDistance(double distanceInKilometers, ActivityType activityType)
    {
        return Double.valueOf(distanceInKilometers * KILOMETER_VALUE * activityType.coefficient()).intValue();
    }

    private static int getPointsForDuration(int durationInMinutes)
    {
        return durationInMinutes * MINUTE_VALUE;
    }

}
