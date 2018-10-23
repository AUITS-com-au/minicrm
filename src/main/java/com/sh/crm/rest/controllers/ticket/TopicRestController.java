package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.jpa.entities.Users;
import com.sh.crm.jpa.repos.tickets.TopicRepo;
import com.sh.crm.jpa.repos.tickets.TopicsPermissionsRepo;
import com.sh.crm.jpa.repos.users.UsersRepos;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import com.sh.crm.security.annotation.TicketsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "topics", produces = MediaType.APPLICATION_JSON_VALUE)
public class TopicRestController extends BasicController<Topic> {

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private TopicsPermissionsRepo topicsPermissionsRepo;
    @Autowired
    private UsersRepos usersRepos;

    @Override
    @TicketsAdmin
    public ResponseEntity<?> create(@RequestBody Topic topic, Principal principal) throws GeneralException {
        if (topic != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to create new topic  {}", topic );
            topic.setEnabled( true );
            topic.setId( null );
            try {
                topicRepo.save( topic );
                if (log.isDebugEnabled())
                    log.debug( "Topic {} created successfully", topic );
                return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ) );
    }

    @Override
    @TicketsAdmin
    public ResponseEntity<?> edit(@RequestBody Topic topic, Principal principal) throws GeneralException {
        if (topic != null) {
            if (log.isDebugEnabled())
                log.debug( "Received a request to modify topic {}", topic );
            try {
                topic = topicRepo.save( topic );
                if (log.isDebugEnabled())
                    log.debug( "topic {} modified successfully", topic );
                return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
    }

    @TicketsAdmin
    @PostMapping("/permissions/create")
    public ResponseEntity<?> createTopicPermissions(@RequestBody Topicspermissions topicspermissions) throws GeneralException {

        if (topicspermissions != null) {
            try {
                topicsPermissionsRepo.save( topicspermissions );
                if (log.isDebugEnabled())
                    log.debug( "Topics permissions for topic {} created/modified sucessfully with values {} ", topicspermissions.getTopicId(), topicspermissions );

                return ResponseEntity.ok().body( new ResponseCode( Errors.SUCCESSFUL ) );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        throw new GeneralException( Errors.CANNOT_CREATE_OBJECT );
    }

    @TicketsAdmin
    @PostMapping("/permissions/edit")
    public ResponseEntity<?> editTopicPermissions(@RequestBody Topicspermissions topicspermissions) throws GeneralException {

        if (topicspermissions != null && topicsPermissionsRepo.findOne( topicspermissions.getId() ) != null) {
            try {
                ResponseEntity responseEntity = createTopicPermissions( topicspermissions );
                return responseEntity;
            } catch (GeneralException e) {
                throw new GeneralException( Errors.CANNOT_EDIT_OBJECT );
            }
        }
        return ResponseEntity.badRequest().body( Errors.CANNOT_EDIT_OBJECT );

    }

    @TicketsAdmin
    @GetMapping("/permissions/{topic}")
    public Iterable<Topicspermissions> getTopicPermissions(@PathVariable("topic") Integer topicID) {
        return topicsPermissionsRepo.findByTopicId( new Topic( topicID ) );
    }

    @TicketsAdmin
    @GetMapping("/permissions/users/{userId}")
    public Iterable<Topicspermissions> getUserTopicPermissions(@PathVariable("userId") Integer userID) {
        return topicsPermissionsRepo.getUserTopicsPermissions( userID );
    }

    @TicketsAdmin
    @GetMapping("/permissions/groups/{groupId}")
    public Iterable<Topicspermissions> getGroupTopicPermissions(@PathVariable("groupId") Integer groupID) {
        return topicsPermissionsRepo.findByAssigneAndType( groupID, "group" );
    }


    @Override
    @TicketsAdmin
    public ResponseEntity<?> delete(@RequestBody Topic topic, Principal principal) throws GeneralException {
        return null;
    }

    @Override
    @TicketsAdmin
    protected Iterable<?> all() {
        return topicRepo.findAll();
    }

    @Override
    @TicketsUser
    protected Iterable<?> authorizedList(Principal principal) {

        if (principal != null) {
            Users user = usersRepos.findByUserID( principal.getName() );
            return topicsPermissionsRepo.getUserTopics( user.getId() );
        }

        return null;

    }

    @GetMapping("/authorized/{subCat}")
    public Iterable<?> getAuthorized(Principal principal, @PathVariable("subCat") Integer subCat) throws GeneralException {
        return topicsPermissionsRepo.getUserTopics( getAuthorizedUser( principal ).getId(), subCat );
    }

    @GetMapping("/subCat/{subCat}")
    @TicketsAdmin
    Iterable<?> getBySubCat(@PathVariable("subCat") Integer subCat) {
        return topicRepo.findBySubCategory( new Subcategory( subCat ) );
    }

    @GetMapping(value = "/{ID}", produces = "application/json")
    ResponseEntity<Topic> findByID(@PathVariable("ID") Integer topicID) {
        return ResponseEntity.ok( topicRepo.findOne( topicID ) );
    }
}
