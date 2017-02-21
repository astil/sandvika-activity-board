package no.bouvet.sandvika.activityboard.points;

import org.junit.Test;

import no.bouvet.sandvika.activityboard.domain.ActivityType;

public class CalculatePointsTest
{
    @Test
    public void sanityCheckOnValues() {
        ActivityType type = ActivityType.RUN;
        int duration = 58;
        double distance = 12;
        double handicap = 1;
        int points = CalculatePoints.getPointsForActivity(type, duration, distance, handicap);
        System.out.println(type.type() + " km: " + distance + " min: " + duration + " hc: " + handicap + " = " + points);

        type = ActivityType.RUN;
        duration = 33;
        distance = 6.3;
        handicap = 1.4;
        points = CalculatePoints.getPointsForActivity(type, duration, distance, handicap);
        System.out.println(type.type() + " km: " + distance + " min: " + duration + " hc: " + handicap + " = " + points);

        type = ActivityType.NORDIC_SKIING;
        duration = 93;
        distance = 22.4;
        handicap = 1;
        points = CalculatePoints.getPointsForActivity(type, duration, distance, handicap);
        System.out.println(type.type() + " km: " + distance + " min: " + duration + " hc: " + handicap + " = " + points);

        type = ActivityType.RIDE;
        duration = 109;
        distance = 36.3;
        handicap = 1.7;
        points = CalculatePoints.getPointsForActivity(type, duration, distance, handicap);
        System.out.println(type.type() + " km: " + distance + " min: " + duration + " hc: " + handicap + " = " + points);

        type = ActivityType.SWIM;
        duration = 30;
        distance = 1.2;
        handicap = 1;
        points = CalculatePoints.getPointsForActivity(type, duration, distance, handicap);
        System.out.println(type.type() + " km: " + distance + " min: " + duration + " hc: " + handicap + " = " + points);

    }

}