package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;

import no.bouvet.sandvika.activityboard.utils.Utils;

public class LeaderboardEntry
{
    private String athleteLastName;
    private String athleteFirstName;
    private double points;
    private Date lastActivityDate;
    private int numberOfActivities;
    private double kilometers;
    private int minutes;
    private double handicap;

    public LeaderboardEntry(String lastName, Double points) {
        this.athleteLastName = lastName;
        this.points = points;
    }

    public double getKilometers() {
        return Utils.scaledDouble(kilometers);
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getHandicap() {
        return Utils.scaledDouble(handicap);
    }

    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }

    public int getNumberOfActivities() {
        return numberOfActivities;
    }

    public void setNumberOfActivities(int numberOfActivities) {
        this.numberOfActivities = numberOfActivities;
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

    public double getPoints() {
        return Utils.scaledDouble(points);
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
}
