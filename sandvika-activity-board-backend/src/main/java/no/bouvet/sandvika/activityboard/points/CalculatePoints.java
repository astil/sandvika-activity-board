package no.bouvet.sandvika.activityboard.points;

import org.springframework.stereotype.Component;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.ActivityType;

/**
 * This is a static class used for points calculation.
 */
@Component
public final class CalculatePoints
{
    static final int KILOMETER_VALUE = 3;
    static final int MINUTE_VALUE = 1;
    static final int SECONDS_IN_MINUTE = 60;

    private CalculatePoints() {
    }

    public static int getPointsForActivity(Activity activity, double handicap) {
        int durationPoints = getPointsForDuration(activity.getMovingTimeInSeconds() / SECONDS_IN_MINUTE);
        int distancePoints = getPointsForDistance(activity.getDistanceInMeters() / 1000);
        ActivityType activityType = ActivityType.toActivityType(activity.getType());
        if (activityType == null) {
            return 0;
        } else {
            return Double.valueOf((durationPoints + distancePoints) * activityType.coefficient() * handicap).intValue();
        }
    }

    private static int getPointsForDistance(double distanceInKilometers) {
        return Double.valueOf(distanceInKilometers * KILOMETER_VALUE).intValue();
    }

    private static int getPointsForDuration(int durationInMinutes) {
        return durationInMinutes * MINUTE_VALUE;
    }

}
