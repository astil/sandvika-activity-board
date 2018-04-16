package no.bouvet.sandvika.activityboard.utils;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.PeriodType;
import no.bouvet.sandvika.activityboard.domain.Statistics;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatsUtils {
    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ActivityUtils activityUtils;

    public Statistics createStatsForCurrentPeriod(String clubName, String activityType, String periodType) {
        Period period = DateUtil.getCurrentPeriod(PeriodType.valueOf(periodType.toUpperCase()), clubRepository.findById(clubName).getCompetitionStartDate());

        return getStatistics(clubName, activityType, periodType, period);
    }


    public Statistics createStatsForHistoricPeriod(String clubName, String activityType, String periodType, int periodNumber, int year) {
        Period period = DateUtil.getPeriod(PeriodType.valueOf(periodType.toUpperCase()), periodNumber, year);

        return getStatistics(clubName, activityType, periodType, period);
    }

    private Statistics getStatistics(String clubName, String activityType, String periodType, Period period) {
        List<Activity> activities;
        activities = activityUtils.getActivitiesForPeriodByActivityType(clubName, activityType, period);
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
}
