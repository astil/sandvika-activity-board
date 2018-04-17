package no.bouvet.sandvika.activityboard.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "distance",
        "elapsed_time",
        "elevation_difference",
        "moving_time",
        "split",
        "average_speed",
        "pace_zone"
})
public class StravaSplitMetric {

        @JsonProperty("distance")
        private Double distance;
        @JsonProperty("elapsed_time")
        private Integer elapsedTime;
        @JsonProperty("elevation_difference")
        private Double elevationDifference;
        @JsonProperty("moving_time")
        private Integer movingTime;
        @JsonProperty("split")
        private Integer split;
        @JsonProperty("average_speed")
        private Double averageSpeed;
        @JsonProperty("pace_zone")
        private Integer paceZone;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("distance")
        public Double getDistance() {
            return distance;
        }

        @JsonProperty("distance")
        public void setDistance(Double distance) {
            this.distance = distance;
        }

        @JsonProperty("elapsed_time")
        public Integer getElapsedTime() {
            return elapsedTime;
        }

        @JsonProperty("elapsed_time")
        public void setElapsedTime(Integer elapsedTime) {
            this.elapsedTime = elapsedTime;
        }

        @JsonProperty("elevation_difference")
        public Double getElevationDifference() {
            return elevationDifference;
        }

        @JsonProperty("elevation_difference")
        public void setElevationDifference(Double elevationDifference) {
            this.elevationDifference = elevationDifference;
        }

        @JsonProperty("moving_time")
        public Integer getMovingTime() {
            return movingTime;
        }

        @JsonProperty("moving_time")
        public void setMovingTime(Integer movingTime) {
            this.movingTime = movingTime;
        }

        @JsonProperty("split")
        public Integer getSplit() {
            return split;
        }

        @JsonProperty("split")
        public void setSplit(Integer split) {
            this.split = split;
        }

        @JsonProperty("average_speed")
        public Double getAverageSpeed() {
            return averageSpeed;
        }

        @JsonProperty("average_speed")
        public void setAverageSpeed(Double averageSpeed) {
            this.averageSpeed = averageSpeed;
        }

        @JsonProperty("pace_zone")
        public Integer getPaceZone() {
            return paceZone;
        }

        @JsonProperty("pace_zone")
        public void setPaceZone(Integer paceZone) {
            this.paceZone = paceZone;
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

