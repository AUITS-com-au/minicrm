package com.sh.crm.services.tickets;

import com.sh.crm.general.utils.TicketOperation;
import com.sh.crm.general.utils.Utils;
import com.sh.crm.jpa.entities.Ticketactions;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.repos.tickets.GeneratedTopicsPermissionsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicPermissionsService {
    private static final Logger log = LoggerFactory.getLogger( TopicPermissionsService.class );
    @Autowired
    private GeneratedTopicsPermissionsRepo tp;

    public Boolean isAllowedPermission(Integer topic, String username, Integer ticketaction) {
        return isAllowedPermission( topic, username, new Ticketactions( ticketaction ) );

    }

    public Boolean isAllowedPermission(Integer topic, String username, Ticketactions ticketactions) {
        /**
         * Actions mapping must be same as database
         * 1 resolved
         * 2 close
         * 3 reopen
         * 4 create
         * 5 On Progress
         * 6 hold
         * 7 Modify Information
         * 8 change department
         * 9 read
         * 10 lock
         * 11 assign
         */

        if (topic == null) {
            log.debug( "topics permissions are null, return false" );
            return false;
        }

        if (log.isDebugEnabled())
            log.debug( "Validating permissions of user {} for topic {} and action {}", username, topic, ticketactions );

        String operation = Utils.getOperationFromAction( ticketactions );
        if (operation == null) {
            log.info( "cannot map ticket action {} with proper operation", ticketactions.toString() );
            return false;
        } else {
            if (log.isDebugEnabled())
                log.debug( "ticket action {} has been translated to ticket operation {}", ticketactions, operation );
        }

        return isAllowedPermission( topic, username, operation );
    }


    public boolean isAllowedPermission(Integer topicID, String username, String operation) {
        Topic topic = new Topic( topicID );
        Boolean returnValue = isAllowedPermissionWrap( topic, username, operation );
        if (returnValue == null || !returnValue)
            return false;
        return true;
    }

    public Boolean isAllowedPermissionWrap(Topic topic, String username, String operation) {
        if (log.isDebugEnabled())
            log.debug( "Validating permissions of user {} for topic {} and operation {}", username, topic, operation );

        switch (operation) {
            case TicketOperation.CLOSE:
                return tp.canClose( username, topic );
            case TicketOperation.CREATE:
                return tp.canCreate( username, topic );
            case TicketOperation.DELETE:
                return tp.canDelete( username, topic );
            case TicketOperation.READ:
                return tp.canRead( username, topic );
            case TicketOperation.REPLY:
                return tp.canReply( username, topic );
            case TicketOperation.REPORT:
                return tp.canRunReport( username, topic );
            case TicketOperation.RESOLVE:
                return tp.canResolve( username, topic );
            case TicketOperation.SUBSCRIBE:
                return tp.canSubcribe( username, topic );
            case TicketOperation.REOPEN:
                return tp.canReopen( username, topic );
            case TicketOperation.MODIFYINFO:
                return tp.canModify( username, topic );
            case TicketOperation.CHGDPT:
                return tp.canChangeDepartment( username, topic );
            case TicketOperation.ASSIGN:
                return tp.canAssign( username, topic );
            default:
                return false;
        }
    }
}
