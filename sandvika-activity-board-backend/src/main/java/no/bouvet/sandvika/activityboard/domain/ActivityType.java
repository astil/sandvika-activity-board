package no.bouvet.sandvika.activityboard.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Enum representing the activity types we support
 */

public enum ActivityType
{
    NORDIC_SKIING(Constants.NORDIC_SKIING_NAME),
    RUN(Constants.RUN_NAME),
    RIDE(Constants.RIDE_NAME),
    SWIM(Constants.SWIM_NAME);

    @Value("${pointscalculation.coefficients.nordic_skiing}")
    private double nordicSkiingCoefficient;
    @Value("${pointscalculation.coefficients.ride}")
    private double rideCofficient;
    @Value("${pointscalculation.coefficients.run}")
    private double runCoefficient;
    @Value("${pointscalculation.coefficients.swim}")
    private double swimCoefficient;


    private String type;

    ActivityType(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }

    public double coefficient() {
        switch (this) {
            case NORDIC_SKIING:
                return nordicSkiingCoefficient;
            case RIDE:
                return rideCofficient;
            case RUN:
                return runCoefficient;
            case SWIM:
                return swimCoefficient;
            default:
                return 1;
        }
    }

    /**
     * Returns an ActivityType based on the activity name provided. Returns NULL if the name is not a valid
     * activity name or if the activityName paramater is NULL.
     *
     * @param activityName
     * @return an ActivityType if a valid activityName is provided, else NULL.
     */

    public static ActivityType toActivityType(String activityName) {
        if (activityName == null) {
            return null;
        }
        else if (activityName.equalsIgnoreCase(Constants.NORDIC_SKIING_NAME)) {
            return ActivityType.NORDIC_SKIING;
        } else if (activityName.equalsIgnoreCase(Constants.RUN_NAME)) {
            return ActivityType.RUN;
        } else if (activityName.equalsIgnoreCase(Constants.RIDE_NAME)) {
            return ActivityType.RIDE;
        } else if (activityName.equalsIgnoreCase(Constants.SWIM_NAME)) {
            return ActivityType.SWIM;
        } else {
            return null;
        }
    }

    private static class Constants
    {
        public static final String NORDIC_SKIING_NAME = "Nordic Skiing";
        public static final String RUN_NAME = "Run";
        public static final String RIDE_NAME = "Ride";
        public static final String SWIM_NAME = "Swim";

    }
}
