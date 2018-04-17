package no.bouvet.sandvika.activityboard.domain;

public class Photo {
    private int activityId;
    private String url;

    public Photo(int activityId, String url) {
        this.activityId = activityId;
        this.url = url;
    }

    public int getActivityId() {
        return activityId;
    }

    public String getUrl() {
        return url;
    }
}
