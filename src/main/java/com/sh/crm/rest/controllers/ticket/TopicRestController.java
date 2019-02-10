package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.TopicPermissionsRequestHolder;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.jpa.repos.tickets.GeneratedTopicsPermissionsRepo;
import com.sh.crm.jpa.repos.tickets.TopicRepo;
import com.sh.crm.jpa.repos.tickets.TopicsPermissionsRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import com.sh.crm.security.annotation.TicketsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(value = "topics", produces = MediaType.APPLICATION_JSON_VALUE)
public class TopicRestController extends BasicController<Topic> {

    @Autowired
    private GeneratedTopicsPermissionsRepo generatedTopicsPermissions;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private TopicsPermissionsRepo topicsPermissionsRepo;


    @Override
    @TicketsAdmin
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createTopicPermissions(@RequestBody List<Topicspermissions> topicspermissions) throws GeneralException {

        if (log.isDebugEnabled()) {
            log.debug( "create/modify topic permissions list {}", topicspermissions.toString() );
        }
        if (topicspermissions != null && !topicspermissions.isEmpty()) {
            try {
                Set<Topicspermissions> created = new LinkedHashSet<>();
                for (Topicspermissions tp : topicspermissions) {

                    Set<Topicspermissions> existing = topicsPermissionsRepo.findByAssigneAndTypeAndTopicId( tp.getAssigne(), tp.getType(), tp.getTopicId() );

                    if (existing != null && !existing.isEmpty()) {
                        try {
                            topicsPermissionsRepo.deleteAll( existing );
                            //  topicsPermissionsRepo.deleteById( tp.getId() );
                            // tp.setId( null );
                        } catch (Exception e) {
                            LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
                            return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
                        }
                    }

                    if (validateTopicPerm( tp )) {
                        topicsPermissionsRepo.save( tp );
                        created.add( tp );
                    } else {
                        log.info( "topic permission {}\n will not be considered because it has no true value", tp );
                    }
                    if (tp.getType().equalsIgnoreCase( "user" )) {
                        topicsPermissionsRepo.generateUserTopicsPermission( tp.getTopicId().getId(), "", tp.getAssigne() );
                    } else {
                        topicsPermissionsRepo.generateGroupTopicsPermission( tp.getTopicId().getId(), "", tp.getAssigne() );
                    }


                }
                if (log.isDebugEnabled())
                    log.debug( "Topics permissions created/modified sucessfully " );

                return ResponseEntity.ok().body( created );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        throw new GeneralException( Errors.CANNOT_CREATE_OBJECT );
    }

    @TicketsAdmin
    @PostMapping("/permissions/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteTopicPermissions(@RequestBody List<Topicspermissions> topicspermissions) throws GeneralException {

        if (log.isDebugEnabled()) {
            log.debug( "delete topic permissions list {}", topicspermissions.toString() );
        }
        if (topicspermissions != null && !topicspermissions.isEmpty()) {
            try {
                for (Topicspermissions tp : topicspermissions) {

                    if (tp != null && tp.getId() != null && topicsPermissionsRepo.existsById( tp.getId() )) {
                        try {
                            //delete old permissions for the same topic and assigne
                            topicsPermissionsRepo.deleteById( tp.getId() );
                            if (tp.getType().equalsIgnoreCase( "user" )) {
                                topicsPermissionsRepo.generateUserTopicsPermission( tp.getTopicId().getId(), "", tp.getAssigne() );
                            } else {
                                topicsPermissionsRepo.generateGroupTopicsPermission( tp.getTopicId().getId(), "", tp.getAssigne() );
                            }
                        } catch (Exception e) {
                            LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
                            return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
                        }
                    }
                }
                if (log.isDebugEnabled())
                    log.debug( "Topics permissions deleted sucessfully " );

                topicspermissions = null;
                return ResponseEntity.ok().body( new ResponseCode( Errors.SUCCESSFUL ) );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        throw new GeneralException( Errors.CANNOT_CREATE_OBJECT );
    }

    private boolean validateTopicPerm(Topicspermissions tp) {
        if (tp != null) {
            return (tp.getCanAssign() ||
                    tp.getCanChgDpt()
                    || tp.getCanClose()
                    || tp.getAdmin()
                    | tp.getCanCreate()
                    || tp.getCanDelete()
                    || tp.getCanModify()
                    || tp.getCanRead()
                    || tp.getCanReopen()
                    || tp.getCanReply()
                    || tp.getCanResolve()
                    || tp.getCanSubscribe()
                    || tp.getCanRunReport());
        }
        return false;
    }

    @TicketsAdmin
    @PostMapping("/permissions/edit")
    public ResponseEntity<?> editTopicPermissions(@RequestBody List<Topicspermissions> topicspermissions) throws GeneralException {
        return createTopicPermissions( topicspermissions );
    }

    @TicketsAdmin
    @GetMapping("/permissions/{topic}")
    public Iterable<Topicspermissions> getTopicPermissions(@PathVariable("topic") Integer topicID) {
        return fillInfo( topicsPermissionsRepo.findByTopicId_Id( topicID ) );
    }

    @TicketsAdmin
    @GetMapping("/permissions/subCat/{subCat}")
    public Iterable<Topicspermissions> getSubCatPermissions(@PathVariable("subCat") Integer subCat) {
        return fillInfo( topicsPermissionsRepo.findByTopicId_SubCategory_Id( subCat ) );
    }

    @TicketsAdmin
    @GetMapping("/permissions/mainCat/{mainCat}")
    public Iterable<Topicspermissions> getMainCatPermissions(@PathVariable("mainCat") Integer mainCat) {
        return fillInfo( topicsPermissionsRepo.findByTopicId_SubCategory_MainCategory_Id( mainCat ) );
    }

    @TicketsAdmin
    @GetMapping("/permissions/users/{userId}")
    public Iterable<Topicspermissions> getUserTopicPermissions(@PathVariable("userId") Integer userID) {
        return fillInfo( topicsPermissionsRepo.findByAssigneAndType( userID, "user" ) );
    }

    @TicketsAdmin
    @GetMapping("/permissions/groups/{groupId}")
    public Iterable<Topicspermissions> getGroupTopicPermissions(@PathVariable("groupId") Integer groupID) {
        return fillInfo( topicsPermissionsRepo.findByAssigneAndType( groupID, "group" ) );
    }

    @TicketsAdmin
    @PostMapping("/permissions/topics/prepareList")
    public Iterable<Topicspermissions> prepare(@RequestBody TopicPermissionsRequestHolder requestHolder) {
        List<Topicspermissions> existingList = topicsPermissionsRepo.findTopicsPermissionByTPListAndAssigneAndType( requestHolder.getTopicList(), requestHolder.getAssigne(), requestHolder.getType() );
        Collection<Topicspermissions> filledInfo = fillInfo( existingList );
        if (filledInfo == null)
            filledInfo = new ArrayList<>();

        Users user = null;
        Groups group = null;

        if (requestHolder.getType().equalsIgnoreCase( "user" )) {
            user = usersRepos.findById( requestHolder.getAssigne() ).orElse( null );
        } else {
            group = groupsRepo.findById( requestHolder.getAssigne() ).orElse( null );
        }

        for (Topic topic : requestHolder.getTopicList()) {
            topic = topicRepo.findById( topic.getId() ).orElse( null );
            if (!isTopicExistsInTp( topic, filledInfo )) {
                Topicspermissions tp = new Topicspermissions();
                tp.setGroup( group );
                tp.setUser( user );
                tp.setTopicId( topic );
                tp.setAssigne( requestHolder.getAssigne() );
                tp.setType( requestHolder.getType() );
                filledInfo.add( tp );
            }
        }
        return filledInfo;
    }

    private boolean isTopicExistsInTp(Topic topic, Collection<Topicspermissions> topicspermissions) {
        for (Topicspermissions item : topicspermissions) {
            if (item.getTopicId().getId() == topic.getId())
                return true;
        }
        return false;
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
            return topicsPermissionsRepo.getUserTopics( principal.getName() );
        }
        return null;
    }

    @GetMapping("/authorized/{subCat}")
    public Iterable<?> getAuthorized(Principal principal, @PathVariable("subCat") Integer subCat) throws GeneralException {
        return topicsPermissionsRepo.getUserTopics( principal.getName(), subCat );
    }

    @Override
    protected Iterable<?> active() {
        return topicRepo.findByEnabledTrue();
    }

    @GetMapping("/active/{subCat}")
    public Iterable<?> getActiveBySubCat(Principal principal, @PathVariable("subCat") Integer subCat) throws GeneralException {
        return topicRepo.findBySubCategory_IdAndEnabledTrue( subCat );
    }

    @GetMapping("/active/mainCat/{mainCat}")
    public Iterable<?> getActiveByMainCat(Principal principal, @PathVariable("mainCat") Integer mainCat) throws GeneralException {
        return topicRepo.findBySubCategory_MainCategory_IdAndEnabledTrue( mainCat );
    }

    @GetMapping("/subCat/{subCat}")
    @TicketsAdmin
    Iterable<?> getBySubCat(@PathVariable("subCat") Integer subCat) {
        return topicRepo.findBySubCategory( new Subcategory( subCat ) );
    }

    @GetMapping(value = "/{ID}", produces = "application/json")
    ResponseEntity<Optional<Topic>> findByID(@PathVariable("ID") Integer topicID) {
        return ResponseEntity.ok( topicRepo.findById( topicID ) );
    }

    private Collection<Topicspermissions> fillInfo(Collection<Topicspermissions> topicspermissions) {
        List<Topicspermissions> newList = null;
        if (topicspermissions != null && !topicspermissions.isEmpty()) {
            newList = new ArrayList<>();
            for (Topicspermissions tp : topicspermissions) {
                if (tp.getType() != null && tp.getType().equalsIgnoreCase( "user" )) {
                    Users user = usersRepos.findById( tp.getAssigne() ).orElse( null );

                    if (user != null) {
                        tp.setUser( user );
                        newList.add( tp );

                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug( "user ID {} not found for topic permissions", tp.getAssigne() );
                        }
                    }
                } else {
                    Groups group = groupsRepo.findById( tp.getAssigne() ).orElse( null );
                    if (group != null) {
                        tp.setGroup( group );
                        newList.add( tp );
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug( "group ID {} not found for topic permissions", tp.getAssigne() );
                        }
                    }
                }
            }
        }
        topicspermissions = null;
        return newList;
    }
}
