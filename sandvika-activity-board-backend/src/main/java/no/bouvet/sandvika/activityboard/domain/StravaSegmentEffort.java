package no.bouvet.sandvika.activityboard.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
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
        "average_cadence",
        "device_watts",
        "average_watts",
        "segment",
        "kom_rank",
        "pr_rank",
        "achievements",
        "hidden"
})
public class StravaSegmentEffort {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("resource_state")
    private Integer resourceState;
    @JsonProperty("name")
    private String name;
    @JsonProperty("activity")
    private StravaActivity activity;
    @JsonProperty("athlete")
    private Athlete athlete;
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
    @JsonProperty("average_cadence")
    private Double averageCadence;
    @JsonProperty("device_watts")
    private Boolean deviceWatts;
    @JsonProperty("average_watts")
    private Double averageWatts;
    @JsonProperty("segment")
    private StravaSegment segment;
    @JsonProperty("kom_rank")
    private Object komRank;
    @JsonProperty("pr_rank")
    private Object prRank;
    @JsonProperty("achievements")
    private List<Object> achievements = null;
    @JsonProperty("hidden")
    private Boolean hidden;
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
    public StravaActivity getActivity() {
        return activity;
    }

    @JsonProperty("activity")
    public void setActivity(StravaActivity activity) {
        this.activity = activity;
    }

    @JsonProperty("athlete")
    public Athlete getAthlete() {
        return athlete;
    }

    @JsonProperty("athlete")
    public void setAthlete(Athlete athlete) {
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

    @JsonProperty("segment")
    public StravaSegment getSegment() {
        return segment;
    }

    @JsonProperty("segment")
    public void setSegment(StravaSegment segment) {
        this.segment = segment;
    }

    @JsonProperty("kom_rank")
    public Object getKomRank() {
        return komRank;
    }

    @JsonProperty("kom_rank")
    public void setKomRank(Object komRank) {
        this.komRank = komRank;
    }

    @JsonProperty("pr_rank")
    public Object getPrRank() {
        return prRank;
    }

    @JsonProperty("pr_rank")
    public void setPrRank(Object prRank) {
        this.prRank = prRank;
    }

    @JsonProperty("achievements")
    public List<Object> getAchievements() {
        return achievements;
    }

    @JsonProperty("achievements")
    public void setAchievements(List<Object> achievements) {
        this.achievements = achievements;
    }

    @JsonProperty("hidden")
    public Boolean getHidden() {
        return hidden;
    }

    @JsonProperty("hidden")
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
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
