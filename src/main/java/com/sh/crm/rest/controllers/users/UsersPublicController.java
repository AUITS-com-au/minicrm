package com.sh.crm.rest.controllers.users;

import com.sh.crm.jpa.entities.Userpreferences;
import com.sh.crm.jpa.entities.Users;
import com.sh.crm.jpa.repos.users.UserPreferencesRepo;
import com.sh.crm.jpa.repos.users.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "usersP", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class UsersPublicController {
    @Autowired
    private UsersRepos userRepo;
    @Autowired
    private UserPreferencesRepo userPreferencesRepo;

    @GetMapping("preferences/{id}")
    public ResponseEntity<?> getUserPreferences(@PathVariable("id") Integer id) {

        Users user = userRepo.findById( id ).orElse( null );

        if (user == null) {

            return ResponseEntity.badRequest().build();
        }

        return getUserPreferences( user.getUserID() );
    }

    public ResponseEntity<?> getUserPreferences(@PathVariable("userID") String userID) {

        Userpreferences userpreferences = userPreferencesRepo.findByUserID( userID );
        if (userpreferences == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok( userpreferences );
    }
}
