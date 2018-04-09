package no.bouvet.sandvika.activityboard.api;

import java.util.ArrayList;
import java.util.List;

import no.bouvet.sandvika.activityboard.domain.AthleteStats;
import no.bouvet.sandvika.activityboard.domain.PeriodType;
import no.bouvet.sandvika.activityboard.utils.ActiveHoursUtil;
import no.bouvet.sandvika.activityboard.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{id}/activities", method = RequestMethod.GET)
    public List<Activity> getUserActivities(@PathVariable("id") int id) {
        return activityRepository.findByAthleteId(id);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete", method = RequestMethod.GET)
    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    //    @CrossOrigin(origins = "*")
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
//        athleteStats.setActiveHoursThisCompetition(activeHoursUtil.getActiveHoursByDaysAndAthlete(DateUtil.getDaysSinceDate()).getDaysSinceStart(), athlete));
//        athleteStats.setActiveHoursThisWeek(activeHoursUtil.getActiveHoursByDaysAndAthlete(DateUtil.getCurrentPeriod(PeriodType.WEEK, clubId).getDaysSinceStart(), athlete));
//        athleteStats.setActiveHoursThisMonth(activeHoursUtil.getActiveHoursByDaysAndAthlete(DateUtil.getCurrentPeriod(PeriodType.MONTH, clubId).getDaysSinceStart(), athlete));
        return athleteStats;
    }


}
