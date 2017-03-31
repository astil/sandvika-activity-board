package no.bouvet.sandvika.activityboard.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.bouvet.sandvika.activityboard.domain.Activity;
import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.repository.ActivityRepository;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;

@RestController
public class AthleteController
{

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AthleteRepository athleteRepository;

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{id}/activities", method = RequestMethod.GET)
    public List<Activity> getUserActivities(@PathVariable("id") int id)
    {
        return activityRepository.findByAthleteId(id);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete", method = RequestMethod.GET)
    public List<Athlete> getAllAthletes()
    {
        return athleteRepository.findAll();
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{id}", method = RequestMethod.GET)
    public Athlete getAllAthletes(@PathVariable("id") int id)
    {
        return athleteRepository.findById(id);
    }


}
