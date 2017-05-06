package no.bouvet.sandvika.activityboard.domain;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Badge
{
    @Id
    private String name;
    private String type;
    private String activityType;
    private int distanceCriteria;
    private String timeCriteria;
    private String beforeOrAfter;
    private List<Activity> activities;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getActivityType()
    {
        return activityType;
    }

    public void setActivityType(String activityType)
    {
        this.activityType = activityType;
    }

    public int getDistanceCriteria()
    {
        return distanceCriteria;
    }

    public void setDistanceCriteria(int distanceCriteria)
    {
        this.distanceCriteria = distanceCriteria;
    }

    public String getTimeCriteria()
    {
        return timeCriteria;
    }

    public void setTimeCriteria(String timeCriteria)
    {
        this.timeCriteria = timeCriteria;
    }

    public String getBeforeOrAfter()
    {
        return beforeOrAfter;
    }

    public void setBeforeOrAfter(String beforeOrAfter)
    {
        this.beforeOrAfter = beforeOrAfter;
    }

    public List<Activity> getActivities()
    {
        if (activities == null)
        {
            activities = new ArrayList<Activity>();
        }
        return activities;
    }

    public void setActivities(List<Activity> activities)
    {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Badge badge = (Badge) o;

        return name != null ? name.equals(badge.name) : badge.name == null;
    }

    @Override
    public int hashCode()
    {
        return name != null ? name.hashCode() : 0;
    }
}
