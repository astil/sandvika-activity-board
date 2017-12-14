package no.bouvet.sandvika.activityboard.points;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;

public class HandicapCalculatorTest
{
    private List<Activity> activityList = new ArrayList<>();

    @Before
    public void setup()
    {
        createActivityList();
    }

    private void createActivityList()
    {
        Activity activity = new Activity();
        activity.setMovingTimeInSeconds(60*60*16);
        activityList.add(activity);
    }

    @Test
    public void testCalculateHandicapForAthlete()
    {
        AthleteRepository mockAthleteRepo = Mockito.mock(AthleteRepository.class);
        ActivityRepository mockActivityRepo = Mockito.mock(ActivityRepository.class);
        Mockito.when(mockActivityRepo.findByStartDateLocalBetweenAndAthleteId(Mockito.any(), Mockito.any(), Mockito.anyInt())).thenReturn(activityList);
        HandicapCalculator calculator = new HandicapCalculator(mockAthleteRepo, mockActivityRepo);
        Athlete a = new Athlete();
        a.setId(1);
        double calculatedHc = calculator.calculateHandicapForAthlete(a, new Date());
        System.out.println(calculatedHc);
    }

}