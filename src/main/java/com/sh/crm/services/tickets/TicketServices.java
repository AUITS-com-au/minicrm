package com.sh.crm.services.tickets;

import com.sh.crm.jpa.entities.TicketHistory;
import com.sh.crm.jpa.entities.Ticketdata;
import com.sh.crm.jpa.repos.tickets.TicketActionsRepo;
import com.sh.crm.jpa.repos.tickets.TicketDataRepo;
import com.sh.crm.jpa.repos.tickets.TicketHistoryRepo;
import com.sh.crm.jpa.repos.tickets.TicketsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
public class TicketServices {

    private static final Logger log = LoggerFactory.getLogger( TicketServices.class );

    @Autowired
    private TicketHistoryRepo ticketHistoryRepo;
    @Autowired
    private TicketsRepo ticketsRepo;

    @Autowired
    private TicketActionsRepo ticketActionsRepo;
    @Autowired
    private TicketDataRepo ticketDataRepo;

    @Async
    @Transactional
    public void logTicketAction(Long ticketID, Integer actionID, boolean enableData, Principal principal) {
        if (log.isTraceEnabled())
            log.trace( "ticket action history log started successfully" );
        if (ticketID != null) {
            if (log.isDebugEnabled())
                log.debug( "logging ticket action {} for ticket {} ", actionID, ticketID );

            //Ticket ticket = ticketsRepo.findOne( ticketID );
            List<Ticketdata> ticketdataList = null;
            if (enableData)
                ticketdataList = ticketDataRepo.findByActionID_ActionIDAndTicketID_IdOrderByCreationDateDesc( actionID, ticketID );
            TicketHistory history = new TicketHistory();
            history.setActionID( actionID );
            history.setTicketID( ticketID );
            history.setCreatedBy( principal.getName() );
            if (ticketdataList != null && ticketdataList.size() > 0) {
                Ticketdata ticketdata = ticketdataList.get( 0 );
                history.setDateTime( ticketdata.getCreationDate() );
                history.setOldTopic( ticketdata.getOldTopic() );
                history.setNewTopic( ticketdata.getNewTopic() );
                history.setOldStatus( ticketdata.getOldStatus() );
                history.setNewStatus( ticketdata.getNewStatus() );

            }

            ticketHistoryRepo.save( history );
            history = null;
            if (log.isDebugEnabled())
                log.debug( "action saved for ticket {}", ticketID );
            ticketdataList = null;
        } else {
            log.error( "error logging ticket action, ticket id is null" );
        }

        if (log.isTraceEnabled())
            log.trace( "ticket action history log completed successfully" );
    }

    @Async
    @Transactional
    public void logTicketHistory(TicketHistory history, Principal principal) {
        if (log.isTraceEnabled())
            log.trace( "ticket history log started successfully" );


        history.setCreatedBy( principal.getName() );
        history.setCreationDate( java.util.Calendar.getInstance().getTime() );
        ticketHistoryRepo.save( history );

        if (log.isDebugEnabled())
            log.debug( "action saved for ticket {}", history.getTicketID() );

        history = null;

        if (log.isTraceEnabled())
            log.trace( "ticket  history log completed successfully" );
    }

   /* private void logTicketAction(Ticket ticket, Ticketactions action,
                                 Integer oldTopic, Integer newTopic,
                                 Integer oldStatus, Integer newStatus,
                                 Principal principal) {
        if (log.isTraceEnabled())
            log.trace( "ticket action history log started successfully" );
        if (ticket != null) {
            if (log.isDebugEnabled())
                log.debug( "logging ticket action {} for ticket {} , old status {} " +
                                "new status {} old topic {} new topic",
                        action != null ? action : null, ticket, oldStatus, newStatus,
                        oldTopic, newTopic );

            TicketHistory history = new TicketHistory();
            history.setActionID( action != null ? action.getActionID() : null );
            history.setTicketID( ticket.getId() );
            history.setCreatedBy( principal != null ? principal.getName() : null );
            history.setOldStatus( oldStatus );
            history.setNewStatus( newStatus );
            history.setOldTopic( oldTopic );
            history.setNewTopic( newTopic );
            ticketHistoryRepo.save( history );
            history = null;
            if (log.isDebugEnabled())
                log.debug( "action saved for ticket {}", ticket.getId() );
        } else {
            log.error( "error logging ticket action, ticket id is null" );
        }

        if (log.isTraceEnabled())
            log.trace( "ticket action history log completed successfully" );
    }
*/
/*
    @Async
    public void logTicketAction(Long ticketId, Integer action,
                                Integer oldTopic, Integer newTopic,
                                Integer oldStatus, Integer newStatus,
                                Principal principal) {

        Ticket ticket = ticketsRepo.findOne( ticketId );
        Ticketactions ticketactions = ticketActionsRepo.findOne( action );

        logTicketAction( ticket, ticketactions, oldTopic, newTopic, oldStatus, newStatus, principal );

    }*/
}
