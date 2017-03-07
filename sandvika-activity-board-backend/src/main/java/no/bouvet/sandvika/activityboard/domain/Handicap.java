package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;

public class Handicap
{
    private double handicap;
    private Date timestamp;

    public Handicap(){}

    public Handicap(double handicap, Date timestamp)
    {
        this.handicap = handicap;
        this.timestamp = timestamp;
    }

    public double getHandicap()
    {
        return handicap;
    }

    public void setHandicap(double handicap)
    {
        this.handicap = handicap;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Handicap{");
        sb.append("handicap=").append(handicap);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
