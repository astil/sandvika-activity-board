package no.bouvet.sandvika.activityboard.domain;

public class Photo {
    private ActivitySummary activitySummary;
    private String url;

    public Photo(ActivitySummary activitySummary, String url) {
        this.activitySummary = activitySummary;
        this.url = url;
    }

    public ActivitySummary getActivitySummary() {
        return this.activitySummary;
    }

    public String getUrl() {
        return url;
    }
}
