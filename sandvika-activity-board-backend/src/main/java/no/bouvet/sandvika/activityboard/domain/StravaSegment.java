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
        "activity_type",
        "distance",
        "average_grade",
        "maximum_grade",
        "elevation_high",
        "elevation_low",
        "start_latlng",
        "end_latlng",
        "start_latitude",
        "start_longitude",
        "end_latitude",
        "end_longitude",
        "climb_category",
        "city",
        "state",
        "country",
        "private",
        "hazardous",
        "starred"
})
public class StravaSegment {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("resource_state")
    private Integer resourceState;
    @JsonProperty("name")
    private String name;
    @JsonProperty("activity_type")
    private String activityType;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("average_grade")
    private Double averageGrade;
    @JsonProperty("maximum_grade")
    private Double maximumGrade;
    @JsonProperty("elevation_high")
    private Double elevationHigh;
    @JsonProperty("elevation_low")
    private Double elevationLow;
    @JsonProperty("start_latlng")
    private List<Double> startLatlng = null;
    @JsonProperty("end_latlng")
    private List<Double> endLatlng = null;
    @JsonProperty("start_latitude")
    private Double startLatitude;
    @JsonProperty("start_longitude")
    private Double startLongitude;
    @JsonProperty("end_latitude")
    private Double endLatitude;
    @JsonProperty("end_longitude")
    private Double endLongitude;
    @JsonProperty("climb_category")
    private Integer climbCategory;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country")
    private String country;
    @JsonProperty("private")
    private Boolean _private;
    @JsonProperty("hazardous")
    private Boolean hazardous;
    @JsonProperty("starred")
    private Boolean starred;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
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

    @JsonProperty("activity_type")
    public String getActivityType() {
        return activityType;
    }

    @JsonProperty("activity_type")
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @JsonProperty("distance")
    public Double getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @JsonProperty("average_grade")
    public Double getAverageGrade() {
        return averageGrade;
    }

    @JsonProperty("average_grade")
    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    @JsonProperty("maximum_grade")
    public Double getMaximumGrade() {
        return maximumGrade;
    }

    @JsonProperty("maximum_grade")
    public void setMaximumGrade(Double maximumGrade) {
        this.maximumGrade = maximumGrade;
    }

    @JsonProperty("elevation_high")
    public Double getElevationHigh() {
        return elevationHigh;
    }

    @JsonProperty("elevation_high")
    public void setElevationHigh(Double elevationHigh) {
        this.elevationHigh = elevationHigh;
    }

    @JsonProperty("elevation_low")
    public Double getElevationLow() {
        return elevationLow;
    }

    @JsonProperty("elevation_low")
    public void setElevationLow(Double elevationLow) {
        this.elevationLow = elevationLow;
    }

    @JsonProperty("start_latlng")
    public List<Double> getStartLatlng() {
        return startLatlng;
    }

    @JsonProperty("start_latlng")
    public void setStartLatlng(List<Double> startLatlng) {
        this.startLatlng = startLatlng;
    }

    @JsonProperty("end_latlng")
    public List<Double> getEndLatlng() {
        return endLatlng;
    }

    @JsonProperty("end_latlng")
    public void setEndLatlng(List<Double> endLatlng) {
        this.endLatlng = endLatlng;
    }

    @JsonProperty("start_latitude")
    public Double getStartLatitude() {
        return startLatitude;
    }

    @JsonProperty("start_latitude")
    public void setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    @JsonProperty("start_longitude")
    public Double getStartLongitude() {
        return startLongitude;
    }

    @JsonProperty("start_longitude")
    public void setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
    }

    @JsonProperty("end_latitude")
    public Double getEndLatitude() {
        return endLatitude;
    }

    @JsonProperty("end_latitude")
    public void setEndLatitude(Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    @JsonProperty("end_longitude")
    public Double getEndLongitude() {
        return endLongitude;
    }

    @JsonProperty("end_longitude")
    public void setEndLongitude(Double endLongitude) {
        this.endLongitude = endLongitude;
    }

    @JsonProperty("climb_category")
    public Integer getClimbCategory() {
        return climbCategory;
    }

    @JsonProperty("climb_category")
    public void setClimbCategory(Integer climbCategory) {
        this.climbCategory = climbCategory;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("private")
    public Boolean getPrivate() {
        return _private;
    }

    @JsonProperty("private")
    public void setPrivate(Boolean _private) {
        this._private = _private;
    }

    @JsonProperty("hazardous")
    public Boolean getHazardous() {
        return hazardous;
    }

    @JsonProperty("hazardous")
    public void setHazardous(Boolean hazardous) {
        this.hazardous = hazardous;
    }

    @JsonProperty("starred")
    public Boolean getStarred() {
        return starred;
    }

    @JsonProperty("starred")
    public void setStarred(Boolean starred) {
        this.starred = starred;
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

