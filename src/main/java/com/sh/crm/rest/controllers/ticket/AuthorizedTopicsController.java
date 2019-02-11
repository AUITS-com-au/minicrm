package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.GetAuthorizedTopics;
import com.sh.crm.jpa.repos.tickets.GeneratedTopicsPermissionsRepo;
import com.sh.crm.jpa.repos.tickets.TopicRepo;
import com.sh.crm.jpa.repos.tickets.TopicsPermissionsRepo;
import com.sh.crm.rest.general.BasicGeneralController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "authorized", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizedTopicsController extends BasicGeneralController {

    @Autowired
    private GeneratedTopicsPermissionsRepo generatedTopicsPermissions;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private TopicsPermissionsRepo topicsPermissionsRepo;

    @PostMapping("mainCats")
    public ResponseEntity<?> getAuthorizedMainCats
            (@RequestBody GetAuthorizedTopics request, Principal principal) throws GeneralException {
        if (request != null && request.getPermissions() != null) {
            request.setAssigne( principal.getName() );

            return ResponseEntity.ok( topicsPermissionsRepo.getAuthorizedMainCat( request ) );
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("subCats")
    public ResponseEntity<?> getAuthorizedSubCats
            (@RequestBody GetAuthorizedTopics request, Principal principal) throws GeneralException {
        if (request != null && request.getPermissions() != null) {
            request.setAssigne( principal.getName() );
            return ResponseEntity.ok( topicsPermissionsRepo.getAuthorizedSubCat( request ) );
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("topics")
    public ResponseEntity<?> getAuthorizedTopics
            (@RequestBody GetAuthorizedTopics request, Principal principal) throws GeneralException {
        if (request != null && request.getPermissions() != null) {
            request.setAssigne( principal.getName() );
            return ResponseEntity.ok( topicsPermissionsRepo.getAuthorizedTopics( request ) );
        }
        return ResponseEntity.badRequest().build();
    }
}
