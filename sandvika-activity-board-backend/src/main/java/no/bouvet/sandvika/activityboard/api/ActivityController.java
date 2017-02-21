package no.bouvet.sandvika.activityboard.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.ActivityType;
import no.bouvet.sandvika.activityboard.domain.LeaderboardEntry;
import no.bouvet.sandvika.activityboard.points.CalculatePoints;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.utils.DateUtil;

@RestController
public class ActivityController
{
    @Autowired
    ActivityRepository activityRepository;


    @RequestMapping(value = "/athlete/{lastName}/activities", method = RequestMethod.GET)
    public List<Activity> getUserActivities(@PathVariable("lastName") String lastName) {
        return activityRepository.findByAthleteLastName(lastName);
    }

    @RequestMapping(value = "/populateDb", method = RequestMethod.GET)
    public void populateDb() {
        activityRepository.deleteAll();
        activityRepository.save(new Activity(1, 98, "Engell", "Sondre", DateUtil.firstDayOfCurrentMonth()));
        activityRepository.save(new Activity(2, 300, "Engell", "Sondre", DateUtil.firstDayOfCurrentWeek()));
        activityRepository.save(new Activity(3, 200, "Stillerud", "Anders", DateUtil.firstDayOfCurrentWeek()));
        activityRepository.save(new Activity(4, 400, "Stillerud", "Anders", DateUtil.firstDayOfCurrentWeek()));
    }

    @RequestMapping(value = "/leaderboard/week/points", method = RequestMethod.GET)
    public List<LeaderboardEntry> getLeaderboardWeek() {
        List<Activity> activityList = activityRepository.findByStartDateLocalAfter(DateUtil.addHours(DateUtil.firstDayOfCurrentWeek(), -24));
        Map<String, Integer> result = activityList.stream().collect(Collectors.groupingBy(activity -> activity.getAthleteLastName(),
                Collectors.summingInt(activity -> activity.getPoints())));
        ;
        List<LeaderboardEntry> resultList = new ArrayList<>();
        for (String lastName : result.keySet()) {
            resultList.add(new LeaderboardEntry(lastName, result.get(lastName)));

        }
        return resultList;
    }


}
