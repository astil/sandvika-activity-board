package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;

public class Statistics
{
    private PeriodType periodType;
    private Date startDate;
    private double meters;
    private double calories;
    private double minutes;
    private double activities;
    private double achievements;

    public PeriodType getPeriodType()
    {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType)
    {
        this.periodType = periodType;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public double getMeters()
    {
        return meters;
    }

    public void setMeters(double meters)
    {
        this.meters = meters;
    }

    public double getCalories()
    {
        return calories;
    }

    public void setCalories(double calories)
    {
        this.calories = calories;
    }

    public double getMinutes()
    {
        return minutes;
    }

    public void setMinutes(double minutes)
    {
        this.minutes = minutes;
    }

    public double getActivities()
    {
        return activities;
    }

    public void setActivities(double activities)
    {
        this.activities = activities;
    }

    public double getAchievements()
    {
        return achievements;
    }

    public void setAchievements(double achievements)
    {
        this.achievements = achievements;
    }
}
