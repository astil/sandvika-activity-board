package no.bouvet.sandvika.activityboard.domain;

/**
 * Enum representing the activity types we support
 */

public enum ActivityType
{
    NORDIC_SKIING(Constants.NORDIC_SKIING_NAME),
    RUN(Constants.RUN_NAME),
    RIDE(Constants.RIDE_NAME),
    SWIM(Constants.SWIM_NAME);


    private String type;

    ActivityType(String type) {
        this.type = type;
    }

    /**
     * Returns an ActivityType based on the activity name provided. Returns NULL if the name is not a valid
     * activity name or if the activityName paramater is NULL.
     *
     * @return an ActivityType if a valid activityName is provided, else NULL.
     */

    public static ActivityType toActivityType(String activityName) {
        if (activityName == null) {
            return null;
        } else if (activityName.equalsIgnoreCase(Constants.NORDIC_SKIING_NAME)) {
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

    public String type() {
        return this.type;
    }

    public double coefficient() {
        switch (this) {
            case NORDIC_SKIING:
                return Constants.NORDIC_SKIIING_COEFFISIENT;
            case RIDE:
                return Constants.RIDE_COEFFISIENT;
            case RUN:
                return Constants.RUN_COEFFISIENT;
            case SWIM:
                return Constants.SWIM_COEFFISIENT;
            default:
                return 1;
        }
    }

    private static class Constants
    {
        public static final String NORDIC_SKIING_NAME = "Nordic Skiing";
        public static final String RUN_NAME = "Run";
        public static final String RIDE_NAME = "Ride";
        public static final String SWIM_NAME = "Swim";
        public static final double NORDIC_SKIIING_COEFFISIENT = 0.6;
        public static final double RIDE_COEFFISIENT = 0.4;
        public static final double RUN_COEFFISIENT = 1;
        public static final double SWIM_COEFFISIENT = 1.5;

    }
}
