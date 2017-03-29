package no.bouvet.sandvika.activityboard.api;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.bouvet.sandvika.activityboard.points.HandicapCalculator;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.strava.StravaSlurper;

import java.util.List;
import java.util.logging.Logger;

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

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminController.class);

    //TODO: This is just for testing, should be more secure.
    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/deleteAllFromDb", method = RequestMethod.GET)
    public void deleteAllActivitiesFromDb()
    {
        activityRepository.deleteAll();
    }

    @RequestMapping(value = "/athletes/reset", method = RequestMethod.GET)
    public void resetAthletes() {
        athleteRepository.deleteAll();
        refreshActivities();
        updateHistoricHandicapForAllAthletes();
    }

    @RequestMapping(value = "/athlete/{id}/deleteFromDb", method = RequestMethod.GET)
    public void deleteAthleteFromDb(@PathVariable("id") int id)
    {
        athleteRepository.deleteById(id);
    }

    @RequestMapping(value = "/athlete/migrate", method = RequestMethod.GET)
    public void migrateUsers()
    {
        List<Athlete> allAthletes = athleteRepository.findAll();
        for (Athlete athlete : allAthletes) {
            Activity a = activityRepository.findOneByAthleteLastName(athlete.getLastName());
            athlete.setId(a.getAthleteId());
            athleteRepository.save(athlete);
        }
        List<Activity> allActivities = activityRepository.findAll();
        for (Activity activity : allActivities) {
            if (activity.getAthleteId() == null || activity.getAthleteId() == 0) {
                Athlete athlete = athleteRepository.findOneByLastNameAndFirstName(activity.getAthleteLastName(), activity.getAthletefirstName());
                if (athlete != null) {
                    activity.setAthleteId(athlete.getId());
                    activityRepository.save(activity);
                } else {
                    log.info("Activity missin athlteteId and no Athlete found: " + activity.toString());

                }
            }
        }
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
        handicapCalculator.updateHandicapForAllAthletesTheLast100Days();
    }

    @RequestMapping(value = "/activities/{id}", method = RequestMethod.DELETE)
    public void deleteActivity(@PathVariable("id") int id)
    {
        activityRepository.delete(id);
    }

}
