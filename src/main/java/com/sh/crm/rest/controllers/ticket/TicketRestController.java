package com.sh.crm.rest.controllers.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.TicketHolder;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.general.utils.TicketOperation;
import com.sh.crm.general.utils.Utils;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.misc.TopicConfiguration;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.Administrator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "tickets", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('TICKET:USER','TICKET:ADMIN','Administrator')")
public class TicketRestController extends BasicController<TicketHolder> {

    @Override
    @Transactional
    public ResponseEntity<?> create(@RequestBody TicketHolder ticketHolder, Principal principal) throws GeneralException {
        try {
            if (ticketHolder != null) {
                if (log.isDebugEnabled())
                    log.debug( "creating ticket {}", ticketHolder );
                CustomerAccounts accounts = ticketHolder.getCustomerAccount();
                if (accounts != null) {
                    if (log.isDebugEnabled())
                        log.debug( "adding/updating customer account {} ", accounts );
                    customerAccountsRepo.save( accounts );
                    if (log.isDebugEnabled())
                        log.debug( "customer account {} added/updated successfully ", accounts );
                }
                Ticket ticket = ticketHolder.getTicket();
                if (ticket == null)
                    throw new GeneralException( Errors.CANNOT_CREATE_OBJECT, "Ticket record is empty" );

                Topic topic = topicRepo.findOne( ticket.getTopic() );

                Set<Topicspermissions> topicspermissions = getCurrentUserTopicsPermissionsByTopicID( topic.getId() );
                if (Utils.isAllowedPermission( topicspermissions, new Ticketactions( 4 ) )) {
                    if (log.isDebugEnabled())
                        log.debug( "User {} is allowed to create a ticket", principal.getName() );
                    ticket.setCustomerAccount( accounts != null ? accounts.getId() : null );
                    ticket.setId( Long.parseLong( getNextTicketID( ticket.getTopic() ) ) );
                    ticket.setOriginalTopic( ticket.getTopic() );
                    ticket.setEscalationCalDate( java.util.Calendar.getInstance().getTime() );
                    if (log.isDebugEnabled())
                        log.debug( "persisting ticket  {} into database", ticket );
                    ticket = ticketsRepo.save( ticket );


                    if (log.isDebugEnabled())
                        log.debug( "ticket {} persisted successfully", ticket );
                    log.info( "ticket {} created successfully", ticket.getId() );
                    if (ticketHolder.getExtDataList() != null && !ticketHolder.getExtDataList().isEmpty()) {
                        List<TicketExtData> updatedList = new ArrayList<>( ticketHolder.getExtDataList().size() );
                        for (TicketExtData data : ticketHolder.getExtDataList()) {
                            data.setTicketID( ticket );
                            data.setId( null );
                            updatedList.add( data );
                        }
                        updatedList = ticketExtDataRepo.save( updatedList );
                        ticket.setTicketExtData( updatedList );
                        if (log.isDebugEnabled())
                            log.debug( "Ticket ID {} extended data created successfuly", ticket.getId() );
                    }
                    ticketHolder = null;
                    return ResponseEntity.ok( ticket );
                } else {
                    return new ResponseEntity( new ResponseCode( Errors.UNAUTHORIZED ), HttpStatus.UNAUTHORIZED );
                }
            }
        } catch (Exception e) {
            LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            throw new GeneralException( Errors.CANNOT_CREATE_OBJECT, e );

        }
        throw new GeneralException( Errors.CANNOT_CREATE_OBJECT );
    }


    @GetMapping("lock/{ticketID}/{actionID}")
    ResponseEntity<?> getLock(@PathVariable("ticketID") Long ticket,
                              @PathVariable("actionID") Integer actionID,
                              Principal principal) throws GeneralException {
        if (log.isDebugEnabled())
            log.debug( "acquiring lock for ticket {} user {} action ID {}", ticket, principal.getName(), actionID );
        Ticket ticketObject = ticketsRepo.findOne( ticket );
        if (ticketObject == null)
            throw new GeneralException( Errors.INVALID_TICKET );


        // ticketLocksRepo.invalidateExistingUserLocks( principal.getName() );
        Set<Topicspermissions> topicspermissions = getCurrentUserTopicsPermissionsByTopicID( ticketObject.getTopic() );

        if (Utils.isAllowedPermission( topicspermissions, new Ticketactions( actionID ) )) {
            List<Ticketlock> locks = ticketLocksRepo.getByTicketIDAndExpiresOnAfterAndExpiredIsFalse( ticketObject, Calendar.getInstance().getTime() );
            if (locks == null || locks.isEmpty()) {
                Topic topic = topicRepo.findOne( ticketObject.getTopic() );
                ObjectMapper objectMapper = new ObjectMapper();
                TopicConfiguration configuration = null;
                try {
                    configuration = objectMapper.readValue( topic.getConfiguration(), TopicConfiguration.class );
                } catch (IOException e) {
                    if (log.isDebugEnabled())
                        log.debug( "unable to deserialize topic configuration {} to object, try to search in global configuration", topic.getConfiguration() );
                }
                if (configuration == null) {
                    configuration = new TopicConfiguration();
                    configuration.setLockTime( 10 );
                }

                Ticketlock lock = new Ticketlock();
                lock.setTicketID( ticketObject );
                lock.setExpired( false );
                Calendar calendar = Calendar.getInstance();
                lock.setDateTime( Calendar.getInstance().getTime() );
                calendar.add( Calendar.MINUTE, configuration.getLockTime() );
                lock.setExpiresOn( calendar.getTime() );
                lock.setUserID( principal.getName() );
                ticketLocksRepo.save( lock );
                log.info( "Ticket lock has been created {} ", lock.toString() );
                return ResponseEntity.ok( lock );
            } else {
                if (log.isDebugEnabled())
                    log.debug( "found list of active locks for ticket {} \n {}", ticket, locks.toString() );
                Ticketlock ticketlock = locks.get( 0 );
                ticketlock.setTicketID( null );
                ResponseEntity.badRequest().body( locks.get( 0 ) );
            }

        }
        topicspermissions = null;
        return ResponseEntity.badRequest().body( Errors.UNAUTHORIZED );
    }

    @PostMapping("action/{ticketID}/{lockID}")
    ResponseEntity<?> addReply(@PathVariable("lockID") Long lockID, @PathVariable("ticketID") Long
            ticketID, @RequestBody Ticketdata ticketdata, Principal principal) throws GeneralException {
        if (log.isDebugEnabled())
            log.debug( "Logging ticket reply:\n{} triggered by user: {}, using lock ID {}", ticketdata, principal.getName(), lockID );

        //validating lock first
        Ticketlock ticketlock = ticketLocksRepo.findOne( lockID );
        if (ticketlock == null)
            throw new GeneralException( Errors.INVALID_TICKET_LOCK );

        if (ticketlock.getTicketID().getId() != lockID) {
            log.info( "ticket id cannot match with the lock ticket ID, target ticket id {} received ticket ID", ticketID, ticketlock.getTicketID().getId() );
            throw new GeneralException( Errors.INVALID_TICKET_LOCK );
        }
        // check if lock still valid
        if (ticketlock.getExpiresOn().before( Calendar.getInstance().getTime() )) {
            if (log.isDebugEnabled())
                log.debug( "Ticket lock {} has expired on {} will try to accquire another lock", lockID, ticketlock.getExpiresOn() );
            throw new GeneralException( Errors.EXPIRED_TICKET_LOCK );
        }

        if (ticketdata != null) {
            if (ticketdata.getActionID() == null)
                throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, "Action ID is empty" );
            if (!ticketActionsRepo.exists( ticketdata.getActionID().getActionID() ))
                throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, "Action ID" + ticketdata.getActionID().getActionID() + " cannot be found" );


            log.debug( "Trying to persist ticket action data" );
            try {
                if (log.isDebugEnabled())
                    log.debug( "fetching data base to get ticket id first before editing, ticket id {}", ticketID );
                Ticket ticket = ticketsRepo.findOne( ticketID );
                if (ticket == null) {
                    String error = String.format( "Ticket ID %s cannot be found", ticketID );
                    if (log.isDebugEnabled())
                        log.debug( error );
                    throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, error );
                }

                Ticketactions ticketactions = ticketActionsRepo.findOne( ticketdata.getActionID().getActionID() );
                if (ticketactions == null) {
                    String error = String.format( "Ticket action %s cannot be found for edit ticket data request", ticketdata.getActionID().getActionID() );
                    if (log.isDebugEnabled())
                        log.debug( error );
                    throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, error );
                }
                Set<Topicspermissions> tps = getCurrentUserTopicsPermissionsByTopicID( ticket.getTopic() );

                if (Utils.isAllowedPermission( tps, ticketactions )) {
                    Status newStatus = ticketactions.getSetStatusTo();
                    ticketdata.setTicketID( ticket );
                    ticketdata.setNewStatus( newStatus.getId() );
                    ticketdata.setOldStatus( ticket.getCurrentStatus() );
                    ticketdata.setOldTopic( ticket.getTopic() );
                    ticketdata.setNewTopic( ticket.getTopic() );
                    tikcetDataRepo.save( ticketdata );
                    modifyTicket( ticket, ticketactions, ticketdata );
                    log.debug( "Ticket action saved successfully for ticket {}\n action {} \n data {}",
                            ticket.getId(),
                            ticketactions, ticketdata );
                    return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
                } else {
                    if (log.isDebugEnabled())
                        log.debug( "User {} is not allowed to perform this action {} on ticket {}", principal.getName(),
                                ticketactions.getActionID(),
                                ticket.getId() );
                    return new ResponseEntity( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ), HttpStatus.UNAUTHORIZED );
                }
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, "error" );
                log.error( "An exception happened during ticket action persistence {} ", e );
                throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, e );
            }
        } else {
            return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ) );
        }
    }

    @PostMapping("modify/{lockID}")
    ResponseEntity<?> modifyTicket(@RequestBody Ticket ticket, Principal principal) {
        if (log.isDebugEnabled()) {
            log.debug( "received modify ticket request from user {}  , ticket: \n{} ", principal.getName(), ticket );
            log.debug( "getting original ticket from db" );
        }


        Ticket originalTicket = ticketsRepo.findOne( ticket.getId() );
        if (originalTicket == null) {
            if (log.isDebugEnabled())
                log.debug( "cannot find original ticket with ID {} ", ticket.getId() );
            return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_FIND_TICKET ) );
        }
        Set<Topicspermissions> topicspermissions = getCurrentUserTopicsPermissionsByTopicID( originalTicket.getTopic() );
        //action is 7
        if (Utils.isAllowedPermission( topicspermissions, new Ticketactions( 7 ) )) {
            originalTicket.setCustomerAccount( ticket.getCustomerAccount() );
            originalTicket.setLanguage( ticket.getLanguage() );
            originalTicket.setSourceChannel( ticket.getSourceChannel() );
            originalTicket.setSubject( ticket.getSubject() );
            originalTicket.setTicketType( ticket.getTicketType() );
            ticketsRepo.save( originalTicket );
            log.info( "Ticket {} has been modified by user {}", ticket.getId(), principal.getName() );
            originalTicket = null;
            ticket = null;
            return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.UNAUTHORIZED ) );

    }

    private void modifyTicket(Ticket ticket, Ticketactions ticketactions, Ticketdata ticketdata) {
        String operation = Utils.getOperationFromAction( ticketactions );
        if (log.isDebugEnabled()) {
            log.debug( "ticket operation is {} ", operation );
            log.debug( "ticket {} will be modified to new status {}", ticket.getId(), ticketactions.getSetStatusTo().getId() );
        }
        ticket.setCurrentStatus( ticketactions.getSetStatusTo().getId() );
        ticket.setLastTicketData( ticketdata.getId() );
        switch (operation) {
            case TicketOperation.CLOSE:
                ticket.setClosed( true );
                break;
            case TicketOperation.RESOLVE:
                ticket.setSolved( true );
                break;
            case TicketOperation.DELETE:
                ticket.setDeleted( true );
                break;
        }
        ticketsRepo.save( ticket );
    }

    @GetMapping("list/{page}/{size}")
    Page<Ticket> getPagingList(@PathVariable("page") int page, @PathVariable("size") int size) {
        if (log.isDebugEnabled())
            log.debug( "Getting page of tickets with size {} , page number {} ", size, page );
        return ticketsRepo.findByDeletedFalse( new PageRequest( page, size, Sort.Direction.ASC, "priority" ) );
    }

    @GetMapping("/data/{ticketID}")
    List<Ticketdata> getTicketData(@PathVariable("ticketID") Long ticketID) {
        return tikcetDataRepo.findByTicketIDOrderByCreationDateDesc( new Ticket( ticketID ) );
    }

    @Override
    public ResponseEntity<?> edit(TicketHolder ticket, Principal principal) throws GeneralException {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(TicketHolder ticket, Principal principal) throws GeneralException {
        return null;
    }

    @Override
    @Administrator
    protected Iterable<?> all() {
        return ticketsRepo.findAll();
    }

    @GetMapping("/{ticketID}")
    Ticket getTicketByID(@PathVariable("ticketID") Long ticketID) {
        return ticketsRepo.findOne( ticketID );
    }
}
