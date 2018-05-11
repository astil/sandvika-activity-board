package no.bouvet.sandvika.activityboard.domain;

import no.bouvet.sandvika.activityboard.points.PointsCalculator;

public class PointsCalculation {
    private int minutes;
    private double elevationGain;
    private double km;
    private int achievements;
    private double hc;
    private String activityType;
    private int activityId;
    private double kmCoeffisient;
    private double minCoeffisient;
    private double elevationCoeffisient;
    private String calculation;

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getElevationGain() {
        return elevationGain;
    }

    public void setElevationGain(double elevationGain) {
        this.elevationGain = elevationGain;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public int getAchievements() {
        return achievements;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public double getHc() {
        return hc;
    }

    public void setHc(double hc) {
        this.hc = hc;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public double getKmCoeffisient() {
        return kmCoeffisient;
    }

    public void setKmCoeffisient(double kmCoeffisient) {
        this.kmCoeffisient = kmCoeffisient;
    }

    public double getMinCoeffisient() {
        return minCoeffisient;
    }

    public void setMinCoeffisient(double minCoeffisient) {
        this.minCoeffisient = minCoeffisient;
    }

    public double getElevationCoeffisient() {
        return elevationCoeffisient;
    }

    public void setElevationCoeffisient(double elevationCoeffisient) {
        this.elevationCoeffisient = elevationCoeffisient;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public void createCalculation() {
        String calc = "Points = (((" +
                minutes + " * " + PointsCalculator.MINUTE_VALUE + " * " + minCoeffisient +
                ") + (" +
                km + " * " + PointsCalculator.KILOMETER_VALUE + " * " + kmCoeffisient +
                ") + (" +
                elevationGain + " * " + PointsCalculator.ELEVATIOIN_METER_VALUE + " * " + elevationCoeffisient +
                ") + " +
                achievements + ") * " + hc + ") /2" + " = " +
                (((minutes * minCoeffisient * PointsCalculator.MINUTE_VALUE) + (km * kmCoeffisient * PointsCalculator.KILOMETER_VALUE) + (elevationGain * elevationCoeffisient * PointsCalculator.ELEVATIOIN_METER_VALUE) + achievements) * hc)/2;
        calculation = calc;
    }

    @Override
    public String toString() {
        return "PointsCalculation{" +
                "minutes=" + minutes +
                ", elevationGain=" + elevationGain +
                ", km=" + km +
                ", achievements=" + achievements +
                ", hc=" + hc +
                ", activityType='" + activityType + '\'' +
                ", activityId=" + activityId +
                ", kmCoeffisient=" + kmCoeffisient +
                ", minCoeffisient=" + minCoeffisient +
                ", elevationCoeffisient=" + elevationCoeffisient +
                '}';
    }


}
