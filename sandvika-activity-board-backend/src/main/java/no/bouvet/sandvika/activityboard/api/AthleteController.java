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
import no.bouvet.sandvika.activityboard.domain.Handicap;
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
    @RequestMapping(value = "/athlete", method = RequestMethod.PUT)
    public Athlete updateAthlete(@RequestBody Athlete request)
    {
        return athleteRepository.save(request);
    }



//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{lastName}/activities", method = RequestMethod.GET)
    public List<Activity> getUserActivities(@PathVariable("lastName") String lastName)
    {
        return activityRepository.findByAthleteLastName(lastName);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{lastName}/handicap", method = RequestMethod.PUT)
    public Athlete updateAthlete(@PathVariable("lastName") String lastName, @RequestBody Handicap handicap)
    {
        Athlete athlete = athleteRepository.findByLastName(lastName);
        if (athlete == null)
        {
            athlete = new Athlete();
            athlete.setLastName(lastName);
        }
        athlete.getHandicapList().add(handicap);
        return athleteRepository.save(athlete);
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete", method = RequestMethod.GET)
    public List<Athlete> getAllAthletes()
    {
        return athleteRepository.findAll();
    }

    //    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/athlete/{lastName}", method = RequestMethod.GET)
    public Athlete getAllAthletes(@PathVariable("lastName") String lastName)
    {
        return athleteRepository.findByLastName(lastName);
    }


}
