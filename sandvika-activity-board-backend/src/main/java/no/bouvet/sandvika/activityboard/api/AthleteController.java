package no.bouvet.sandvika.activityboard.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import no.bouvet.sandvika.activityboard.domain.AthleteStats;
import no.bouvet.sandvika.activityboard.domain.PeriodType;
import no.bouvet.sandvika.activityboard.domain.StravaAthlete;
import no.bouvet.sandvika.activityboard.domain.StravaToken;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import no.bouvet.sandvika.activityboard.utils.ActiveHoursUtil;
import no.bouvet.sandvika.activityboard.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;

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
    //@CrossOrigin(value = "*")
    public Athlete tokenRegistration(@RequestParam String token) {
        return athleteRepository.findOneByToken(token);
    }

    @RequestMapping(value = "/athlete/login/token-registration", method = RequestMethod.GET)
    //@CrossOrigin(value = "*")
    public Athlete tokenRegistration(@RequestParam(required = false) String state, @RequestParam String code, HttpServletResponse response)
    {
        StravaToken stravaToken = restTemplate.postForEntity("https://www.strava.com/oauth/token?client_id=3034&client_secret=506d1d0ed30af56b74a458a26419dd6ead8e910d&code=" + code, "", StravaToken.class).getBody();

        Athlete athlete = null;
        if (Objects.nonNull(stravaToken)) {
            StravaAthlete stravaAthlete = stravaToken.getStravaAthlete();
            athlete = athleteRepository.findById(stravaAthlete.getId());
            if (athlete == null)
            {
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
