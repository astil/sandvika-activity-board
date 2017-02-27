package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;

public class Handicap
{
    private double handicap;
    private Date timestamp;

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
}
