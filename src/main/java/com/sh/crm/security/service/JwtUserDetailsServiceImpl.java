package com.sh.crm.security.service;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.entities.Users;
import com.sh.crm.jpa.repos.users.PermissionsRepo;
import com.sh.crm.jpa.repos.users.UsersRepos;
import com.sh.crm.security.model.CustomAuthority;
import com.sh.crm.security.util.JwtUserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsServiceImpl.class);
    @Autowired
    private UsersRepos userRepository;
    @Autowired
    private PermissionsRepo permissionsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserID(username);
        if (user == null) {
            if (logger.isErrorEnabled())
                logger.info("User: " + username + " not found");
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            if (logger.isDebugEnabled())
                logger.debug("User :" + username + " found, loading information");
            return JwtUserFactory.create( user, getUserAuthorities( user.getUserID() ) );
        }
    }

    public List<Permissions> getUserAuthorities(String id) {

        List<Permissions> userAuthorities = permissionsRepo.usersPermission(id);
        if (logger.isDebugEnabled())
            logger.debug("User Authorities loaded: " + userAuthorities);

        return userAuthorities;
    }

    public Set<CustomAuthority> getUserGrantedAuth(String username) {
        // Users user = userRepository.findByUserID(username);
        List<Permissions> allAuth = getUserAuthorities( username );
        Set<CustomAuthority> activeAuth = new HashSet<>();
        if (allAuth != null) {
            for (Permissions auth : allAuth) {
                activeAuth.add(new CustomAuthority(auth.getPermission()));
            }
        }

        allAuth = null;
        return activeAuth;
    }

}
