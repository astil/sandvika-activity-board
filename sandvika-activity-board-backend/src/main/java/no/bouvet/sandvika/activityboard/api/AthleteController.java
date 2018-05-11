package no.bouvet.sandvika.activityboard.api;

import no.bouvet.sandvika.activityboard.domain.*;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.utils.ActiveHoursUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class AthleteController {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    ActiveHoursUtil activeHoursUtil;

    @Autowired
    RestTemplate restTemplate;

    //@CrossOrigin("*")
    @RequestMapping(value = "/athlete/{id}/activities", method = RequestMethod.GET)
    public List<Activity> getUserActivities(@PathVariable("id") int id) {
        return activityRepository.findByAthleteId(id);
    }

    @RequestMapping(value = "/athlete/login", method = RequestMethod.GET)
//    @CrossOrigin(value = "*")
    public Athlete tokenRegistration(@RequestParam String token) {
        return athleteRepository.findOneByToken(token);
    }

    @RequestMapping(value = "/athlete/login/token-registration", method = RequestMethod.GET)
//    @CrossOrigin(value = "*")
    public Athlete tokenRegistration(@RequestParam(required = false) String state, @RequestParam String code, HttpServletResponse response) {
        StravaToken stravaToken = restTemplate.postForEntity("https://www.strava.com/oauth/token?client_id=3034&client_secret=506d1d0ed30af56b74a458a26419dd6ead8e910d&code=" + code, "", StravaToken.class).getBody();

        Athlete athlete = null;
        if (Objects.nonNull(stravaToken)) {
            StravaAthlete stravaAthlete = stravaToken.getStravaAthlete();
            athlete = athleteRepository.findById(stravaAthlete.getId());
            if (athlete == null || athlete.getLastName() == null || athlete.getProfile() == null) {
                Athlete newAthlete = new Athlete();
                newAthlete.setId(stravaAthlete.getId());
                newAthlete.setFirstName(stravaAthlete.getFirstname());
                newAthlete.setLastName(stravaAthlete.getLastname());
                newAthlete.setProfile(stravaAthlete.getProfile());

                athlete = newAthlete;
            }
            athlete.setToken(stravaToken.getAccessToken());

            athleteRepository.save(athlete);
        } else {
            throw new IllegalArgumentException("Strava token not found!");
        }

        return athlete;
    }

    @RequestMapping(value = "/athlete", method = RequestMethod.GET)
    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    @RequestMapping(value = "/athlete/{id}", method = RequestMethod.GET)
    public Athlete getAthlete(@PathVariable("id") int id) {
        return athleteRepository.findById(id);
    }

    @RequestMapping(value = "/athlete/stats/{id}", method = RequestMethod.GET)
    public AthleteStats getAthleteStatistics(@PathVariable("id") int id) {
        return createAthleteStats(id);
    }

    @RequestMapping(value = "/athlete/stats/", method = RequestMethod.GET)
    public List<AthleteStats> getAllAthleteStatistics() {
        List<Athlete> athletes = athleteRepository.findAll();
        List<AthleteStats> athleteStats = new ArrayList<>();
        for (Athlete athlete : athletes) {
            athleteStats.add(createAthleteStats(athlete.getId()));
        }
        return athleteStats;
    }

    private AthleteStats createAthleteStats(int id) {
        Athlete athlete = athleteRepository.findById(id);
        AthleteStats athleteStats = new AthleteStats();
        athleteStats.setId(id);
        athleteStats.setName(athlete.getFirstName() + " " + athlete.getLastName());
        athleteStats.setHc(athlete.getCurrentHandicap());
        athleteStats.setActiveHoursHcPeriod(activeHoursUtil.getActiveHoursByDaysAndAthlete(30, athlete));
        return athleteStats;
    }


}
