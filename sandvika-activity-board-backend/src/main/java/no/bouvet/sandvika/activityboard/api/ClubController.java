package no.bouvet.sandvika.activityboard.api;

import java.util.Arrays;

import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.domain.Club;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClubController
{

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @RequestMapping(value = "/club", method = RequestMethod.POST)
    public void createClub(@RequestBody Club club)
    {
        if (club.getId() == null || club.getCompetitionStartDate() == null)
        {
            throw (new IllegalArgumentException());
        }

        club.getMemberIds().stream().map(athleteRepository::findById).forEach(athlete -> {
            athlete.setClub(club.getId());
            athleteRepository.save(athlete);
        });

        clubRepository.save(club);
    }

    @RequestMapping(value = "/club/{id}/{athleteId}", method = RequestMethod.PUT)
    public void addMember(@PathVariable("id") String id, @PathVariable("athleteId") int athleteId)
    {
        Club club = clubRepository.findById(id);
        club.addMember(athleteId);
        clubRepository.save(club);

        Athlete athlete = athleteRepository.findById(athleteId);
        athlete.setClub(club.getId());
        athleteRepository.save(athlete);
    }

    @RequestMapping(value = "/club/athlete/{athleteId}", method = RequestMethod.GET)
    public List<Club> getAthleteClubs(@PathVariable("athleteId") int athleteId) {
        return clubRepository.findClubsByMemberIdsContains(athleteId);
    }

}
