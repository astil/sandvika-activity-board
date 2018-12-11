package no.bouvet.sandvika.activityboard.domain;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "resource_state",
    "external_id",
    "upload_id",
    "athlete",
    "name",
    "distance",
    "moving_time",
    "elapsed_time",
    "total_elevation_gain",
    "type",
    "start_date",
    "start_date_local",
    "timezone",
    "utc_offset",
    "start_latlng",
    "end_latlng",
    "location_city",
    "location_state",
    "location_country",
    "start_latitude",
    "start_longitude",
    "achievement_count",
    "kudos_count",
    "comment_count",
    "athlete_count",
    "photo_count",
    "map",
    "trainer",
    "commute",
    "manual",
    "private",
    "flagged",
    "gear_id",
    "from_accepted_tag",
    "average_speed",
    "max_speed",
    "average_watts",
    "kilojoules",
    "device_watts",
    "has_heartrate",
    "average_heartrate",
    "max_heartrate",
    "elev_high",
    "elev_low",
    "pr_count",
    "total_photo_count",
    "has_kudoed",
    "workout_type",
    "suffer_score",

})
public class StravaActivity
{
    @JsonProperty("id")
    private Long id;
    @JsonProperty("resource_state")
    private Integer resourceState;
    @JsonProperty("external_id")
    private String externalId;
    @JsonProperty("upload_id")
    private Long uploadId;
    @JsonProperty("athlete")
    private StravaAthlete athlete;
    @JsonProperty("name")
    private String name;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("moving_time")
    private Integer movingTime;
    @JsonProperty("elapsed_time")
    private Integer elapsedTime;
    @JsonProperty("total_elevation_gain")
    private Double totalElevationGain;
    @JsonProperty("type")
    private String type;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("start_date_local")
    private String startDateLocal;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("utc_offset")
    private Integer utcOffset;
    @JsonProperty("start_latlng")
    private List<Double> startLatlng = null;
    @JsonProperty("end_latlng")
    private List<Double> endLatlng = null;
    @JsonProperty("location_city")
    private Object locationCity;
    @JsonProperty("location_state")
    private Object locationState;
    @JsonProperty("location_country")
    private String locationCountry;
    @JsonProperty("start_latitude")
    private Double startLatitude;
    @JsonProperty("start_longitude")
    private Double startLongitude;
    @JsonProperty("achievement_count")
    private Integer achievementCount;
    @JsonProperty("kudos_count")
    private Integer kudosCount;
    @JsonProperty("comment_count")
    private Integer commentCount;
    @JsonProperty("athlete_count")
    private Integer athleteCount;
    @JsonProperty("photo_count")
    private Integer photoCount;
    @JsonProperty("map")
    private StravaMap stravaMap;
    @JsonProperty("trainer")
    private Boolean trainer;
    @JsonProperty("commute")
    private Boolean commute;
    @JsonProperty("manual")
    private Boolean manual;
    @JsonProperty("private")
    private Boolean _private;
    @JsonProperty("flagged")
    private Boolean flagged;
    @JsonProperty("gear_id")
    private String gearId;
    @JsonProperty("from_accepted_tag")
    private Boolean fromAcceptedTag;
    @JsonProperty("average_speed")
    private Double averageSpeed;
    @JsonProperty("max_speed")
    private Double maxSpeed;
    @JsonProperty("average_watts")
    private Double averageWatts;
    @JsonProperty("kilojoules")
    private Double kilojoules;
    @JsonProperty("device_watts")
    private Boolean deviceWatts;
    @JsonProperty("has_heartrate")
    private Boolean hasHeartrate;
    @JsonProperty("average_heartrate")
    private Double averageHeartrate;
    @JsonProperty("max_heartrate")
    private Integer maxHeartrate;
    @JsonProperty("elev_high")
    private Integer elevHigh;
    @JsonProperty("elev_low")
    private Integer elevLow;
    @JsonProperty("pr_count")
    private Integer prCount;
    @JsonProperty("total_photo_count")
    private Integer totalPhotoCount;
    @JsonProperty("has_kudoed")
    private Boolean hasKudoed;
    @JsonProperty("workout_type")
    private Integer workoutType;
    @JsonProperty("suffer_score")
    private Integer sufferScore;
    @JsonIgnore
    private java.util.Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Long getId()
    {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id)
    {
        this.id = id;
    }

    @JsonProperty("resource_state")
    public Integer getResourceState()
    {
        return resourceState;
    }

    @JsonProperty("resource_state")
    public void setResourceState(Integer resourceState)
    {
        this.resourceState = resourceState;
    }

    @JsonProperty("external_id")
    public String getExternalId()
    {
        return externalId;
    }

    @JsonProperty("external_id")
    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    @JsonProperty("upload_id")
    public Long getUploadId()
    {
        return uploadId;
    }

    @JsonProperty("upload_id")
    public void setUploadId(Long uploadId)
    {
        this.uploadId = uploadId;
    }

    @JsonProperty("athlete")
    public StravaAthlete getAthlete()
    {
        return athlete;
    }

    @JsonProperty("athlete")
    public void setAthlete(StravaAthlete athlete)
    {
        this.athlete = athlete;
    }

    @JsonProperty("name")
    public String getName()
    {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name)
    {
        this.name = name;
    }

    @JsonProperty("distance")
    public Double getDistance()
    {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Double distance)
    {
        this.distance = distance;
    }

    @JsonProperty("moving_time")
    public Integer getMovingTime()
    {
        return movingTime;
    }

    @JsonProperty("moving_time")
    public void setMovingTime(Integer movingTime)
    {
        this.movingTime = movingTime;
    }

    @JsonProperty("elapsed_time")
    public Integer getElapsedTime()
    {
        return elapsedTime;
    }

    @JsonProperty("elapsed_time")
    public void setElapsedTime(Integer elapsedTime)
    {
        this.elapsedTime = elapsedTime;
    }

    @JsonProperty("total_elevation_gain")
    public Double getTotalElevationGain()
    {
        return totalElevationGain;
    }

    @JsonProperty("total_elevation_gain")
    public void setTotalElevationGain(Double totalElevationGain)
    {
        this.totalElevationGain = totalElevationGain;
    }

    @JsonProperty("type")
    public String getType()
    {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type)
    {
        this.type = type;
    }

    @JsonProperty("start_date")
    public String getStartDate()
    {
        return startDate;
    }

    @JsonProperty("start_date")
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    @JsonProperty("start_date_local")
    public String getStartDateLocal()
    {
        return startDateLocal;
    }

    @JsonProperty("start_date_local")
    public void setStartDateLocal(String startDateLocal)
    {
        this.startDateLocal = startDateLocal;
    }

    @JsonProperty("timezone")
    public String getTimezone()
    {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone)
    {
        this.timezone = timezone;
    }

    @JsonProperty("utc_offset")
    public Integer getUtcOffset()
    {
        return utcOffset;
    }

    @JsonProperty("utc_offset")
    public void setUtcOffset(Integer utcOffset)
    {
        this.utcOffset = utcOffset;
    }

    @JsonProperty("start_latlng")
    public List<Double> getStartLatlng()
    {
        return startLatlng;
    }

    @JsonProperty("start_latlng")
    public void setStartLatlng(List<Double> startLatlng)
    {
        this.startLatlng = startLatlng;
    }

    @JsonProperty("end_latlng")
    public List<Double> getEndLatlng()
    {
        return endLatlng;
    }

    @JsonProperty("end_latlng")
    public void setEndLatlng(List<Double> endLatlng)
    {
        this.endLatlng = endLatlng;
    }

    @JsonProperty("location_city")
    public Object getLocationCity()
    {
        return locationCity;
    }

    @JsonProperty("location_city")
    public void setLocationCity(Object locationCity)
    {
        this.locationCity = locationCity;
    }

    @JsonProperty("location_state")
    public Object getLocationState()
    {
        return locationState;
    }

    @JsonProperty("location_state")
    public void setLocationState(Object locationState)
    {
        this.locationState = locationState;
    }

    @JsonProperty("location_country")
    public String getLocationCountry()
    {
        return locationCountry;
    }

    @JsonProperty("location_country")
    public void setLocationCountry(String locationCountry)
    {
        this.locationCountry = locationCountry;
    }

    @JsonProperty("start_latitude")
    public Double getStartLatitude()
    {
        return startLatitude;
    }

    @JsonProperty("start_latitude")
    public void setStartLatitude(Double startLatitude)
    {
        this.startLatitude = startLatitude;
    }

    @JsonProperty("start_longitude")
    public Double getStartLongitude()
    {
        return startLongitude;
    }

    @JsonProperty("start_longitude")
    public void setStartLongitude(Double startLongitude)
    {
        this.startLongitude = startLongitude;
    }

    @JsonProperty("achievement_count")
    public Integer getAchievementCount()
    {
        return achievementCount;
    }

    @JsonProperty("achievement_count")
    public void setAchievementCount(Integer achievementCount)
    {
        this.achievementCount = achievementCount;
    }

    @JsonProperty("kudos_count")
    public Integer getKudosCount()
    {
        return kudosCount;
    }

    @JsonProperty("kudos_count")
    public void setKudosCount(Integer kudosCount)
    {
        this.kudosCount = kudosCount;
    }

    @JsonProperty("comment_count")
    public Integer getCommentCount()
    {
        return commentCount;
    }

    @JsonProperty("comment_count")
    public void setCommentCount(Integer commentCount)
    {
        this.commentCount = commentCount;
    }

    @JsonProperty("athlete_count")
    public Integer getAthleteCount()
    {
        return athleteCount;
    }

    @JsonProperty("athlete_count")
    public void setAthleteCount(Integer athleteCount)
    {
        this.athleteCount = athleteCount;
    }

    @JsonProperty("photo_count")
    public Integer getPhotoCount()
    {
        return photoCount;
    }

    @JsonProperty("photo_count")
    public void setPhotoCount(Integer photoCount)
    {
        this.photoCount = photoCount;
    }

    @JsonProperty("map")
    public StravaMap getStravaMap()
    {
        return stravaMap;
    }

    @JsonProperty("map")
    public void setStravaMap(StravaMap stravaMap)
    {
        this.stravaMap = stravaMap;
    }

    @JsonProperty("trainer")
    public Boolean getTrainer()
    {
        return trainer;
    }

    @JsonProperty("trainer")
    public void setTrainer(Boolean trainer)
    {
        this.trainer = trainer;
    }

    @JsonProperty("commute")
    public Boolean getCommute()
    {
        return commute;
    }

    @JsonProperty("commute")
    public void setCommute(Boolean commute)
    {
        this.commute = commute;
    }

    @JsonProperty("manual")
    public Boolean getManual()
    {
        return manual;
    }

    @JsonProperty("manual")
    public void setManual(Boolean manual)
    {
        this.manual = manual;
    }

    @JsonProperty("private")
    public Boolean getPrivate()
    {
        return _private;
    }

    @JsonProperty("private")
    public void setPrivate(Boolean _private)
    {
        this._private = _private;
    }

    @JsonProperty("flagged")
    public Boolean getFlagged()
    {
        return flagged;
    }

    @JsonProperty("flagged")
    public void setFlagged(Boolean flagged)
    {
        this.flagged = flagged;
    }

    @JsonProperty("gear_id")
    public String getGearId()
    {
        return gearId;
    }

    @JsonProperty("gear_id")
    public void setGearId(String gearId)
    {
        this.gearId = gearId;
    }

    @JsonProperty("from_accepted_tag")
    public Boolean getFromAcceptedTag()
    {
        return fromAcceptedTag;
    }

    @JsonProperty("from_accepted_tag")
    public void setFromAcceptedTag(Boolean fromAcceptedTag)
    {
        this.fromAcceptedTag = fromAcceptedTag;
    }

    @JsonProperty("average_speed")
    public Double getAverageSpeed()
    {
        return averageSpeed;
    }

    @JsonProperty("average_speed")
    public void setAverageSpeed(Double averageSpeed)
    {
        this.averageSpeed = averageSpeed;
    }

    @JsonProperty("max_speed")
    public Double getMaxSpeed()
    {
        return maxSpeed;
    }

    @JsonProperty("max_speed")
    public void setMaxSpeed(Double maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }

    @JsonProperty("average_watts")
    public Double getAverageWatts()
    {
        return averageWatts;
    }

    @JsonProperty("average_watts")
    public void setAverageWatts(Double averageWatts)
    {
        this.averageWatts = averageWatts;
    }

    @JsonProperty("kilojoules")
    public Double getKilojoules()
    {
        return kilojoules;
    }

    @JsonProperty("kilojoules")
    public void setKilojoules(Double kilojoules)
    {
        this.kilojoules = kilojoules;
    }

    @JsonProperty("device_watts")
    public Boolean getDeviceWatts()
    {
        return deviceWatts;
    }

    @JsonProperty("device_watts")
    public void setDeviceWatts(Boolean deviceWatts)
    {
        this.deviceWatts = deviceWatts;
    }

    @JsonProperty("has_heartrate")
    public Boolean getHasHeartrate()
    {
        return hasHeartrate;
    }

    @JsonProperty("has_heartrate")
    public void setHasHeartrate(Boolean hasHeartrate)
    {
        this.hasHeartrate = hasHeartrate;
    }

    @JsonProperty("average_heartrate")
    public Double getAverageHeartrate()
    {
        return averageHeartrate;
    }

    @JsonProperty("average_heartrate")
    public void setAverageHeartrate(Double averageHeartrate)
    {
        this.averageHeartrate = averageHeartrate;
    }

    @JsonProperty("max_heartrate")
    public Integer getMaxHeartrate()
    {
        return maxHeartrate;
    }

    @JsonProperty("max_heartrate")
    public void setMaxHeartrate(Integer maxHeartrate)
    {
        this.maxHeartrate = maxHeartrate;
    }

    @JsonProperty("elev_high")
    public Integer getElevHigh()
    {
        return elevHigh;
    }

    @JsonProperty("elev_high")
    public void setElevHigh(Integer elevHigh)
    {
        this.elevHigh = elevHigh;
    }

    @JsonProperty("elev_low")
    public Integer getElevLow()
    {
        return elevLow;
    }

    @JsonProperty("elev_low")
    public void setElevLow(Integer elevLow)
    {
        this.elevLow = elevLow;
    }

    @JsonProperty("pr_count")
    public Integer getPrCount()
    {
        return prCount;
    }

    @JsonProperty("pr_count")
    public void setPrCount(Integer prCount)
    {
        this.prCount = prCount;
    }

    @JsonProperty("total_photo_count")
    public Integer getTotalPhotoCount()
    {
        return totalPhotoCount;
    }

    @JsonProperty("total_photo_count")
    public void setTotalPhotoCount(Integer totalPhotoCount)
    {
        this.totalPhotoCount = totalPhotoCount;
    }

    @JsonProperty("has_kudoed")
    public Boolean getHasKudoed()
    {
        return hasKudoed;
    }

    @JsonProperty("has_kudoed")
    public void setHasKudoed(Boolean hasKudoed)
    {
        this.hasKudoed = hasKudoed;
    }

    @JsonProperty("workout_type")
    public Integer getWorkoutType()
    {
        return workoutType;
    }

    @JsonProperty("workout_type")
    public void setWorkoutType(Integer workoutType)
    {
        this.workoutType = workoutType;
    }

    @JsonProperty("suffer_score")
    public Integer getSufferScore()
    {
        return sufferScore;
    }

    @JsonProperty("suffer_score")
    public void setSufferScore(Integer sufferScore)
    {
        this.sufferScore = sufferScore;
    }

    @JsonAnyGetter
    public java.util.Map<String, Object> getAdditionalProperties()
    {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
    }

}
