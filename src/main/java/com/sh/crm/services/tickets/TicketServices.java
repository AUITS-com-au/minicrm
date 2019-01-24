package com.sh.crm.services.tickets;

import com.sh.crm.jpa.entities.TicketHistory;
import com.sh.crm.jpa.repos.tickets.TicketActionsRepo;
import com.sh.crm.jpa.repos.tickets.TicketDataRepo;
import com.sh.crm.jpa.repos.tickets.TicketHistoryRepo;
import com.sh.crm.jpa.repos.tickets.TicketsRepo;
import com.sh.crm.services.notification.NotificationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;
import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class TicketServices {

    private static final Logger log = LoggerFactory.getLogger( TicketServices.class );
    @Autowired
    private EventBus eventBus;
    @Autowired
    private TicketHistoryRepo ticketHistoryRepo;
    @Autowired
    private TicketsRepo ticketsRepo;
    @Autowired
    private TicketActionsRepo ticketActionsRepo;
    @Autowired
    private TicketDataRepo ticketDataRepo;

    protected void notifyAction(long actionID) {
        NotificationData notificationData = new NotificationData( actionID );
        eventBus.notify( "NC", Event.wrap( notificationData ) );
    }

    @Async
    @Transactional
    public void logTicketHistory(TicketHistory history, Principal principal) {
        if (log.isTraceEnabled())
            log.trace( "ticket history log started successfully" );

        history.setCreatedBy( principal.getName() );
        history.setCreationDate( java.util.Calendar.getInstance().getTime() );
        history = ticketHistoryRepo.save( history );

        if (log.isDebugEnabled())
            log.debug( "action saved for ticket {}", history.getTicketID() );

        notifyAction( history.getId() );
        history = null;
        if (log.isTraceEnabled())
            log.trace( "ticket  history log completed successfully" );
    }
}
