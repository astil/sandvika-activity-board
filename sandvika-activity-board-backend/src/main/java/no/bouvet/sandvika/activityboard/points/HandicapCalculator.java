package no.bouvet.sandvika.activityboard.points;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ActivityRepository activityRepository;

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

    public void updateHandicapForAllAthletesTheLast40Days()
    {
        IntStream.range(0, 40).forEach(i ->
        {
            deleteHandicapsForAllAthletsTheLast40Days();
            updateHandicapForAllAthletesForDate(DateUtil.getDateDaysAgo(i));
        });
    }

    private void deleteHandicapsForAllAthletsTheLast40Days()
    {
        List<Athlete> athletes = athleteRepository.findAll();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -40);
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

    private double calculateHandicapForAthlete(Athlete athlete, Date dateDaysAgo)
    {
        double activeHours = getActiveHoursByDaysAndDateAndAthlete(30, dateDaysAgo, athlete);
        if (activeHours <= 4)
        {
            return 6;
        } else
        {
            return Utils.scaledDouble(BASE_HOURS / (activeHours / 4), 3);
        }
    }

    private double getActiveHoursByDaysAndDateAndAthlete(int days, Date dateDaysAgo, Athlete athlete)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDaysAgo);
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        List<Activity> activities = activityRepository.findByStartDateLocalBetweenAndAthleteLastName(calendar.getTime(), dateDaysAgo, athlete.getLastName());
        return activities.stream()
            .mapToInt(Activity::getMovingTimeInSeconds)
            .sum() / SECONDS_IN_HOUR;
    }

    public List<Handicap> calculateHistoricHandicapForAthlete(Athlete athlete)
    {
        List<Handicap> handicaps = new ArrayList<>();
        IntStream.range(0, 40).forEach(i ->
        {
            deleteHandicapsForAthleteTheLast40Days(athlete);
            handicaps.add(createHandicapForAthleteForDate(athlete, DateUtil.getDateDaysAgo(i)));
        });
        return handicaps;
    }

    private Handicap createHandicapForAthleteForDate(Athlete athlete, Date dateDaysAgo)
    {
        return new Handicap(calculateHandicapForAthlete(athlete, dateDaysAgo), dateDaysAgo);
    }

    private void deleteHandicapsForAthleteTheLast40Days(Athlete athlete)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -40);
        athlete.setHandicapList(athlete.getHandicapList()
            .stream()
            .filter(h -> h.getTimestamp().before(calendar.getTime()))
            .collect(Collectors.toList()));
        athleteRepository.save(athlete);
    }

    public double calculateHandicapForAthlete(Athlete athlete)
    {
        double activeHours = getActiveHoursByDaysAndAthlete(30, athlete);
        if (activeHours <= 4)
        {
            return 6;
        } else
        {
            return Utils.scaledDouble(BASE_HOURS / (activeHours / 4), 3);
        }
    }

    private double getActiveHoursByDaysAndAthlete(int days, Athlete athlete)
    {
        List<Activity> activities = activityRepository.findByStartDateLocalBetweenAndAthleteLastName(DateUtil.getDateDaysAgo(days), new Date(), athlete.getLastName());
        return activities.stream()
            .mapToInt(Activity::getMovingTimeInSeconds)
            .sum() / SECONDS_IN_HOUR;
    }


}
