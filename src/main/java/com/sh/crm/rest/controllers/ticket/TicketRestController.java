package com.sh.crm.rest.controllers.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.exceptions.UserNotAllowedException;
import com.sh.crm.general.holders.SearchTicketsContainer;
import com.sh.crm.general.holders.SearchTicketsResult;
import com.sh.crm.general.holders.TicketHolder;
import com.sh.crm.general.utils.*;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.misc.TopicConfiguration;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.Administrator;
import com.sh.crm.services.tickets.TopicPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Calendar;
import java.util.*;

@RestController
@RequestMapping(value = "tickets", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('TICKET:USER','TICKET:ADMIN','Administrator')")
public class TicketRestController extends BasicController<TicketHolder> {

    @Autowired
    private TopicPermissionsService topicPermissionsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> create(@RequestBody TicketHolder ticketHolder, Principal principal) throws GeneralException {
        if (ticketHolder != null) {
            if (log.isDebugEnabled())
                log.debug( "creating ticket {}", ticketHolder );
            CustomerAccounts accounts = ticketHolder.getCustomerAccount();

            Ticket ticket = ticketHolder.getTicket();
            if (ticket == null)
                throw new GeneralException( Errors.CANNOT_CREATE_OBJECT, "Ticket record is empty" );
            // Topic topic = topicRepo.findOne( ticket.getTopic() );
            if (accounts != null) {
                if (log.isDebugEnabled())
                    log.debug( "adding/updating customer account {} ", accounts );
                // find account first
                CustomerAccounts originalAcct = customerAccountsRepo.checkExistingAccount( accounts.getId(), accounts.getNin(), accounts.getCustomerCIF() );
                if (originalAcct != null) {
                    Utils.copyAccount( accounts, originalAcct );
                    accounts = customerAccountsRepo.save( originalAcct );
                } else {
                    if ((accounts.getCustomerCIF() == null || accounts.getCustomerCIF().isEmpty()) && (accounts.getNin() == null || accounts.getNin().isEmpty()))
                        throw new GeneralException( Errors.CANNOT_CREATE_OBJECT, "Customer Account is NOT Valid" );
                    accounts = customerAccountsRepo.save( accounts );
                }

                if (log.isDebugEnabled())
                    log.debug( "customer account {} added/updated successfully ", accounts );
            } else {
                accounts = customerAccountsRepo.findById( 1l ).orElse( null );
            }
            ticket.setCustomerAccount( accounts );

            Ticketactions ticketaction = new Ticketactions( TicketAction.CREATE );
            if (topicPermissionsService.isAllowedPermission( ticket.getTopic().getId(), principal.getName(), ticketaction )) {
                if (log.isDebugEnabled())
                    log.trace( "User {} is allowed to create a ticket", principal.getName() );
                // ticket.setCustomerAccount( accounts != null ? accounts.getId() : null );
                ticket.setId( Long.parseLong( getNextTicketID( ticket.getTopic().getId() ) ) );
                ticket.setOriginalTopic( ticket.getTopic() );
                ticket.setCurrentStatus( TicketStatus.Opened );

                updateTotalNumberOfSLA( ticket );

                if (ticket.getSourceChannel() == null)
                    ticket.setSourceChannel( 1 );
                ticket.setEscalationCalDate( java.util.Calendar.getInstance().getTime() );

                if (ticket.getPriority() == null)
                    ticket.setPriority( 1 );
                if (ticket.getLanguage() == null)
                    ticket.setLanguage( 1 );
                if (log.isDebugEnabled())
                    log.debug( "persisting ticket  {} into database", ticket );
                ticket = ticketsRepo.save( ticket );

                if (log.isDebugEnabled())
                    log.debug( "ticket {} persisted successfully", ticket );
                log.info( "ticket {} created successfully", ticket.getId() );
                if (log.isDebugEnabled())
                    log.debug( "log ticket action after creation" );
                TicketHistory history = new TicketHistory();
                history.setTicketID( ticket.getId() );
                history.setActionID( new Ticketactions( TicketAction.CREATE ) );
                if (ticketHolder.getExtDataList() != null && !ticketHolder.getExtDataList().isEmpty()) {
                    List<TicketExtData> updatedList = new ArrayList<>( ticketHolder.getExtDataList().size() );
                    for (TicketExtData data : ticketHolder.getExtDataList()) {
                        data.setTicketID( ticket.getId() );
                        data.setId( null );
                        updatedList.add( data );
                    }
                    updatedList = ticketExtDataRepo.saveAll( updatedList );
                    ticket.setTicketExtData( updatedList );
                    if (log.isDebugEnabled())
                        log.debug( "Ticket ID {} extended data created successfuly", ticket.getId() );
                }
                /**
                 * add attachments
                 */
                handleAttachments( ticket.getId(), null, ticketHolder );

                ticketServices.logTicketHistory( history
                        , principal );

                ticketHolder = null;
                ticketaction = null;
                return ResponseEntity.ok( ticketsRepo.findById( ticket.getId() ).orElse( null ) );
            } else {
                return new ResponseEntity( new ResponseCode( Errors.UNAUTHORIZED ), HttpStatus.UNAUTHORIZED );
            }
        }

        return null;
    }

    @GetMapping("lock/{ticketID}/{actionID}")
    ResponseEntity<?> getLock(@PathVariable("ticketID") Long ticket,
                              @PathVariable("actionID") Integer actionID,
                              Principal principal) throws GeneralException {
        if (log.isDebugEnabled())
            log.debug( "acquiring lock for ticket {} user {} action ID {}", ticket, principal.getName(), actionID );
        Ticket ticketObject = null;

        Optional<Ticket> optionalTicket = ticketsRepo.findById( ticket );

        if (!optionalTicket.isPresent())
            throw new GeneralException( Errors.INVALID_TICKET );

        ticketObject = optionalTicket.orElse( null );
        ticketLocksRepo.invalidateExistingUserLocks( principal.getName() );

        if (topicPermissionsService.isAllowedPermission( ticketObject.getTopic().getId(), principal.getName(), new Ticketactions( actionID ) )) {
            //if open ticket for read only no need to make a lock
            TicketHistory history = new TicketHistory();
            history.setTicketID( ticket );
            history.setActionID( new Ticketactions( actionID ) );

            List<Ticketlock> locks = ticketLocksRepo.getByTicketIDAndExpiresOnAfterAndExpiredIsFalse( ticketObject, Calendar.getInstance().getTime() );
            if (locks == null || locks.isEmpty()) {
                Topic topic = topicRepo.findById( ticketObject.getTopic().getId() ).orElse( null );
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

                lock = ticketLocksRepo.save( lock );

                log.info( "Ticket lock has been created {} ", lock.toString() );
                if (log.isDebugEnabled())
                    log.debug( "saving ticket lock action" );
                // action id 10 means ticket locked for editing

                history.setActionID( new Ticketactions( TicketAction.LOCK ) );
                ticketServices.logTicketHistory( history, principal );
                Ticket ticket1 = getTicketFullInfo( lock.getTicketID() );
                lock.setTicketID( ticket1 );
                return ResponseEntity.ok( lock );
            } else {
                if (log.isDebugEnabled())
                    log.debug( "found list of active locks for ticket {} \n {}", ticket, locks.toString() );
                Ticketlock ticketlock = locks.get( 0 );
                ticketlock.setTicketID( null );
                return ResponseEntity.badRequest().body( locks.get( 0 ) );
            }
        }
        return ResponseEntity.badRequest().body( Errors.UNAUTHORIZED );
    }

    @PostMapping("action")
    @PutMapping("action")
    ResponseEntity<?> ticketAction(
            @RequestBody TicketHolder ticketHolder, Principal principal) throws GeneralException {
        if (log.isDebugEnabled())
            log.debug( "Logging ticket action:\n{} triggered by user: {}", ticketHolder, principal.getName() );
        ResponseEntity responseEntity = null;
        // check action first
        if (ticketHolder.getActionID() != null) {
            switch (ticketHolder.getActionID()) {
                case TicketAction.REPLY:
                case TicketAction.CHGDEPT:
                case TicketAction.CLOSE:
                case TicketAction.ONPROGRESS:
                case TicketAction.REOPEN:
                case TicketAction.RESOLVED:
                    handleTicketData( ticketHolder, principal );
                    return ResponseEntity.ok( ticketsRepo.findById( ticketHolder.getTicket().getId() ).orElse( null ) );
                case TicketAction.ASSIGN:
                    Set<Ticket> response = assignTickets( ticketHolder, principal );
                    return ResponseEntity.ok( response );
                case TicketAction.MODIFYINFO:
                    return modifyTicketInfo( ticketHolder.getTicket(), principal );

            }
        } else {
            throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, "Action ID is empty" );
        }
        return responseEntity;
    }

    @Transactional
    Set<Ticket> assignTickets(TicketHolder ticketHolder, Principal principal) throws GeneralException {
        Set<Ticket> successSet = new LinkedHashSet<>();
        if (ticketHolder != null) {
            List<Long> tickets = new ArrayList<>();
            if (ticketHolder.getTicketList() != null && !ticketHolder.getTicketList().isEmpty()) {
                tickets.addAll( ticketHolder.getTicketList() );
            } else if (ticketHolder.getTicket() != null) {
                tickets.add( ticketHolder.getTicket().getId() );
            } else {
                throw new GeneralException( Errors.CANNOT_APPLY_ACTION, "Tickets list or ticket ID is empty" );
            }

            if (ticketHolder.getTargetUser() == null || ticketHolder.getTargetUser().isEmpty()) {
                throw new GeneralException( Errors.CANNOT_APPLY_ACTION, "Users list is empty" );
            }

            for (Long ticketID : tickets) {
                Ticket ticket = ticketsRepo.findById( ticketID ).orElse( null );
                if (ticket == null)
                    continue;
                if (topicPermissionsService.isAllowedPermission( ticket.getTopic().getId(), principal.getName(), TicketAction.ASSIGN )) {

                    try {
                        assignTicket( ticket, ticketHolder.getTargetUser(), principal );
                        log.info( "Ticket {} assigned to user {}", ticket.getId(), ticketHolder.getTargetUser() );
                        successSet.add( ticket );
                    } catch (Exception e) {
                        log.error( "Error Assigning ticket {} to user {} , exception {}", ticket.getId(), ticketHolder.getTargetUser(), e );
                        e.printStackTrace();
                    }
                }
            }

        } else {
            throw new GeneralException( Errors.CANNOT_APPLY_ACTION, "Ticket Holder is empty" );
        }
        ticketHolder = null;
        return successSet;
    }

    @Transactional
    void assignTicket(Ticket ticket, String user, Principal principal) {
        TicketHistory history = new TicketHistory();
        history.setOldAssigne( ticket.getAssignedTo() );
        ticket.setAssignedTo( user );
        ticketsRepo.save( ticket );
        history.setNewAssigne( user );
        history.setActionID( new Ticketactions( TicketAction.ASSIGN ) );
        history.setTicketID( ticket.getId() );
        ticketServices.logTicketHistory( history, principal );
    }

    @Transactional
    void handleTicketData(TicketHolder ticketHolder, Principal principal) throws GeneralException {
        validateTicketLock( ticketHolder );
        Integer actionID = ticketHolder.getActionID();
        Ticketdata ticketdata = ticketHolder.getTicketdata();
        Integer newTopic = ticketHolder.getNewTopic();
        log.debug( "Trying to persist ticket action data" );
        Ticketactions ticketactions = ticketActionsRepo.findById( actionID ).orElse( null );
        Ticket ticket = null;
        try {
            if (log.isDebugEnabled())
                log.debug( "fetching data base to get ticket id first before editing, ticket id {}", ticketHolder.getTicket().getId() );
            Optional<Ticket> optionalTicket = ticketsRepo.findById( ticketHolder.getTicket().getId() );

            if (!optionalTicket.isPresent()) {
                String error = String.format( "Ticket ID %s cannot be found", ticketHolder.getTicket().getId() );
                if (log.isDebugEnabled())
                    log.debug( error );
                throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, error );
            }
            ticket = optionalTicket.orElse( null );

            Integer currentStatus = ticket.getCurrentStatus();
            Topic currentTopic = ticket.getTopic();
            //action ID 8 is
            if (newTopic != null && actionID != TicketAction.CHGDEPT) {
                throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, "Received Value to Change topic to " + newTopic + " and action is not change topic action" );
            }
            if (topicPermissionsService.isAllowedPermission( ticket.getTopic().getId(), principal.getName(), ticketactions )) {
                Status newStatus = ticketactions.getSetStatusTo();
                ticketdata.setTicketID( ticket );

                if (newTopic != null) {
                    ticket.setTopic( topicRepo.getOne( newTopic ) );
                }
                ticketdata.setActionID( ticketactions );
                ticketdata = ticketDataRepo.save( ticketdata );
                if (log.isDebugEnabled())
                    log.debug( "Ticket action saved successfully for ticket {}\n action {} \n data {}",
                            ticket.getId(),
                            ticketactions, ticketdata );
                // updating ticket record
                if (newStatus != null)
                    ticket.setCurrentStatus( newStatus.getId() );

                //update ticket record it self
                updateTicketRecord( ticket, actionID, newTopic, principal );
                if (log.isDebugEnabled()) {
                    log.debug( "Ticket Record Modified {} ", ticket.getId() );
                    log.debug( "Storing Ticket Action {} for ticket {} ", actionID, ticket.getId() );
                }
                TicketHistory history = new TicketHistory();
                history.setActionID( new Ticketactions( actionID ) );
                history.setTicketID( ticket.getId() );
                history.setDataID( ticketdata.getId() );
                history.setOldTopic( currentTopic );
                history.setNewTopic( ticket.getTopic() );
                history.setOldStatus( currentStatus );
                history.setNewStatus( ticket.getCurrentStatus() );
                history.setOldAssigne( ticket.getAssignedTo() );
                ticketServices.logTicketHistory( history, principal );
                handleAttachments( ticket.getId(), ticketdata.getId(), ticketHolder );
                if (log.isDebugEnabled())
                    log.debug( "Ticket action for ticket {}  handled", ticket.getId() );

            } else {
                if (log.isDebugEnabled())
                    log.debug( "User {} is not allowed to do this action {} on ticket {}", principal.getName(),
                            ticketactions.getActionID(),
                            ticket.getId() );
                throw new UserNotAllowedException( "User" + principal.getName() + " is not allowed for this action " );
            }
        } catch (Exception e) {
            LoggingUtils.logStackTrace( log, e, "error" );
            log.error( "An exception happened during ticket action persistence {} ", e );
            throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, e );
        } finally {
            ticket = null;
            ticketactions = null;
        }
    }

    @PostMapping("modifyInfo")
    @Transactional
    ResponseEntity<?> modifyTicketInfo(@RequestBody Ticket ticket, Principal principal) {
        if (log.isDebugEnabled()) {
            log.debug( "received modify ticket request from user {}  , ticket: \n{} ", principal.getName(), ticket );
            log.debug( "getting original ticket from db" );
        }
        Ticket originalTicket = ticketsRepo.findById( ticket.getId() ).orElse( null );
        if (originalTicket == null) {
            if (log.isDebugEnabled())
                log.debug( "cannot find original ticket with ID {} ", ticket.getId() );
            return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_FIND_TICKET ) );
        }
        //action is 7
        if (topicPermissionsService.isAllowedPermission( originalTicket.getTopic().getId(), principal.getName(), new Ticketactions( TicketAction.MODIFYINFO ) )) {
            if (ticket.getCustomerAccount() != null)
                originalTicket.setCustomerAccount( ticket.getCustomerAccount() );
            if (ticket.getLanguage() != null)
                originalTicket.setLanguage( ticket.getLanguage() );
            if (ticket.getSourceChannel() != null)
                originalTicket.setSourceChannel( ticket.getSourceChannel() );
            if (ticket.getSubject() != null)
                originalTicket.setSubject( ticket.getSubject() );
            if (ticket.getTicketType() != null)
                originalTicket.setTicketType( ticket.getTicketType() );

            if (ticket.getTicketExtData() != null && !ticket.getTicketExtData().isEmpty())
                originalTicket.setTicketExtData( ticket.getTicketExtData() );

            TicketHistory history = new TicketHistory();
            history.setTicketID( ticket.getId() );
            history.setActionID( new Ticketactions( TicketAction.MODIFYINFO ) );
            ticketsRepo.save( originalTicket );
            ticketServices.logTicketHistory( history, principal );
            log.info( "Ticket {} has been modified by user {}", ticket.getId(), principal.getName() );

            ticket = null;
            return ResponseEntity.ok( originalTicket );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.UNAUTHORIZED ) );
    }

    @Transactional
    void updateTicketRecord(Ticket ticket, Integer ticketactions,
                            Integer newTopic, Principal principal) {
        Integer currentStatus = ticket.getCurrentStatus();
        switch (currentStatus) {
            case TicketStatus.Closed:
                //closed
                ticket.setClosed( true );
                break;
            case TicketStatus.Resolved:
                //resolved
                ticket.setSolved( true );
                break;
            case TicketStatus.Deleted:
                //deleted
                ticket.setDeleted( true );
                break;
        }
        if (ticketactions != null) {
            if (ticketactions == TicketAction.REOPEN
                    || ticketactions == TicketAction.CHGDEPT) {
                /**
                 * reopen
                 * move to other department
                 */
                ticket.setEscalationCalDate( Calendar.getInstance().getTime() );
                ticket.setLastSLA( null );
                ticket.setCrossedAllSLA( false );
                ticket.setNumberOfCrossedSLA( 0 );
                updateTotalNumberOfSLA( ticket );

            }
        }
        ticketsRepo.save( ticket );
    }


    @PostMapping("list")
    SearchTicketsResult getTickets(@RequestBody SearchTicketsContainer searchTicketsContainer, Principal principal) {
        if (log.isDebugEnabled())
            log.debug( "Getting  list for searching parameters {} ", searchTicketsContainer );
        searchTicketsContainer.setSearchUser( principal.getName() );

        return ticketsRepo.searchTickets( searchTicketsContainer );
    }

    @GetMapping("/data/{ticketID}")
    List<Ticketdata> getTicketData(@PathVariable("ticketID") Long ticketID) {
        return ticketDataRepo.findByTicketIDOrderByCreationDateDesc( new Ticket( ticketID ) );
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
    ResponseEntity<?> getTicketByID(@PathVariable("ticketID") Long ticketID, Principal principal) {
        Ticket ticket = ticketsRepo.findById( ticketID ).orElse( null );
        if (ticket == null)
            return ResponseEntity.badRequest().body( new ResponseCode( Errors.UNAUTHORIZED ) );
        if (topicPermissionsService.isAllowedPermission( ticket.getTopic().getId(), principal.getName(), TicketOperation.READ )) {

            TicketHistory history = new TicketHistory();
            history.setTicketID( ticket.getId() );
            history.setActionID( new Ticketactions( TicketAction.READ ) );
            ticketServices.logTicketHistory( history, principal );

            ticket = getTicketFullInfo( ticket );
            return ResponseEntity.ok( ticket );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.UNAUTHORIZED ) );
    }

    @PostMapping("attachments/data")
    public List<Attachments> getAttachments(@RequestBody List<Long> request) {
        if (request != null && !request.isEmpty()) {
            List<Attachments> attachmentsList = attachmentsRepo.findByIdIn( request );
            if (attachmentsList != null && attachmentsList.size() > 0)
                return attachmentsList;
        }
        return null;
    }

    @PostMapping("attachments/info")
    public List<Attachments> getAttachmentsInfo(@RequestBody List<Long> request) {
        if (request != null && !request.isEmpty()) {
            List<Attachments> attachmentsList = attachmentsRepo.findInfo( request );
            if (attachmentsList != null && attachmentsList.size() > 0) {
                log.debug( "attachments {} ", attachmentsList );
                return attachmentsList;
            }

        }
        return null;
    }


    @GetMapping("/authorizedActions/{topic}")
    public Iterable<?> getTopicPermissions(@PathVariable("topic") Integer topicID, Principal principal) {
        Set<GeneratedTopicPermissions> generatedTopicPermissiont = generatedTopicsPermissionsRepo.getByUserNameAndTopic_Id( principal.getName(), topicID );
        return ticketActionsRepo.findDistinctByEnabledTrueAndActionIDIn( topicPermissionsService.getActionsFromTopicPermission( generatedTopicPermissiont ) );
    }

    /**
     * @param ticket
     * @return Ticket
     */
    private Ticket getTicketFullInfo(Ticket ticket) {
        if (ticket != null) {
            List<TicketExtData> ticketExtDataList = ticketExtDataRepo.findByTicketID( ticket.getId() );
            if (ticketExtDataList != null)
                ticket.setTicketExtData( ticketExtDataList );

            List<Ticketdata> ticketdataList = ticketDataRepo.findByTicketIDOrderByCreationDateDesc( ticket );
            if (ticketdataList != null && !ticketdataList.isEmpty()) {
                List<Ticketdata> newTicketData = new ArrayList<>();
                for (Ticketdata tdata : ticketdataList) {
                    tdata.setAttachmentsList( ticketAttachmentRepo.getAttachmentsByDataID( tdata.getId() ) );
                    newTicketData.add( tdata );
                }
                ticket.setTicketdataList( newTicketData );
            }
            List<Escalationhistory> escalationhistories = escalationHistoryRepo.findByTicketID( ticket );
            if (escalationhistories != null)
                ticket.setEscalationhistoryList( escalationhistories );

            List<TicketHistory> ticketHistoryList = ticketHistoryRepo.findByTicketID( ticket.getId() );
            if (ticketHistoryList != null)
                ticket.setTicketHistoryList( ticketHistoryList );

            List<Long> attachmentsList = ticketAttachmentRepo.getAttachmentsIDByTicketID( ticket.getId() );

            if (attachmentsList != null && attachmentsList.size() > 0) {
                ticket.setAttachmentsList( attachmentsList );
            }
        }

        return ticket;
    }

    /**
     * This method validates the ticket lock using ticketholder container
     *
     * @param ticketHolder
     * @throws GeneralException
     */
    private void validateTicketLock(TicketHolder ticketHolder) throws GeneralException {
        if (ticketHolder == null || ticketHolder.getLockID() == null || ticketHolder.getTicket() == null
                || ticketHolder.getTicket().getId() == null)
            throw new GeneralException( Errors.INVALID_TICKET_LOCK );

        validateTicketLock( ticketHolder.getLockID(), ticketHolder.getTicket().getId() );
    }

    /**
     * This method validates ticket lock using lock ID and ticket ID
     * Use this service if you have the ticket ID and lock ID
     *
     * @param ticketLock
     * @param ticketID
     * @throws GeneralException
     */
    private void validateTicketLock(Long ticketLock, Long ticketID) throws GeneralException {
        Ticketlock ticketlock = ticketLocksRepo.findById( ticketLock ).orElse( null );
        if (ticketlock == null)
            throw new GeneralException( Errors.INVALID_TICKET_LOCK );

        if (!ticketlock.getTicketID().getId().equals( ticketID )) {
            log.info( "ticket id cannot match with the lock ticket ID, target ticket id {} received ticket ID {}", ticketID, ticketlock.getTicketID().getId() );
            throw new GeneralException( Errors.INVALID_TICKET_LOCK );
        }
        // check if lock still valid
        if (ticketlock.getExpiresOn().before( Calendar.getInstance().getTime() )) {
            if (log.isDebugEnabled())
                log.debug( "Ticket lock {} has expired on {} will try to accquire another lock", ticketlock.getLockID(), ticketlock.getExpiresOn() );
            throw new GeneralException( Errors.EXPIRED_TICKET_LOCK );
        }
    }

    @Transactional
    void handleAttachments(long ticketID, Long dataID, TicketHolder ticketHolder) throws GeneralException {
        if (ticketHolder.getAttachments() != null && !ticketHolder.getAttachments().isEmpty()) {
            if (log.isDebugEnabled())
                log.debug( "ticket attachments found, Linking ticket with attachments {}", ticketHolder.getAttachments() );

            for (Long attachID : ticketHolder.getAttachments()) {
                if (attachID != null) {
                    TicketAttachments ticketAttachments = new TicketAttachments();
                    ticketAttachments.setTicketID( ticketID );
                    if (dataID != null)
                        ticketAttachments.setDataID( dataID );
                    ticketAttachments.setAttachmentID( attachID );
                    ticketAttachmentRepo.save( ticketAttachments );
                }
            }
        }
    }

    private Ticket updateTotalNumberOfSLA(Ticket ticket) {
        if (ticket.getTopic() == null || ticket.getTopic().getId() == null)
            ticket.setNumberOfSLA( 0 );

        Integer totalSLA = topicSlaRepo.countDistinctByTopicID( ticket.getTopic() );
        if (totalSLA != null) {
            ticket.setNumberOfSLA( totalSLA );
        }
        return ticket;
    }
}
