package com.sh.crm.security.config;


import com.sh.crm.security.service.JwtUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class LDAPUserDetailsMapper extends LdapUserDetailsMapper {
    private final Logger log = LoggerFactory.getLogger(LDAPUserDetailsMapper.class);
    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        log.debug("Loading LDAP user details for user: " + username);

        return userDetailsService.loadUserByUsername(username);

    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
    }
}


