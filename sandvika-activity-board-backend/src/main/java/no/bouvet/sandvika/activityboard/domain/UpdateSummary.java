package no.bouvet.sandvika.activityboard.domain;

import java.util.HashMap;
import java.util.Map;

public class UpdateSummary {
    Map<String, Integer> numberOfActivitiesUpdated;

    public Map<String, Integer> getNumberOfActivitiesUpdated() {
        return numberOfActivitiesUpdated;
    }

    public void setNumberOfActivitiesUpdated(Map<String, Integer> numberOfActivitiesUpdated) {
        this.numberOfActivitiesUpdated = numberOfActivitiesUpdated;
    }

    public void addNumberOfActivities(String athlete, Integer number) {
        if (numberOfActivitiesUpdated == null) {
            numberOfActivitiesUpdated = new HashMap<>();
        }
        numberOfActivitiesUpdated.put(athlete, number);
    }
}
