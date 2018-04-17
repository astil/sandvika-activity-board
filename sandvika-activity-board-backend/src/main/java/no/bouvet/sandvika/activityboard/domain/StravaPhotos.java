package no.bouvet.sandvika.activityboard.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "primary",
        "use_primary_photo",
        "count"
})

public class StravaPhotos {

    @JsonProperty("primary")
    private StravaPrimaryPhoto primary;
    @JsonProperty("use_primary_photo")
    private Boolean useStravaPrimaryPhotoPhoto;
    @JsonProperty("count")
    private Integer count;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("primary")
    public StravaPrimaryPhoto getStravaPrimaryPhoto() {
        return primary;
    }

    @JsonProperty("primary")
    public void setStravaPrimaryPhoto(StravaPrimaryPhoto primary) {
        this.primary = primary;
    }

    @JsonProperty("use_primary_photo")
    public Boolean getUseStravaPrimaryPhotoPhoto() {
        return useStravaPrimaryPhotoPhoto;
    }

    @JsonProperty("use_primary_photo")
    public void setUseStravaPrimaryPhotoPhoto(Boolean useStravaPrimaryPhotoPhoto) {
        this.useStravaPrimaryPhotoPhoto = useStravaPrimaryPhotoPhoto;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
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

