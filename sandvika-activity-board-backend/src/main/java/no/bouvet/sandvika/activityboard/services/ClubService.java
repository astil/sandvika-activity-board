package no.bouvet.sandvika.activityboard.services;

import no.bouvet.sandvika.activityboard.domain.Club;
import no.bouvet.sandvika.activityboard.repository.ClubRepository;
import no.bouvet.sandvika.activityboard.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class ClubService {

    private ClubRepository clubRepository;

    @Autowired
    ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public void updateCompetitionStartDate(String clubId, String newDate) {

        Date date = DateUtil.getDateFromLocalDateTimeString(newDate);
        Club club = this.clubRepository.findById(clubId);
        club.setCompetitionStartDate(date);
        this.clubRepository.save(club);
    }
}
