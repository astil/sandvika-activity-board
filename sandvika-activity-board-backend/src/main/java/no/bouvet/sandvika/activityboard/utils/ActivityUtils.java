package no.bouvet.sandvika.activityboard.utils;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Club;
import no.bouvet.sandvika.activityboard.domain.PeriodType;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityUtils {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ClubRepository clubRepository;


    public List<Activity> getActivitiesForCurrentPeriodByActivityType(String clubName, String activityType, String periodType) {
        Period period = DateUtil.getCurrentPeriod(PeriodType.valueOf(periodType.toUpperCase()), clubRepository.findById(clubName).getCompetitionStartDate());
        return getActivitiesForPeriodByActivityType(clubName, activityType, period);
    }

    public List<Activity> getActivitiesForPeriodByActivityType(String clubName, String activityType, String periodType, int periodNumber, int year) {
        Period period = DateUtil.getPeriod(PeriodType.valueOf(periodType.toUpperCase()), periodNumber, year);
        return getActivitiesForPeriodByActivityType(clubName, activityType, period);
    }

    public List<Activity> getActivitiesForPeriodByActivityType(String clubName, String activityType, Period period) {
        List<Activity> activityList;
        if (activityType.equalsIgnoreCase("all")) {
            activityList = activityRepository.findByStartDateLocalBetween(period.getStart(), period.getEnd());
        } else {
            activityList = activityRepository.findByStartDateLocalBetweenAndType(period.getStart(), period.getEnd(), activityType);
        }

        Club club = clubRepository.findById(clubName);
        List<Activity> filteredActivities = activityList.stream().filter(activity -> club.getMemberIds().contains(activity.getAthleteId())).collect(Collectors.toList());
        return filteredActivities;
    }

    public List<Activity> getActivities(String clubName,
                                        String activityType,
                                        String periodType,
                                        int periodNumber,
                                        int year) {
        return getActivitiesForPeriodByActivityType(clubName, activityType, periodType, periodNumber, year);
    }

    public List<Activity> getActivitiesByActivityType(String activityType, int numberOfActivities) {
        return activityRepository.findByTypeOrderByStartDateLocalDesc(activityType)
                .limit(numberOfActivities)
                .collect(Collectors.toList());
    }

    public List<Activity> getActivities(int numberOfActivities, String clubName) {
        List<Activity> activityList = activityRepository.findAllByOrderByStartDateLocalDesc()
                .limit(numberOfActivities)
                .collect(Collectors.toList());
        Club club = clubRepository.findById(clubName);
        List<Activity> filteredActivities = activityList.stream().filter(activity -> club.getMemberIds().contains(activity.getAthleteId())).collect(Collectors.toList());
        return filteredActivities;
    }

    public List<Activity> getTopActivities(String clubName, int limit, String activityType, String periodType, int periodNumber, int year) {
        return getActivitiesForPeriodByActivityType(clubName, activityType.toLowerCase(), periodType, periodNumber, year)
                .stream()
                .sorted(Comparator.comparingDouble(Activity::getPoints).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Activity> getTopActivitiesForCurrentPeriod(String clubName, int limit, String activityType, String periodType) {
        return getActivitiesForCurrentPeriodByActivityType(clubName, activityType.toLowerCase(), periodType)
                .stream()
                .sorted(Comparator.comparingDouble(Activity::getPoints).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Activity> getMostRecentActivities(String clubName, String activityType, int numberOfActivities) {
        List<Activity> activityList;
        if (activityType.equalsIgnoreCase("all") || activityType.equalsIgnoreCase("")) {
            activityList = getActivities(numberOfActivities, clubName);
        } else {
            activityList = getActivitiesByActivityType(activityType.toLowerCase(), numberOfActivities);
        }
        return activityList;
    }

    public double getMetersForMonthByActivity(String activityType, int month, int year) {
        return activityRepository.findByStartDateLocalBetween(DateUtil.firstDayOfMonth(month - 1, year), DateUtil.lastDayOfMonth(month - 1, year))
                .stream()
                .filter(a -> a.getType().equalsIgnoreCase(activityType))
                .mapToDouble(Activity::getDistanceInMeters)
                .sum();
    }
}
