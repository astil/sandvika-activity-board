package no.bouvet.sandvika.activityboard.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    CustomAuthenticationToken(String token, AthleteUserDetails athlete, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        if(athlete.getToken().equalsIgnoreCase(token)) {
            super.setAuthenticated(true);
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
