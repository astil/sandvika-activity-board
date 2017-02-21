package no.bouvet.sandvika.activityboard.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class Activity
{
    @Id
    private int id;
    private int points;
    private String name;
    private String athleteLastName;
    private String athletefirstName;
    private String description;
    private float distanceInMeters;
    private int movingTimeInSeconds;
    private int elapsedTimeInSeconds;
    private float totalElevationGaininMeters;
    private String type;
    private LocalDateTime startDateLocal;
    private String timezone;
    @GeoSpatialIndexed
    private double[] startLatLng;
    @GeoSpatialIndexed
    private double[] endLatLng;
    private int achievementCount;
    private int kudosCount;
    private int commentCount;
    private int athleteCount;
    private boolean trainer;
    private boolean commute;
    private boolean manual;
    private float averageSpeedInMetersPerSecond;
    private float maxSpeedInMetersPerSecond;
    private boolean hasHeartrate;
    private float averageHeartrate;
    private int maxHeartrate;
    private float calories;
    private int sufferScore;


    public Activity() {}

    public Activity(int id, int points, String athleteLastName, String athletefirstName) {
        this.id = id;
        this.points = points;
        this.athletefirstName = athletefirstName;
        this.athleteLastName = athleteLastName;
    }

    public Activity(int id, int points, String athleteLastName, String athletefirstName, LocalDateTime startDateLocal) {
        this.id = id;
        this.points = points;
        this.athletefirstName = athletefirstName;
        this.athleteLastName = athleteLastName;
        this.startDateLocal = startDateLocal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAthleteLastName() {
        return athleteLastName;
    }

    public void setAthleteLastName(String athleteLastName) {
        this.athleteLastName = athleteLastName;
    }

    public String getAthletefirstName() {
        return athletefirstName;
    }

    public void setAthletefirstName(String athletefirstName) {
        this.athletefirstName = athletefirstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(float distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public int getMovingTimeInSeconds() {
        return movingTimeInSeconds;
    }

    public void setMovingTimeInSeconds(int movingTimeInSeconds) {
        this.movingTimeInSeconds = movingTimeInSeconds;
    }

    public int getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

    public void setElapsedTimeInSeconds(int elapsedTimeInSeconds) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
    }

    public float getTotalElevationGaininMeters() {
        return totalElevationGaininMeters;
    }

    public void setTotalElevationGaininMeters(float totalElevationGaininMeters) {
        this.totalElevationGaininMeters = totalElevationGaininMeters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartDateLocal() {
        return startDateLocal;
    }

    public void setStartDateLocal(LocalDateTime startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double[] getStartLatLng() {
        return startLatLng;
    }

    public void setStartLatLng(double[] startLatLng) {
        this.startLatLng = startLatLng;
    }

    public double[] getEndLatLng() {
        return endLatLng;
    }

    public void setEndLatLng(double[] endLatLng) {
        this.endLatLng = endLatLng;
    }

    public int getAchievementCount() {
        return achievementCount;
    }

    public void setAchievementCount(int achievementCount) {
        this.achievementCount = achievementCount;
    }

    public int getKudosCount() {
        return kudosCount;
    }

    public void setKudosCount(int kudosCount) {
        this.kudosCount = kudosCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getAthleteCount() {
        return athleteCount;
    }

    public void setAthleteCount(int athleteCount) {
        this.athleteCount = athleteCount;
    }

    public boolean isTrainer() {
        return trainer;
    }

    public void setTrainer(boolean trainer) {
        this.trainer = trainer;
    }

    public boolean isCommute() {
        return commute;
    }

    public void setCommute(boolean commute) {
        this.commute = commute;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public float getAverageSpeedInMetersPerSecond() {
        return averageSpeedInMetersPerSecond;
    }

    public void setAverageSpeedInMetersPerSecond(float averageSpeedInMetersPerSecond) {
        this.averageSpeedInMetersPerSecond = averageSpeedInMetersPerSecond;
    }

    public float getMaxSpeedInMetersPerSecond() {
        return maxSpeedInMetersPerSecond;
    }

    public void setMaxSpeedInMetersPerSecond(float maxSpeedInMetersPerSecond) {
        this.maxSpeedInMetersPerSecond = maxSpeedInMetersPerSecond;
    }

    public boolean isHasHeartrate() {
        return hasHeartrate;
    }

    public void setHasHeartrate(boolean hasHeartrate) {
        this.hasHeartrate = hasHeartrate;
    }

    public float getAverageHeartrate() {
        return averageHeartrate;
    }

    public void setAverageHeartrate(float averageHeartrate) {
        this.averageHeartrate = averageHeartrate;
    }

    public int getMaxHeartrate() {
        return maxHeartrate;
    }

    public void setMaxHeartrate(int maxHeartrate) {
        this.maxHeartrate = maxHeartrate;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public int getSufferScore() {
        return sufferScore;
    }

    public void setSufferScore(int sufferScore) {
        this.sufferScore = sufferScore;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return id == activity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
