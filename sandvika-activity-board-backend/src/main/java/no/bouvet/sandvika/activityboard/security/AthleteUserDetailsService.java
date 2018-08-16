package no.bouvet.sandvika.activityboard.security;

import no.bouvet.sandvika.activityboard.domain.Athlete;
import no.bouvet.sandvika.activityboard.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AthleteUserDetailsService implements UserDetailsService {

    private AthleteRepository athleteRepository;

    @Autowired
    AthleteUserDetailsService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public AthleteUserDetails loadUserByToken(String token) throws UsernameNotFoundException {
        Athlete athlete = athleteRepository.findOneByToken(token);
        List<GrantedAuthority> authorities = buildUserAuthority(athlete.getRoles());

        AthleteUserDetails athleteUserDetails = new AthleteUserDetails();

        athleteUserDetails.setAthleteId(athlete.getId());
        athleteUserDetails.setToken(athlete.getToken());
        athleteUserDetails.setAuthorities(authorities);

        return athleteUserDetails;
    }

    private List<GrantedAuthority> buildUserAuthority(Set<String> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        for (String role : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(role));
        }

        return new ArrayList<>(setAuths);
    }
}
