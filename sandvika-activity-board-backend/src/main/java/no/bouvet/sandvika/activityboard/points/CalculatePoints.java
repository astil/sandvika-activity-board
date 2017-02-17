package no.bouvet.sandvika.activityboard.points;

import no.bouvet.sandvika.activityboard.domain.ActivityType;

/**
 * This is a static class used for points calculation.
 */
public final class CalculatePoints {

    static final int KILOMETER_VALUE = 3;
    static final int MINUTE_VALUE = 1;

    private CalculatePoints() {
    }

    public static int getPointsForActivity(ActivityType activityType, int durationInMinutes, double distanceInKilometers, double handicap) {
        int durationPoints = getPointsForDuration(durationInMinutes);
        int distancePoints = getPointsForDistance(distanceInKilometers);
        return Double.valueOf((durationPoints + distancePoints) * activityType.coefficient() * handicap).intValue();
    }

    private static int getPointsForDistance(double distanceInKilometers) {
        return Double.valueOf(distanceInKilometers * KILOMETER_VALUE).intValue();
    }

    private static int getPointsForDuration(int durationInMinutes) {
        return durationInMinutes * MINUTE_VALUE;
    }

}
