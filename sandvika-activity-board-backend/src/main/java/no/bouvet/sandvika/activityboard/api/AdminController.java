package no.bouvet.sandvika.activityboard.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.Badge;
import no.bouvet.sandvika.activityboard.points.HandicapCalculator;
import no.bouvet.sandvika.activityboard.points.PointsCalculator;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.repository.BadgeRepository;
import no.bouvet.sandvika.activityboard.strava.StravaSlurper;

@RestController
public class AdminController
{
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminController.class);
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    AthleteRepository athleteRepository;
    @Autowired
    StravaSlurper stravaSlurper;
    @Autowired
    HandicapCalculator handicapCalculator;

    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    public void reloadUsersAndPoint()
    {
        athleteRepository.deleteAll();

        List<Activity> allActivities = activityRepository.findAll();
        allActivities
            .stream()
            .filter(a -> a.getAthleteId() != null && !athleteRepository.exists(a.getAthleteId()))
            .forEach(this::saveAthlete);

        allActivities = activityRepository.findAll();
        for (Activity activity : allActivities)
        {
            if (activity.getAthleteId() == null || activity.getAthleteId() == 0)
            {
                Athlete athlete = athleteRepository.findOneByLastNameAndFirstName(activity.getAthleteLastName(), activity.getAthletefirstName());
                if (athlete != null)
                {
                    activity.setAthleteId(athlete.getId());
                    activityRepository.save(activity);
                } else
                {
                    log.info("Activity missing athlteteId and no Athlete found: " + activity.toString());

                }
            }
        }
        updateHistoricHandicapForAllAthletes();
    }

    private void saveAthlete(Activity activity)
    {
        Athlete athlete = new Athlete();
        athlete.setLastName(activity.getAthleteLastName());
        athlete.setFirstName(activity.getAthletefirstName());
        athlete.setId(activity.getAthleteId());
        athleteRepository.save(athlete);
    }


    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/refresh", method = RequestMethod.GET)
    public void refreshActivities()
    {
        stravaSlurper.updateActivities();
    }

    @RequestMapping(value = "/activities/{id}", method = RequestMethod.PUT)
    public void addActivity(@PathVariable("id") int id, @RequestBody Activity activity)
    {
        activity.setHandicap(handicapCalculator.getHandicapForActivity(activity));
        activity.setPoints(PointsCalculator.getPointsForActivity(activity, activity.getHandicap()));
        activityRepository.save(activity);
    }

    @RequestMapping(value = "/athlete/all/updateHistoricHandicap", method = RequestMethod.GET)
    public void updateHistoricHandicapForAllAthletes()
    {
        handicapCalculator.updateHandicapForAllAthletesTheLast300Days();
        List<Activity> activities = activityRepository.findAll();
        for (Activity activity : activities)
        {
            activity.setHandicap(handicapCalculator.getHandicapForActivity(activity));
            activity.setPoints(PointsCalculator.getPointsForActivity(activity, activity.getHandicap()));
            activityRepository.save(activity);
        }
    }

    @RequestMapping(value = "/activities/{id}", method = RequestMethod.DELETE)
    public void deleteActivity(@PathVariable("id") int id)
    {
        activityRepository.delete(id);
    }


    @RequestMapping(value = "/badges/", method = RequestMethod.POST)
    public void addBadge(@RequestBody Badge badge)
    {
        badgeRepository.save(badge);
    }


}
