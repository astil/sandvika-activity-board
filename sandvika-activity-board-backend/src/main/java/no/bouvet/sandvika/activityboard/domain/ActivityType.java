package no.bouvet.sandvika.activityboard.domain;

/**
 * Enum representing the activity types we support
 */
public enum ActivityType
{
    NORDIC_SKIING("Nordic Skiing"),
    RUN("Run"),
    RIDE("Ride"),
    SWIM("Swim");

    private static final double NORDIC_SKIING_COEFFICIENT = 0.6;
    private static final double RIDE_COEFFICIENT = 0.4;
    private static final int RUN_COEFFICIENT = 1;
    private static final double SWIM_COEFFICIENT = 1.5;
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
                return NORDIC_SKIING_COEFFICIENT;
            case RIDE:
                return RIDE_COEFFICIENT;
            case RUN:
                return RUN_COEFFICIENT;
            case SWIM:
                return SWIM_COEFFICIENT;
            default:
                return 1;
        }
    }

}
