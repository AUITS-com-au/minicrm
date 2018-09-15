package com.sh.crm.services;

import com.sh.crm.jpa.entities.Users;
import com.sh.crm.jpa.repos.users.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    private UsersRepos usersRepos;

    public void validateCredentials(String username, String password) throws BadCredentialsException {
        Users user = usersRepos.findByUserIDAndEnabledAndLDAPUser(username, true, false);
        if (user == null) {
            throw new BadCredentialsException("Invalid username or user is disabled");
        }
        if (!passwordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
