package no.bouvet.sandvika.activityboard.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.strava.StravaSlurper;

@RestController
public class AdminController
{
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    StravaSlurper stravaSlurper;

    //TODO: This is just for testing, should be more secure.
    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/deleteAllFromDb", method = RequestMethod.GET)
    public void deleteAllActivitiesFromDb()
    {
        activityRepository.deleteAll();
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/refresh", method = RequestMethod.GET)
    public void refreshActivities()
    {
        stravaSlurper.updateActivities();
    }


}
