package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;

public class LeaderboardEntry
{
    private String athleteLastName;
    private String athleteFirstName;
    private int points;
    private Date lastActivityDate;

    public LeaderboardEntry(String lastName, Integer points) {
        this.athleteLastName = lastName;
        this.points = points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
}
