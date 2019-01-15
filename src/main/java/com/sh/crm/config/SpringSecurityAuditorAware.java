package com.sh.crm.config;


import com.sh.crm.security.model.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class SpringSecurityAuditorAware implements AuditorAware<String> {
    private static final Logger log = LoggerFactory.
            getLogger( SpringSecurityAuditorAware.class );

    public SpringSecurityAuditorAware() {

    }

    public Optional<String> getCurrentAuditor() {
        log.debug( "Getting current authenticated user" );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.debug( "Current Authenticated User IS NULL!!" );
            return null;
        }
        return Optional.of( ((JwtUser) authentication.getPrincipal()).getUsername() );
    }
}