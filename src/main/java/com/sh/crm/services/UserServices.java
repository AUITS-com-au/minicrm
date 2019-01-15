package com.sh.crm.services;

import com.sh.crm.jpa.entities.Users;
import com.sh.crm.jpa.repos.users.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    private UsersRepos usersRepos;
    @Autowired
    private PasswordEncoder encoder;

    public void validateCredentials(String username, String password) throws BadCredentialsException {
        Users user = usersRepos.findByUserIDAndEnabledAndLDAPUser( username, true, false );
        if (user == null) {
            throw new BadCredentialsException( "Invalid username or user is disabled" );
        }
        if (!encoder.matches( password, user.getPassword() )) {
            throw new BadCredentialsException( "Invalid username or password" );
        }
    }

}
