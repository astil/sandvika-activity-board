package no.bouvet.sandvika.activityboard.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "unique_id",
        "urls",
        "source"
})

public class StravaPrimaryPhoto {

    @JsonProperty("id")
    private Object id;
    @JsonProperty("unique_id")
    private String uniqueId;
    @JsonProperty("urls")
    private StravaUrls urls;
    @JsonProperty("source")
    private Integer source;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Object getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Object id) {
        this.id = id;
    }

    @JsonProperty("unique_id")
    public String getUniqueId() {
        return uniqueId;
    }

    @JsonProperty("unique_id")
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @JsonProperty("urls")
    public StravaUrls getStravaUrls() {
        return urls;
    }

    @JsonProperty("urls")
    public void setStravaUrls(StravaUrls urls) {
        this.urls = urls;
    }

    @JsonProperty("source")
    public Integer getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(Integer source) {
        this.source = source;
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
