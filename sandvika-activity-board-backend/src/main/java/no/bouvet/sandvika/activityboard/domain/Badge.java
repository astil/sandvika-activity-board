package no.bouvet.sandvika.activityboard.domain;

import org.springframework.data.annotation.Id;

public class Badge
{
    @Id
    private String name;
    private String type;
    private String activityType;
    private int distanceCriteria;
    private String timeCriteria;
    private String moreOrLess;

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

    public String getMoreOrLess()
    {
        return moreOrLess;
    }

    public void setMoreOrLess(String moreOrLess)
    {
        this.moreOrLess = moreOrLess;
    }
}
