package com.sh.crm.security.config;


import com.sh.crm.security.service.JwtUserDetailsServiceImpl;
import com.sh.crm.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private UserServices userServices;
    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        log.info("Authenticating user:" + auth.getName(), " check Database first");

        String username = auth.getName();
        String password = auth.getCredentials().toString();

        userServices.validateCredentials(username, password);

        return new UsernamePasswordAuthenticationToken(username, password, userDetailsService.getUserGrantedAuth(username));
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}