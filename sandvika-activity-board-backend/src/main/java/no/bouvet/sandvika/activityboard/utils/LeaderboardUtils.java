package no.bouvet.sandvika.activityboard.utils;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.LeaderboardEntry;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LeaderboardUtils {

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ActivityUtils activityUtils;

    private static int getRankingByAthleteId(int athleteId, List<LeaderboardEntry> comparingLeaderboard) {
        return comparingLeaderboard
                .stream()
                .filter(l -> l.getAthleteId() == athleteId)
                .mapToInt(LeaderboardEntry::getRanking)
                .sum();
    }

    public List<LeaderboardEntry> addChangeToLeaderboard(List<LeaderboardEntry> currentLeaderboard, List<LeaderboardEntry> comparingLeaderboard) {
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

    public List<LeaderboardEntry> getLeaderboardEntries(List<Activity> activityList) {
        Map<Integer, LeaderboardEntry> entries = new HashMap<>();
        for (Activity activity : activityList) {
            if (!entries.containsKey(activity.getAthleteId())) {
                LeaderboardEntry entry = new LeaderboardEntry(activity.getAthleteId(), activity.getPoints());
                entry.setAthleteLastName(activity.getAthleteLastName());
                entry.setAthleteFirstName(activity.getAthleteFirstName());
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

    public List<LeaderboardEntry> getLeaderboardEntries(String clubName, String activityType, String periodType) {
        List<LeaderboardEntry> currentLeaderboard = getLeaderboardEntries(activityUtils.getActivitiesForCurrentPeriodByActivityType(clubName, activityType.toLowerCase(), periodType));
        Period comparingPeriod;
        if (periodType.equalsIgnoreCase("week")) {
            comparingPeriod = DateUtil.getPeriodFromWeekStartToDate(DateUtil.getDateDaysAgo(1));
        } else if (periodType.equalsIgnoreCase("month")) {
            comparingPeriod = DateUtil.getPeriodFromMonthStartToDate(DateUtil.getDateDaysAgo(7));
        } else {
            comparingPeriod = DateUtil.getPeriodBetweenDates(clubRepository.findById(clubName).getCompetitionStartDate(), DateUtil.getDateDaysAgo(7));
        }
        List<LeaderboardEntry> comparingLeaderboard = getLeaderboardEntries(activityUtils.getActivitiesForPeriodByActivityType(clubName, "all", comparingPeriod));

        return addChangeToLeaderboard(currentLeaderboard, comparingLeaderboard);
    }

    public List<LeaderboardEntry> getLeaderboardEntries(String clubName, Date date) {
        Period period = DateUtil.getPeriodBetweenDates(clubRepository.findById(clubName).getCompetitionStartDate(), date);
        List<LeaderboardEntry> currentLeaderboard = getLeaderboardEntries(activityUtils.getActivitiesForPeriodByActivityType(clubName, "all", period));
        Period comparingPeriod = DateUtil.getPeriodBetweenDates(clubRepository.findById(clubName).getCompetitionStartDate(), DateUtil.firstDayOfCurrentMonth());
        List<LeaderboardEntry> comparingLeaderboard = getLeaderboardEntries(activityUtils.getActivitiesForPeriodByActivityType(clubName, "all", comparingPeriod));

        return addChangeToLeaderboard(currentLeaderboard, comparingLeaderboard);
    }

    public int getLeaderboardStanding(String clubName, Date date, int athleteId) {
        List<LeaderboardEntry> leaderboardEntry = getLeaderboardEntries(clubName, date)
                .stream()
                .filter(e -> e.getAthleteId() == athleteId)
                .collect(Collectors.toList());
        if (leaderboardEntry != null || leaderboardEntry.size() == 0) {
            return leaderboardEntry.get(0).getRanking();
        } else {
            return 0;
        }
    }

}
