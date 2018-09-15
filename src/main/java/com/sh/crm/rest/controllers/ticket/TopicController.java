package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.jpa.repos.tickets.TopicRepo;
import com.sh.crm.jpa.repos.tickets.TopicsPermissionsRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import com.sh.crm.security.annotation.TicketsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("topics")
public class TopicController extends BasicController<Topic> {

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private TopicsPermissionsRepo topicsPermissionsRepo;

    @Override
    @TicketsAdmin
    public ResponseEntity<?> create(Topic topic) throws GeneralException {
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
    public ResponseEntity<?> edit(Topic topic) throws GeneralException {
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

    @Override
    @TicketsAdmin
    public ResponseEntity<?> delete(Topic topic) throws GeneralException {
        return null;
    }

    @Override
    @TicketsAdmin
    protected Iterable<?> all() {
        return topicRepo.findAll();
    }

    @Override
    @TicketsUser
    protected Iterable<?> authorizedList() {
        return topicRepo.findAll();
    }

    ResponseEntity<?> createTopicPermissions(@RequestBody Topicspermissions topicspermissions) throws GeneralException {

        if (log.isDebugEnabled())
            log.debug( "Received request to add/modify topics permissions" );
        if (topicspermissions != null) {


        }
        throw new GeneralException( Errors.CANNOT_CREATE_OBJECT );
    }
}
