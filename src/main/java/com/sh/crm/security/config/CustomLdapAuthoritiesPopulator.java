package com.sh.crm.security.config;


import com.sh.crm.security.service.JwtUserDetailsServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {
    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;
    private Logger log = LoggerFactory.getLogger(CustomLdapAuthoritiesPopulator.class);

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(
            DirContextOperations userData, String username) {
        log.debug("Loading LDAP user roles, username: " + username);
        if (username.indexOf('@') != -1) {
            username = username.substring(0, username.indexOf('@') - 1);
        }
        return userDetailsService.getUserGrantedAuth(username);
    }
}