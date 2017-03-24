package no.bouvet.sandvika.activityboard.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.bouvet.sandvika.activityboard.points.HandicapCalculator;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.strava.StravaSlurper;

@RestController
public class AdminController
{
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    StravaSlurper stravaSlurper;

    @Autowired
    HandicapCalculator handicapCalculator;

    @Value("${ssb.test}")
    String test;


    //TODO: This is just for testing, should be more secure.
    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/deleteAllFromDb", method = RequestMethod.GET)
    public void deleteAllActivitiesFromDb()
    {
        activityRepository.deleteAll();
    }

    @RequestMapping(value = "/athlete/{lastName}/deleteFromDb", method = RequestMethod.GET)
    public void deleteAthleteFromDb(@PathVariable("lastName") String lastName)
    {
        athleteRepository.deleteByLastName(lastName);
    }

    @RequestMapping(value = "/athlete/all/deleteFromDb", method = RequestMethod.GET)
    public void deleteAthleteFromDb()
    {
        athleteRepository.deleteAll();
    }


    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/refresh", method = RequestMethod.GET)
    public void refreshActivities()
    {
        stravaSlurper.updateActivities();
    }

    @RequestMapping(value = "/athlete/all/updateHandicap", method = RequestMethod.GET)
    public void updateHandicapForAllAthletes()
    {
        handicapCalculator.updateHandicapForAllAthletes();
    }

    @RequestMapping(value = "/athlete/all/updateHistoricHandicap", method = RequestMethod.GET)
    public void updateHistoricHandicapForAllAthletes()
    {
        handicapCalculator.updateHandicapForAllAthletesTheLast40Days();
    }

    @RequestMapping(value = "/activities/{id}", method = RequestMethod.DELETE)
    public void deleteActivity(@PathVariable("id") int id)
    {
        activityRepository.delete(id);
    }

    @RequestMapping(value = "/property", method = RequestMethod.GET)
    public String getProperty()
    {
        return test;
    }

}
