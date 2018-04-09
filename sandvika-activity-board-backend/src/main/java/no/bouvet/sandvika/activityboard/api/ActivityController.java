package no.bouvet.sandvika.activityboard.api;

import no.bouvet.sandvika.activityboard.domain.*;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import no.bouvet.sandvika.activityboard.utils.DateUtil;
import no.bouvet.sandvika.activityboard.utils.Period;
import no.bouvet.sandvika.activityboard.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

//import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    ClubRepository clubRepository;

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/{clubName}/{activityType}/{periodType}", method = RequestMethod.GET)
    public List<Activity> getActivitiesForCurrentPeriod(@PathVariable("clubName") String clubName,
                                                        @PathVariable("activityType") String activityType,
                                                        @PathVariable("periodType") String periodType) {
        Period period = DateUtil.getCurrentPeriod(PeriodType.valueOf(periodType.toUpperCase()), clubRepository.findById(clubName).getCompetitionStartDate());
        return getActivitiesForPeriodByActivityType(clubName, activityType.toLowerCase(), period);
    }

    @RequestMapping(value = "/activities/{clubName}/{activityType}/{periodType}/{periodNumber}/{year}", method = RequestMethod.GET)
    public List<Activity> getActivitiesForPeriod(@PathVariable("clubName") String clubName,
                                                 @PathVariable("activityType") String activityType,
                                                 @PathVariable("periodType") String periodType,
                                                 @PathVariable("periodNumber") int periodNumber,
                                                 @PathVariable("year") int year) {
        Period period = DateUtil.getPeriod(PeriodType.valueOf(periodType.toUpperCase()), periodNumber, year);
        return getActivitiesForPeriodByActivityType(clubName, activityType.toLowerCase(), period);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/leaderboard/{clubName}/{activityType}/{periodType}", method = RequestMethod.GET)
    public List<LeaderboardEntry> getLeaderboardForCurrentPeriod(@PathVariable("clubName") String clubName,
                                                                 @PathVariable("activityType") String activityType,
                                                                 @PathVariable("periodType") String periodType) {
        Period period = DateUtil.getCurrentPeriod(PeriodType.valueOf(periodType.toUpperCase()), clubRepository.findById(clubName).getCompetitionStartDate());
        List<LeaderboardEntry> currentLeaderboard = getLeaderboardEntries(getActivitiesForPeriodByActivityType(clubName, activityType.toLowerCase(), period));
        Period comparingPeriod;
        if (periodType.equalsIgnoreCase("week")) {
            comparingPeriod = DateUtil.getPeriodFromWeekStartToDate(DateUtil.getDateDaysAgo(1));
        } else if (periodType.equalsIgnoreCase("month")) {
            comparingPeriod = DateUtil.getPeriodFromMonthStartToDate(DateUtil.getDateDaysAgo(7));
        } else {
            comparingPeriod = DateUtil.getPeriodBetweenDates(clubRepository.findById(clubName).getCompetitionStartDate(), DateUtil.getDateDaysAgo(7));
        }
        List<LeaderboardEntry> comparingLeaderboard = getLeaderboardEntries(getActivitiesForPeriodByActivityType(clubName,"all", comparingPeriod));

        return addChangeToLeaderboard(currentLeaderboard, comparingLeaderboard);
    }

    @RequestMapping(value = "/leaderboard/{clubName}/total/{date}", method = RequestMethod.GET)
    public List<LeaderboardEntry> getTotalLeaderboardOnDate(@PathVariable("clubName") String clubName,
                                                            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Period period = DateUtil.getPeriodBetweenDates(clubRepository.findById(clubName).getCompetitionStartDate(), date);
        List<LeaderboardEntry> currentLeaderboard = getLeaderboardEntries(getActivitiesForPeriodByActivityType(clubName,"all", period));
        Period comparingPeriod = DateUtil.getPeriodBetweenDates(clubRepository.findById(clubName).getCompetitionStartDate(), DateUtil.firstDayOfCurrentMonth());
        List<LeaderboardEntry> comparingLeaderboard = getLeaderboardEntries(getActivitiesForPeriodByActivityType(clubName,"all", comparingPeriod));

        return addChangeToLeaderboard(currentLeaderboard, comparingLeaderboard);
    }

    private List<LeaderboardEntry> addChangeToLeaderboard(List<LeaderboardEntry> currentLeaderboard, List<LeaderboardEntry> comparingLeaderboard) {
        currentLeaderboard.forEach(leaderboardEntry ->
        {
            int comparingRank = getRankingByAthleteId(leaderboardEntry.getAthleteId(), comparingLeaderboard);
            if (comparingRank == 0) {
                leaderboardEntry.setChange(99);
            } else {
                leaderboardEntry.setChange(
                        comparingRank - leaderboardEntry.getRanking());
            }
        });
        return currentLeaderboard;
    }

    private int getRankingByAthleteId(int athleteId, List<LeaderboardEntry> comparingLeaderboard) {
        return comparingLeaderboard
                .stream()
                .filter(l -> l.getAthleteId() == athleteId)
                .mapToInt(LeaderboardEntry::getRanking)
                .sum();
    }

    private List<Activity> getActivitiesForPeriodByActivityType(String clubName, String activityType, Period period) {
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

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/leaderboard/{clubName}/{activityType}/{periodType}/{periodNumber}/{year}", method = RequestMethod.GET)
    public List<LeaderboardEntry> getLeaderboardForPeriod(@PathVariable("clubName") String clubName,
                                                          @PathVariable("activityType") String activityType,
                                                          @PathVariable("periodType") String periodType,
                                                          @PathVariable("periodNumber") int periodNumber,
                                                          @PathVariable("year") int year) {
        return getLeaderboardEntries(getActivities(clubName, activityType.toLowerCase(), periodType, periodNumber, year));
    }

    private List<Activity> getActivities(String clubName,
                                         String activityType,
                                         String periodType,
                                         int periodNumber,
                                         int year) {
        Period period = DateUtil.getPeriod(PeriodType.valueOf(periodType.toUpperCase()), periodNumber, year);
        return getActivitiesForPeriodByActivityType(clubName, activityType, period);
    }


    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/{clubName}/{activityType}/top/{limit}/{periodType}/{periodNumber}/{year}", method = RequestMethod.GET)
    public List<Activity> getTopActivitiesForPeriod(@PathVariable("clubName") String clubName,
                                                    @PathVariable("limit") int limit,
                                                    @PathVariable("activityType") String activityType,
                                                    @PathVariable("periodType") String periodType,
                                                    @PathVariable("periodNumber") int periodNumber,
                                                    @PathVariable("year") int year) {

        Period period = DateUtil.getPeriod(PeriodType.valueOf(periodType.toUpperCase()), periodNumber, year);
        return getActivitiesForPeriodByActivityType(clubName, activityType.toLowerCase(), period)
                .stream()
                .sorted(Comparator.comparingDouble(Activity::getPoints).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/{clubName}/{activityType}/top/{limit}/{periodType}", method = RequestMethod.GET)
    public List<Activity> getTopActivitiesForCurrentPeriod(@PathVariable("clubName") String clubName,
                                                           @PathVariable("limit") int limit,
                                                           @PathVariable("activityType") String activityType,
                                                           @PathVariable("periodType") String periodType) {

        Period period = DateUtil.getCurrentPeriod(PeriodType.valueOf(periodType.toUpperCase()), clubRepository.findById(clubName).getCompetitionStartDate());
        return getActivitiesForPeriodByActivityType(clubName, activityType.toLowerCase(), period)
                .stream()
                .sorted(Comparator.comparingDouble(Activity::getPoints).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/activities/{clubName}/{activityType}/stats/{periodType}", method = RequestMethod.GET)
    public Statistics getStatisticsForCurrentPeriodByActivityType(@PathVariable("clubName") String clubName,
                                                                  @PathVariable("activityType") String activityType,
                                                                  @PathVariable("periodType") String periodType) {
        return createStatsForCurrentPeriod(clubName, activityType.toLowerCase(), periodType);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/{clubName}/{activityType}/stats/{periodType}/{periodNumber}/{year}", method = RequestMethod.GET)
    public Statistics getStatisticsForPeriodByActivityType(@PathVariable("clubName") String clubName,
                                                           @PathVariable("activityType") String activityType,
                                                           @PathVariable("periodType") String periodType,
                                                           @PathVariable("periodNumber") int periodNumber,
                                                           @PathVariable("year") int year) {
        return createStatsForHistoricPeriod(clubName, activityType.toLowerCase(), periodType, periodNumber, year);
    }

    @RequestMapping(value = "/activities/{activityType}/latest/{numberOfActivities}", method = RequestMethod.GET)
    public List<Activity> getStatisticsForPeriodByActivityType(@PathVariable("activityType") String activityType,
                                                               @PathVariable("numberOfActivities") int numberOfActivities) {
        List<Activity> activityList;
        if (activityType.equalsIgnoreCase("all") || activityType.equalsIgnoreCase("")) {
            activityList = getActivities(numberOfActivities);
        } else {
            activityList = getActivitiesByActivityType(activityType.toLowerCase(), numberOfActivities);
        }
        return activityList;
    }

    private List<Activity> getActivitiesByActivityType(String activityType, int numberOfActivities) {
        return activityRepository.findByTypeOrderByStartDateLocalDesc(activityType)
                .limit(numberOfActivities)
                .collect(Collectors.toList());
    }

    private List<Activity> getActivities(int numberOfActivities) {
        return activityRepository.findAllByOrderByStartDateLocalDesc()
                .limit(numberOfActivities)
                .collect(Collectors.toList());
    }


    private Statistics createStatsForCurrentPeriod(String clubName, String activityType, String periodType) {
        Period period = DateUtil.getCurrentPeriod(PeriodType.valueOf(periodType.toUpperCase()), clubRepository.findById(clubName).getCompetitionStartDate());

        return getStatistics(clubName, activityType, periodType, period);
    }


    private Statistics createStatsForHistoricPeriod(String clubName, String activityType, String periodType, int periodNumber, int year) {
        Period period = DateUtil.getPeriod(PeriodType.valueOf(periodType.toUpperCase()), periodNumber, year);

        return getStatistics(clubName, activityType, periodType, period);
    }

    private Statistics getStatistics(String clubName, String activityType, String periodType, Period period) {
        List<Activity> activities;
        activities = getActivitiesForPeriodByActivityType(clubName, activityType, period);
        Statistics stats = new Statistics();
        stats.setType(activityType);
        stats.setPeriodType(PeriodType.valueOf(periodType.toUpperCase()));
        stats.setStartDate(period.getStart());
        stats.setAchievements(activities.stream().mapToInt(Activity::getAchievementCount).sum());
        stats.setMeters(Utils.scaledDouble(activities.stream().mapToDouble(Activity::getDistanceInMeters).sum()));
        stats.setMinutes(Utils.scaledDouble(activities.stream().mapToDouble(Activity::getMovingTimeInSeconds).sum() / 60));
        stats.setActivities(activities.size());
        stats.setKiloJoules(activities.stream().mapToDouble(Activity::getKiloJoules).sum());
        return stats;
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/activities/{activityType}/total/meters/{month}/{year}", method = RequestMethod.GET)
    public double getTotalMetersForMonthByActivity(@PathVariable("activityType") String activityType, @PathVariable("month") int month, @PathVariable("year") int year) {
        return activityRepository.findByStartDateLocalBetween(DateUtil.firstDayOfMonth(month - 1, year), DateUtil.lastDayOfMonth(month - 1, year))
                .stream()
                .filter(a -> a.getType().equalsIgnoreCase(activityType))
                .mapToDouble(Activity::getDistanceInMeters)
                .sum();
    }


    private List<LeaderboardEntry> getLeaderboardEntries(List<Activity> activityList) {
        Map<Integer, LeaderboardEntry> entries = new HashMap<>();
        for (Activity activity : activityList) {
            if (!entries.containsKey(activity.getAthleteId())) {
                LeaderboardEntry entry = new LeaderboardEntry(activity.getAthleteId(), activity.getPoints());
                entry.setAthleteLastName(activity.getAthleteLastName());
                entry.setAthleteFirstName(activity.getAthletefirstName());
                entry.setNumberOfActivities(1);

                entry.setHandicap(getCurrentHandicap(activity.getAthleteId()));
                entry.setKilometers(activity.getDistanceInMeters() / 1000);
                entry.setMinutes(activity.getMovingTimeInSeconds() / 60);
                entry.setLastActivityDate(activity.getStartDateLocal());
                entries.put(entry.getAthleteId(), entry);

            } else {
                LeaderboardEntry entry = entries.get(activity.getAthleteId());
                entry.setNumberOfActivities(entry.getNumberOfActivities() + 1);
                entry.setKilometers(entry.getKilometers() + (activity.getDistanceInMeters() / 1000));
                entry.setMinutes(Double.valueOf(entry.getMinutes() + (activity.getMovingTimeInSeconds() / 60)).intValue());
                entry.setPoints(entry.getPoints() + activity.getPoints());
                if (entry.getLastActivityDate().before(activity.getStartDateLocal())) {
                    entry.setLastActivityDate(activity.getStartDateLocal());
                }
            }
        }

        List<LeaderboardEntry> sortedEntries = (entries.values())
                .stream()
                .sorted(Comparator.comparingDouble(LeaderboardEntry::getPoints).reversed())
                .collect(Collectors.toList());

        for (LeaderboardEntry e : sortedEntries) {
            e.setRanking(sortedEntries.indexOf(e) + 1);
        }
        return sortedEntries;
    }

    private double getCurrentHandicap(int athleteId) {
        Athlete athlete = athleteRepository.findById(athleteId);
        if (athlete == null) {
            return 1;
        }
        return athlete.getHandicapForDate(new Date());
    }

}
