package no.bouvet.sandvika.activityboard.domain;

import org.springframework.data.annotation.Id;

public class Athlete
{
    @Id
    private String lastName;
    private double handicap;

    public double getHandicap() {
        return handicap;
    }

    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Athlete athlete = (Athlete) o;

        if (Double.compare(athlete.handicap, handicap) != 0) return false;
        return lastName != null ? lastName.equals(athlete.lastName) : athlete.lastName == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = lastName != null ? lastName.hashCode() : 0;
        temp = Double.doubleToLongBits(handicap);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
