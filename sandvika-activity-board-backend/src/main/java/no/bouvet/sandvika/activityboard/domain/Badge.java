package no.bouvet.sandvika.activityboard.domain;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Badge
{
    @Id
    private String name;
    private String type;
    private String uri;
    private String activityType;
    private int distanceCriteria;
    private String timeCriteria;
    private String beforeOrAfter;
    private int points;
    private Set<Activity> activities;

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

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

    public Set<Activity> getActivities()
    {
        if (activities == null)
        {
            activities = new HashSet<>();
        }
        return activities;
    }

    public void setActivities(Set<Activity> activities)
    {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(name, badge.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Badge{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", uri='" + uri + '\'' +
                ", activityType='" + activityType + '\'' +
                ", distanceCriteria=" + distanceCriteria +
                ", timeCriteria='" + timeCriteria + '\'' +
                ", beforeOrAfter='" + beforeOrAfter + '\'' +
                ", points=" + points +
                ", activities=" + activities +
                '}';
    }
}
