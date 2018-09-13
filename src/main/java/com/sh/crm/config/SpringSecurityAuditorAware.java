package com.sh.crm.config;


import com.sh.crm.security.model.JwtUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    public SpringSecurityAuditorAware() {
        // TODO Auto-generated constructor stub
    }

    public String getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((JwtUser) authentication.getPrincipal()).getUsername();
    }
}