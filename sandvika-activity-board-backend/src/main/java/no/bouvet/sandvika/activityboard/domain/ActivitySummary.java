package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;

public class ActivitySummary {
    private long id;
    private double points;
    private String name;
    private String athleteLastName;
    private String athleteFirstName;
    private String description;
    private double distanceInMeters;
    private int movingTimeInSeconds;
    private int elapsedTimeInSeconds;
    private double totalElevationGaininMeters;
    private String type;
    private Date startDateLocal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAthleteLastName() {
        return athleteLastName;
    }

    public void setAthleteLastName(String athleteLastName) {
        this.athleteLastName = athleteLastName;
    }

    public String getAthleteFirstName() {
        return athleteFirstName;
    }

    public void setAthleteFirstName(String athleteFirstName) {
        this.athleteFirstName = athleteFirstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(double distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public int getMovingTimeInSeconds() {
        return movingTimeInSeconds;
    }

    public void setMovingTimeInSeconds(int movingTimeInSeconds) {
        this.movingTimeInSeconds = movingTimeInSeconds;
    }

    public int getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

    public void setElapsedTimeInSeconds(int elapsedTimeInSeconds) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
    }

    public double getTotalElevationGaininMeters() {
        return totalElevationGaininMeters;
    }

    public void setTotalElevationGaininMeters(double totalElevationGaininMeters) {
        this.totalElevationGaininMeters = totalElevationGaininMeters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDateLocal() {
        return startDateLocal;
    }

    public void setStartDateLocal(Date startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

}
