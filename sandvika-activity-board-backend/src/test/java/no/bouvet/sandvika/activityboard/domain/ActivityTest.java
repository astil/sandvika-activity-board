package no.bouvet.sandvika.activityboard.domain;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

public class ActivityTest
{

    @Test
    public void testUnmarshallingOfJsonResponse() throws IOException
    {
        URL url = this.getClass().getResource("/activities.json");

        Activity[] activities = new ObjectMapper()
            .readerFor(Activity[].class)
            .readValue(url);
        System.out.println(activities[0]);
    }


}