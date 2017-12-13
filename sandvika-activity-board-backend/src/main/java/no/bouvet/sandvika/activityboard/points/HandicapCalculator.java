package no.bouvet.sandvika.activityboard.points;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.Handicap;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.utils.DateUtil;
import no.bouvet.sandvika.activityboard.utils.Utils;

@Component
public class HandicapCalculator
{
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int BASE_HOURS = 6;
    private static Logger log = LoggerFactory.getLogger(HandicapCalculator.class);

    private final AthleteRepository athleteRepository;
    private final ActivityRepository activityRepository;

    public HandicapCalculator(AthleteRepository athleteRepository, ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        this.athleteRepository = athleteRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateHandicapForAllAthletes()
    {
        List<Athlete> athletes = athleteRepository.findAll();

        for (Athlete athlete : athletes)
        {
            Handicap hc = new Handicap(calculateHandicapForAthlete(athlete), new Date());
            log.info("Adding new HC for " + athlete.getLastName() + " " + hc.toString());
            athlete.getHandicapList().add(hc);
        }
        athleteRepository.save(athletes);
    }

    public void updateHandicapForAllAthletesTheLast300Days()
    {
        deleteHandicapsForAllAthlets();
        IntStream.range(0, 300).forEach(i ->
            updateHandicapForAllAthletesForDate(DateUtil.getDateDaysAgo(i)));
    }

    public void updateHistoricalHandicapForAllAthletes()
    {
        deleteHandicapsForAllAthlets();
        IntStream.range(0, 9999).forEach(i ->
            updateHandicapForAllAthletesForDate(DateUtil.getDateDaysAgo(i)));
    }

    public double getHandicapForActivity(Activity activity)
    {
        Athlete athlete = athleteRepository.findById(activity.getAthleteId());
        if (athlete == null || athlete.getHandicapList().isEmpty())
        {
            return 1;
        } else
        {
            return athlete.getHandicapForDate(activity.getStartDateLocal());
        }
    }

    private void deleteHandicapsForAllAthlets()
    {
        List<Athlete> athletes = athleteRepository.findAll();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -9999);
        for (Athlete athlete : athletes)
        {
            athlete.setHandicapList(athlete.getHandicapList()
                .stream()
                .filter(h -> h.getTimestamp().before(calendar.getTime()))
                .collect(Collectors.toList()));
        }
        athleteRepository.save(athletes);
    }

    private void updateHandicapForAllAthletesForDate(Date dateDaysAgo)
    {
        List<Athlete> athletes = athleteRepository.findAll();

        for (Athlete athlete : athletes)
        {
            Handicap hc = new Handicap(calculateHandicapForAthlete(athlete, dateDaysAgo), dateDaysAgo);
            log.info("Adding new HC for " + athlete.getLastName() + " " + hc.toString());
            athlete.getHandicapList().add(hc);
        }
        athleteRepository.save(athletes);

    }

    protected double calculateHandicapForAthlete(Athlete athlete, Date dateDaysAgo)
    {
        double activeHours = getActiveHoursByDaysAndDateAndAthlete(60, dateDaysAgo, athlete);
        if (activeHours <= 1)
        {
            return 6;
        } else
        {
            return Utils.scaledDouble(6 - ((Math.log10(activeHours))*3), 3);
        }
    }

    private double getActiveHoursByDaysAndDateAndAthlete(int days, Date dateDaysAgo, Athlete athlete)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDaysAgo);
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        List<Activity> activities = activityRepository.findByStartDateLocalBetweenAndAthleteId(calendar.getTime(), dateDaysAgo, athlete.getId());
        return activities.stream()
            .mapToInt(Activity::getMovingTimeInSeconds)
            .sum() / SECONDS_IN_HOUR;
    }

    private double calculateHandicapForAthlete(Athlete athlete)
    {
        double activeHours = getActiveHoursByDaysAndAthlete(60, athlete);
        if (activeHours <= 1)
        {
            return 6;
        } else
        {
            return Utils.scaledDouble(6 - (Math.log10(activeHours)*3), 3);
        }
    }

    private double getActiveHoursByDaysAndAthlete(int days, Athlete athlete)
    {
        List<Activity> activities = activityRepository.findByStartDateLocalBetweenAndAthleteId(DateUtil.getDateDaysAgo(days), new Date(), athlete.getId());
        return activities.stream()
            .mapToInt(Activity::getMovingTimeInSeconds)
            .sum() / SECONDS_IN_HOUR;
    }


    public void updateHandicapForAthleteTheLast300Days(int athleteId)
    {
        deleteHandicapsForAthleteTheLast300Days(athleteId);
        IntStream.range(0, 300).forEach(i ->
            updateHandicapForAthleteForDate(athleteId, DateUtil.getDateDaysAgo(i)));
    }

    private void updateHandicapForAthleteForDate(int athleteId, Date dateDaysAgo)
    {
        Athlete athlete = athleteRepository.findById(athleteId);
        Handicap hc = new Handicap(calculateHandicapForAthlete(athlete, dateDaysAgo), dateDaysAgo);
        log.info("Adding new HC for " + athlete.getLastName() + " " + hc.toString());
        athlete.getHandicapList().add(hc);
        athleteRepository.save(athlete);
    }

    private void deleteHandicapsForAthleteTheLast300Days(int athleteId)
    {
        Athlete athlete = athleteRepository.findById(athleteId);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -300);
        athlete.setHandicapList(athlete.getHandicapList()
            .stream()
            .filter(h -> h.getTimestamp().before(calendar.getTime()))
            .collect(Collectors.toList()));
        athleteRepository.save(athlete);
    }
}
