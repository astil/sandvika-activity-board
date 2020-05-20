package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class AthleteLeaderboardHistroy {
    private long athleteId;
    private String firstName;
    private String lastName;
    private HashMap<Date, Integer> history;

    public AthleteLeaderboardHistroy(int athleteId, String athleteFirstName, String athleteLastName) {
        this.athleteId = athleteId;
        this.firstName = athleteFirstName;
        this.lastName = athleteLastName;
    }

    public long getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(long athleteId) {
        this.athleteId = athleteId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashMap<Date, Integer> getHistory() {
        return history;
    }

    public void setHistory(HashMap<Date, Integer> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AthleteLeaderboardHistroy that = (AthleteLeaderboardHistroy) o;
        return athleteId == that.athleteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(athleteId);
    }
}
