package no.bouvet.sandvika.activityboard.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "resource_state",
        "name",
        "activity",
        "athlete",
        "elapsed_time",
        "moving_time",
        "start_date",
        "start_date_local",
        "distance",
        "start_index",
        "end_index",
        "total_elevation_gain",
        "average_speed",
        "max_speed",
        "average_cadence",
        "device_watts",
        "average_watts",
        "lap_index",
        "split"
})

public class StravaLap {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("resource_state")
    private Integer resourceState;
    @JsonProperty("name")
    private String name;
    @JsonProperty("activity")
    private StravaActivityFull activity;
    @JsonProperty("athlete")
    private StravaAthlete athlete;
    @JsonProperty("elapsed_time")
    private Integer elapsedTime;
    @JsonProperty("moving_time")
    private Integer movingTime;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("start_date_local")
    private String startDateLocal;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("start_index")
    private Integer startIndex;
    @JsonProperty("end_index")
    private Integer endIndex;
    @JsonProperty("total_elevation_gain")
    private Integer totalElevationGain;
    @JsonProperty("average_speed")
    private Double averageSpeed;
    @JsonProperty("max_speed")
    private Double maxSpeed;
    @JsonProperty("average_cadence")
    private Double averageCadence;
    @JsonProperty("device_watts")
    private Boolean deviceWatts;
    @JsonProperty("average_watts")
    private Double averageWatts;
    @JsonProperty("lap_index")
    private Integer lapIndex;
    @JsonProperty("split")
    private Integer split;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("resource_state")
    public Integer getResourceState() {
        return resourceState;
    }

    @JsonProperty("resource_state")
    public void setResourceState(Integer resourceState) {
        this.resourceState = resourceState;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("activity")
    public StravaActivityFull getActivity() {
        return activity;
    }

    @JsonProperty("activity")
    public void setActivity(StravaActivityFull activity) {
        this.activity = activity;
    }

    @JsonProperty("athlete")
    public StravaAthlete getAthlete() {
        return athlete;
    }

    @JsonProperty("athlete")
    public void setAthlete(StravaAthlete athlete) {
        this.athlete = athlete;
    }

    @JsonProperty("elapsed_time")
    public Integer getElapsedTime() {
        return elapsedTime;
    }

    @JsonProperty("elapsed_time")
    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @JsonProperty("moving_time")
    public Integer getMovingTime() {
        return movingTime;
    }

    @JsonProperty("moving_time")
    public void setMovingTime(Integer movingTime) {
        this.movingTime = movingTime;
    }

    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("start_date_local")
    public String getStartDateLocal() {
        return startDateLocal;
    }

    @JsonProperty("start_date_local")
    public void setStartDateLocal(String startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

    @JsonProperty("distance")
    public Double getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @JsonProperty("start_index")
    public Integer getStartIndex() {
        return startIndex;
    }

    @JsonProperty("start_index")
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    @JsonProperty("end_index")
    public Integer getEndIndex() {
        return endIndex;
    }

    @JsonProperty("end_index")
    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    @JsonProperty("total_elevation_gain")
    public Integer getTotalElevationGain() {
        return totalElevationGain;
    }

    @JsonProperty("total_elevation_gain")
    public void setTotalElevationGain(Integer totalElevationGain) {
        this.totalElevationGain = totalElevationGain;
    }

    @JsonProperty("average_speed")
    public Double getAverageSpeed() {
        return averageSpeed;
    }

    @JsonProperty("average_speed")
    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @JsonProperty("max_speed")
    public Double getMaxSpeed() {
        return maxSpeed;
    }

    @JsonProperty("max_speed")
    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @JsonProperty("average_cadence")
    public Double getAverageCadence() {
        return averageCadence;
    }

    @JsonProperty("average_cadence")
    public void setAverageCadence(Double averageCadence) {
        this.averageCadence = averageCadence;
    }

    @JsonProperty("device_watts")
    public Boolean getDeviceWatts() {
        return deviceWatts;
    }

    @JsonProperty("device_watts")
    public void setDeviceWatts(Boolean deviceWatts) {
        this.deviceWatts = deviceWatts;
    }

    @JsonProperty("average_watts")
    public Double getAverageWatts() {
        return averageWatts;
    }

    @JsonProperty("average_watts")
    public void setAverageWatts(Double averageWatts) {
        this.averageWatts = averageWatts;
    }

    @JsonProperty("lap_index")
    public Integer getLapIndex() {
        return lapIndex;
    }

    @JsonProperty("lap_index")
    public void setLapIndex(Integer lapIndex) {
        this.lapIndex = lapIndex;
    }

    @JsonProperty("split")
    public Integer getSplit() {
        return split;
    }

    @JsonProperty("split")
    public void setSplit(Integer split) {
        this.split = split;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
