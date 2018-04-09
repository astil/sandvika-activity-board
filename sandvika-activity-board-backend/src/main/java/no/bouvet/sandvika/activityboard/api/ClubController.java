package no.bouvet.sandvika.activityboard.api;

import no.bouvet.sandvika.activityboard.domain.Club;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClubController {

    @Autowired
    ClubRepository clubRepository;

    @RequestMapping(value = "/club", method = RequestMethod.POST)
    public void createClub(@RequestBody Club club) {
        if (club.getId() == null || club.getCompetitionStartDate() == null) {
            throw(new IllegalArgumentException());
        }
        clubRepository.save(club);
    }

    @RequestMapping(value = "/club/{id}/{athleteId}", method = RequestMethod.PUT)
    public void addMember(@PathVariable("id") String id, @PathVariable("athleteId") int athleteId) {
        Club club = clubRepository.findById(id);
        club.addMember(athleteId);
        clubRepository.save(club);
    }

}
