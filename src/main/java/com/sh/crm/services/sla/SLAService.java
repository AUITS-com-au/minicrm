package com.sh.crm.services.sla;

import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.general.utils.TicketAction;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.jpa.repos.global.CalendarRepo;
import com.sh.crm.jpa.repos.tickets.*;
import com.sh.crm.services.notification.service.NotificationService;
import com.sh.crm.services.tickets.TicketServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SLAService {
    private static final Logger logger = LoggerFactory.getLogger( SLAService.class );
    private final static long slaPeriodTime = 60000l;
    @Autowired
    private EscalationHistoryRepo escalationHistoryRepo;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SlaRepo slaRepo;
    @Autowired
    private TicketServices ticketServices;
    @Autowired
    private TicketsRepo ticketsRepo;
    @Autowired
    private TicketActionsRepo ticketActionsRepo;
    //@Autowired private
    @Autowired
    private TopicSlaRepo topicSlaRepo;
    @Autowired
    private CalendarRepo calendarRepo;

    @Scheduled(fixedRate = slaPeriodTime)
    public void startSLACheck() throws Exception {
        logger.debug( "................SLA Service Started................" );

        logger.info( "SLA Service checking for Date is working date first" );

        if (calendarRepo.findByIsHolidayFalseAndIsWeekdayTrueAndDate( Date.from( LocalDate.now().atStartOfDay( ZoneId.systemDefault() ).toInstant() ) ) == null) {
            logger.info( "Today Is NOT a working day, SLA service will not run" );

            return;
        }

        ReentrantLock lock = new ReentrantLock();

        if (lock.tryLock( slaPeriodTime, TimeUnit.MILLISECONDS )) {

            logger.debug( "................Locking for {} Milli-Second................", slaPeriodTime );
            try {
                List<EsclatedTickets> escalatedTicketsList = slaRepo.getEscalatedTickets();
                if (escalatedTicketsList != null && !escalatedTicketsList.isEmpty()) {
                    if (logger.isDebugEnabled())
                        logger.debug( "The following tickets " +
                                "has been escalated \n{}", escalatedTicketsList.toString() );

                    for (EsclatedTickets esclatedTicket : escalatedTicketsList) {
                        try {
                            Ticketactions ticketactions = ticketActionsRepo.findById( TicketAction.ESC ).orElse( null );
                            Ticket ticket = ticketsRepo.findById( esclatedTicket.getTicketID().longValue() ).orElse( null );
                            if (ticket == null) {
                                logger.error( "ticket object is null {}", esclatedTicket.getTicketID() );
                                continue;
                            }
                            logger.info( "SLA process started for {}", esclatedTicket.toString() );
                            Topicsla topicsla = topicSlaRepo.findById( esclatedTicket.getTopicSLA() ).orElse( null );

                            TicketHistory history = new TicketHistory();
                            history.setActionID( new Ticketactions( ticketactions.getActionID() ) );
                            history.setTicketID( ticket.getId() );
                            history.setOldStatus( ticket.getCurrentStatus() );
                            if (ticketactions.getSetStatusTo() != null) {
                                ticket.setCurrentStatus( ticketactions.getSetStatusTo().getId() );
                                history.setNewStatus( ticketactions.getSetStatusTo().getId() );
                            }
                            ticket.setModificationDate( Calendar.getInstance().getTime() );
                            ticket.setModifiedBy( "system" );
                            // calculate number of crossed SLA
                            Integer totalSLA = topicSlaRepo.countDistinctByTopicID( ticket.getTopic() );
                            if (totalSLA != null) {
                                ticket.setNumberOfSLA( totalSLA );
                            }
                            Integer currentNumberOfCrossedSLA = ticket.getNumberOfCrossedSLA();
                            if (currentNumberOfCrossedSLA == null)
                                currentNumberOfCrossedSLA = 0;
                            ++currentNumberOfCrossedSLA;
                            ticket.setNumberOfCrossedSLA( currentNumberOfCrossedSLA );
                            // calculate total number of crossed time in hours
                            Long totalNumberOfTime = ticket.getTotalCrossedTime();
                            if (totalNumberOfTime == null)
                                totalNumberOfTime = 0l;

                            Sla sla = topicsla.getSlaid();
                            if (sla != null) {
                                totalNumberOfTime += sla.getTime();
                                ticket.setTotalCrossedTime( totalNumberOfTime );
                                sla = null;
                            }
                            /**
                             * Calculate LAST SLA
                             */
                            Integer maxSLALevel = topicSlaRepo.getMaxSlaLevel( ticket.getTopic() );
                            if (maxSLALevel == null)
                                maxSLALevel = 1;
                            if (topicsla.getSLALevel() >= maxSLALevel)
                                ticket.setCrossedAllSLA( true );
                            //ticket.setLastSLA( esclatedTicket.getSlaID() );
                            //ticket.setEscalationCalDate( Calendar.getInstance().getTime() );
                            Escalationhistory escalationhistory = new Escalationhistory();
                            escalationhistory.setEscDateTime( Calendar.getInstance().getTime() );
                            escalationhistory.setTopicSLA( topicsla );
                            escalationhistory.setTicketID( ticket );
                            escalationhistory = escalationHistoryRepo.save( escalationhistory );
                            history.setEscalationHisID( escalationhistory.getId() );
                            ticketsRepo.save( ticket );
                            ticketServices.logTicketHistory( history, null );
                            logger.info( "SLA process completed for {}", esclatedTicket.toString() );
                            escalationhistory = null;
                            history = null;
                            ticket = null;
                        } catch (Exception e) {
                            LoggingUtils.logStackTrace( logger, e, "error" );
                            e.printStackTrace();
                        }
                    }
                }

            } catch (Exception e) {
                LoggingUtils.logStackTrace( logger, e, "error" );
            } finally {
                lock.unlock();
                logger.debug( "................SLA Service Unlock................" );
            }
        }
        logger.debug( "................SLA Service Completed.............." );
    }

}
