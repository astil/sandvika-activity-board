package no.bouvet.sandvika.activityboard.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.Handicap;
import no.bouvet.sandvika.activityboard.domain.LeaderboardEntry;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.strava.StravaSlurper;
import no.bouvet.sandvika.activityboard.utils.DateUtil;

//import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class ActivityController
{
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    StravaSlurper stravaSlurper;

    //TODO: This is just for testing, should be more secure.
//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/deleteAllFromDb", method = RequestMethod.GET)
    public void deleteDb()
    {
        activityRepository.deleteAll();
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{lastName}/activities", method = RequestMethod.GET)
    public List<Activity> getUserActivities(@PathVariable("lastName") String lastName)
    {
        return activityRepository.findByAthleteLastName(lastName);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/month/", method = RequestMethod.GET)
    public List<Activity> getUserActivities()
    {
        return activityRepository.findByStartDateLocalAfter(DateUtil.addHours(DateUtil.firstDayOfCurrentWeek(), -24));
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/leaderboard/week/points", method = RequestMethod.GET)
    public List<LeaderboardEntry> getLeaderboardWeek()
    {
        List<Activity> activityList = activityRepository.findByStartDateLocalAfter(DateUtil.addHours(DateUtil.firstDayOfCurrentWeek(), -24));
        return getLeaderboardEntries(activityList);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/leaderboard/month/points", method = RequestMethod.GET)
    public List<LeaderboardEntry> getLeaderboardMonth()
    {
        List<Activity> activityList = activityRepository.findByStartDateLocalAfter(DateUtil.firstDayOfCurrentMonth());
        return getLeaderboardEntries(activityList);
    }

    @RequestMapping(value = "/leaderboard/month/points/{activityTypeName}", method = RequestMethod.GET)
    public List<LeaderboardEntry> getLeaderboardMonth(@PathVariable("activityTypeName") String activityTypeName)
    {
        List<Activity> activityList = activityRepository.findByStartDateLocalAfterAndType(DateUtil.firstDayOfCurrentMonth(), activityTypeName);
        return getLeaderboardEntries(activityList);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete", method = RequestMethod.PUT)
    public Athlete updateAthlete(@RequestBody Athlete request)
    {
        return athleteRepository.save(request);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{lastName}/handicap", method = RequestMethod.PUT)
    public Athlete updateAthlete(@PathVariable("lastName") String lastName, @RequestBody Handicap handicap)
    {
        Athlete athlete = athleteRepository.findByLastName(lastName);
        if (athlete == null)
        {
            athlete = new Athlete();
            athlete.setLastName(lastName);
        }
        athlete.getHandicapList().add(handicap);
        return athleteRepository.save(athlete);
    }

    //    @CrossOrigin(origins = "*")
//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete", method = RequestMethod.GET)
    public List<Athlete> getAllAthletes()
    {
        return athleteRepository.findAll();
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{lastName}", method = RequestMethod.GET)
    public Athlete getAllAthletes(@PathVariable("lastName") String lastName)
    {
        return athleteRepository.findByLastName(lastName);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/refresh", method = RequestMethod.GET)
    public void refreshActivities()
    {
        stravaSlurper.updateActivities();
    }

    private List<LeaderboardEntry> getLeaderboardEntries(List<Activity> activityList)
    {
        Map<String, LeaderboardEntry> entries = new HashMap<>();
        for (Activity activity : activityList)
        {
            if (!entries.containsKey(activity.getAthleteLastName()))
            {
                LeaderboardEntry entry = new LeaderboardEntry(activity.getAthleteLastName(), activity.getPoints());
                entry.setAthleteFirstName(activity.getAthletefirstName());
                entry.setNumberOfActivities(1);

                entry.setHandicap(getHandicapForActivity(activity));
                entry.setKilometers(activity.getDistanceInMeters() / 1000);
                entry.setMinutes(activity.getMovingTimeInSeconds() / 60);
                entry.setLastActivityDate(activity.getStartDateLocal());
                entries.put(entry.getAthleteLastName(), entry);

            } else
            {
                LeaderboardEntry entry = entries.get(activity.getAthleteLastName());
                entry.setNumberOfActivities(entry.getNumberOfActivities() + 1);
                entry.setKilometers(entry.getKilometers() + (activity.getDistanceInMeters() / 1000));
                entry.setMinutes(Double.valueOf(entry.getMinutes() + (activity.getMovingTimeInSeconds() / 60)).intValue());
                entry.setPoints(entry.getPoints() + activity.getPoints());
                if (entry.getLastActivityDate().before(activity.getStartDateLocal()))
                {
                    entry.setLastActivityDate(activity.getStartDateLocal());
                }
            }
        }

        return new ArrayList<>(entries.values())
            .stream()
            .sorted(Comparator.comparingDouble(LeaderboardEntry::getPoints).reversed())
            .collect(Collectors.toList());
    }

    private double getHandicapForActivity(Activity activity)
    {
        Athlete athlete = athleteRepository.findByLastName(activity.getAthleteLastName());
        if (athlete == null || athlete.getHandicapList().isEmpty())
        {
            return 1;
        } else
        {
            return athlete.getHandicapForDate(activity.getStartDateLocal());
        }
    }


}
