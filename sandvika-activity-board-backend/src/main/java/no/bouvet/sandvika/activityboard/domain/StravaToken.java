package no.bouvet.sandvika.activityboard.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "access_token",
        "token_type",
        "athlete"
})
public class StravaToken
{
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("athlete")
    private StravaAthlete stravaAthlete;

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }

    public StravaAthlete getStravaAthlete()
    {
        return stravaAthlete;
    }

    public void setStravaAthlete(StravaAthlete stravaAthlete)
    {
        this.stravaAthlete = stravaAthlete;
    }
}
