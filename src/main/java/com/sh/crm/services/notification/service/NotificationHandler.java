package com.sh.crm.services.notification.service;

import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.*;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.jpa.repos.notifications.EmailPref;
import com.sh.crm.services.basic.BasicService;
import com.sh.crm.services.notification.service.impl.NotificationStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationHandler extends BasicService {
    @Async
    public void handleAction(Long actionID) {
        try {
            if (logger.isDebugEnabled())
                logger.debug( "handling ticket history action with ID {}", actionID );
            TicketHistory history = historyRepo.findById( actionID ).orElse( null );
            String assignedUser = null;
            if (history != null && history.getActionID() != null) {
                if (history.getActionID() == TicketAction.ESC) {
                    handleSLA( history );
                    return;
                }
                String operation = Utils.getOperationFromAction( history.getActionID() );
                String template = null;
                Integer templateID = null;

                int notificationStatus = 0;
                switch (history.getActionID()) {
                    case TicketAction
                            .MODIFYINFO:
                    case TicketAction.ONPROGRESS:
                    case TicketAction.REOPEN:
                    case TicketAction.REPLY:
                    case TicketAction.RESOLVED:
                    case TicketAction.HOLD:
                        notificationStatus = NotificationStatus.OnUpdate;
                        template = globalConfigRepo.findValueByProperty( EmailTemplatName.UPDATE );
                        break;
                    case TicketAction.CREATE:
                        notificationStatus = NotificationStatus.OnCreate;
                        template = globalConfigRepo.findValueByProperty( EmailTemplatName.CREATE );
                        break;
                    case TicketAction.CHGDEPT:
                        notificationStatus = NotificationStatus.OnCreate;
                        template = globalConfigRepo.findValueByProperty( EmailTemplatName.CHGDEPT );
                        // create /changed depart
                        break;
                    case TicketAction.CLOSE:
                        notificationStatus = NotificationStatus.OnClose;
                        template = globalConfigRepo.findValueByProperty( EmailTemplatName.UPDATE );
                        break;
                    case TicketAction.ASSIGN:
                        notificationStatus = NotificationStatus.OnAssign;
                        template = globalConfigRepo.findValueByProperty( EmailTemplatName.ASSIGN );
                        assignedUser = history.getNewAssigne();
                        break;
                    default:
                        logger.debug( "action {} doesn't need notification, ignore it", history.getActionID() );
                }
                try {
                    templateID = Integer.parseInt( template );
                } catch (Exception e) {
                    logger.error( "template ID for template {} is not an Integer value please set a template as no emails will be generated".toUpperCase(), EmailTemplatName.UPDATE );
                    return;
                }
                //now get list of notified users/emails
                Set<EmailPref> emailPrefSet = getNotifiedEmailsForTicketAction( history.getTicketID(), notificationStatus, assignedUser );
                if (emailPrefSet != null && !emailPrefSet.isEmpty()) {
                    createEmails( emailPrefSet, templateID, history.getTicketID(), history, null, operation );
                }
            }
            //history is null  or action id is null so nothing to do....
        } catch (Exception e) {
            LoggingUtils.logStackTrace( logger, e, "error" );
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void handleSLA(TicketHistory history) {
        try {
            if (logger.isDebugEnabled())
                logger.debug( "handling ticket SLA  for ticket ID {}", history.getTicketID() );

            Escalationhistory escalationhistory = escalationHistoryRepo.findById( history.getEscalationHisID() ).orElse( null );
            if (escalationhistory == null) {
                logger.error( "error handling escalation for ticket ID {} because escalation history is null", history.getTicketID() );
                return;
            }

            Ticket ticket = escalationhistory.getTicketID();


            if (ticket != null) {
                String operation = TicketOperation.ESC;
                String template = null;
                Integer templateID = null;

                int notificationStatus = NotificationStatus.OnSLA;
                template = globalConfigRepo.findValueByProperty( EmailTemplatName.SLA );

                try {
                    templateID = Integer.parseInt( template );
                } catch (Exception e) {
                    logger.error( "template ID for template {} is not an Integer value please set a template as no emails will be generated".toUpperCase(), EmailTemplatName.SLA );
                    return;
                }
                //now get list of notified users/emails
                List<EmailPref> emailPrefSet = getNotifiedEmailsForSLA( escalationhistory.getTopicSLA(), ticket );
                if (emailPrefSet != null && !emailPrefSet.isEmpty()) {
                    createEmails( emailPrefSet, templateID, ticket.getId(), history, escalationhistory, operation );
                }

            }
            //history is null  or action id is null so nothing to do....
        } catch (Exception e) {
            LoggingUtils.logStackTrace( logger, e, "error" );
        }
    }


    private void createEmails(Collection<EmailPref> emailPrefSet, Integer templateID, Long ticketID, TicketHistory histroy, Escalationhistory escalationhistory, String operation) {
        Emailtemplates emailtemplates = null;
        Integer actionID = (histroy == null) ? 0 : histroy.getActionID();
        try {
            emailtemplates = formatTemplate( templateID, ticketID, escalationhistory == null ? null : escalationhistory.getTopicSLA(), null, operation );
        } catch (Exception e) {
            LoggingUtils.logStackTrace( logger, e, "error" );
            return;
        }
        Emailmessage emailmessage = new Emailmessage();
        emailmessage.setEmailTitle( emailtemplates.getTemplateTitle() );
        emailmessage.setEmailMessage( emailtemplates.getTemplateData() );
        List<Long> attachmentsList = null;
        if (histroy != null && histroy.getDataID() != null) {
            attachmentsList = ticketAttachmentsRepo.getAttachmentsByDataID( histroy.getDataID() );
        } else if (ticketID != null && (histroy.getActionID() == TicketAction.CREATE || histroy.getActionID() == TicketAction.CHGDEPT || histroy.getActionID() == TicketAction.ASSIGN)) {
            attachmentsList = ticketAttachmentsRepo.getAttachmentsIDByTicketID( ticketID );
        }
        if (attachmentsList != null && !attachmentsList.isEmpty()) {
            String attachListStr = attachmentsList.stream().map( v -> v.toString() ).collect( Collectors.joining( "," ) );
            emailmessage.setAttachmentsID( attachListStr );
        }
        emailmessage = emailMessageRepo.save( emailmessage );
        for (EmailPref emailPref : emailPrefSet) {
            Emailhistory emailhistory = new Emailhistory();
            emailhistory.setEmailMessage( emailmessage );
            emailhistory.setCreationDate( Calendar.getInstance().getTime() );
            emailhistory.setEmailID( emailPref.getEmailID() );
            emailhistory.setStatus( emailPref.isDisabled() ? 1000 : null );
            if (actionID != null) {
                Ticketactions action = ticketActionsRepo.findById( actionID ).orElse( null );
                logger.debug( "ticket action {}", action );
                if (action != null && !StringUtils.isEmpty( action.getEnglishLabel() ))
                    emailhistory.setSendingOn( action.getEnglishLabel() );
                action = null;
            } else {
                emailhistory.setSendingOn( operation );
            }

            if (actionID != null)
                emailhistory.setType( "Action Email" );
            else if (operation != null) {
                emailhistory.setType( operation );
            }
            emailhistory.setTicketID( ticketID );
            emailHistoryRepo.save( emailhistory );
            emailhistory = null;
        }
        emailPrefSet = null;
    }

    private Set<EmailPref> getNotifiedEmailsForTicketAction(Long ticketID, int status, String assignUser) throws GeneralException {
        if (ticketID == null || !ticketsRepo.existsById( ticketID ))
            throw new GeneralException( "Ticket ID IS NULL !!!!" );

        Ticket ticket = ticketsRepo.findById( ticketID ).orElse( null );
        List<Subscriptions> notificationsListTicket = null;
        List<Subscriptions> notificationsListTopic = null;
        List<Subscriptions> allSub = new ArrayList<>();
        Set<String> usersList = new HashSet<>();
        Set<EmailPref> notifiedEmails = new HashSet<>();
        List<Users> listOFUsers = new ArrayList<>();
        switch (status) {
            case NotificationStatus
                    .OnClose:
                notificationsListTicket = subscriptionRepo.getByTicketIDAndSubStatusTrueAndOnCloseIsTrue( ticketID );
                notificationsListTopic = subscriptionRepo.getByTopicAndSubStatusIsTrueAndOnCloseIsTrue( ticket.getTopic().getId() );
                break;
            case NotificationStatus
                    .OnUpdate:
                notificationsListTicket = subscriptionRepo.getByTicketIDAndSubStatusTrueAndOnUpdateIsTrue( ticketID );
                notificationsListTopic = subscriptionRepo.getByTopicAndSubStatusIsTrueAndOnUpdateIsTrue( ticket.getTopic().getId() );

                break;
            case NotificationStatus
                    .OnCreate:
                notificationsListTicket = subscriptionRepo.getByTicketIDAndSubStatusTrueAndOnCreateIsTrue( ticketID );
                notificationsListTopic = subscriptionRepo.getByTopicAndSubStatusIsTrueAndOnCreateIsTrue( ticket.getTopic().getId() );
                break;
            case NotificationStatus.OnAssign:
                Users user = usersRepos.findByUserIDAndEnabledIsTrue( assignUser );
                if (user != null)
                    listOFUsers.add( user );
                else
                    return null;
                break;
        }

        if (listOFUsers.isEmpty()) {
            if (notificationsListTicket != null)
                allSub.addAll( notificationsListTicket );
            if (notificationsListTopic != null)
                allSub.addAll( notificationsListTopic );


            if (!allSub.isEmpty()) {
                for (Subscriptions subscription : allSub) {
                    if (subscription.getUserID() != null) {
                        usersList.add( subscription.getUserID() );
                    }
                }
            }
            listOFUsers = usersRepos.getUsersByUserName( usersList );
        }

        allSub = null;
        notificationsListTicket = null;
        notificationsListTopic = null;
        if (listOFUsers == null || listOFUsers.isEmpty())
            throw new GeneralException( "Users List is Empty or NULL !!!! For Status: " + status + " Ticket:" + ticketID );

        for (Users user : listOFUsers) {
            if (user.getPreferences() != null) {
                EmailPref pref = null;
                switch (status) {
                    case NotificationStatus
                            .OnClose:
                        if (user.getPreferences() != null && user.getPreferences().getEmailsNotifications() != null && user.getPreferences().getEmailsNotifications() && user.getPreferences().getTicketCLoseEmails() != null && user.getPreferences().getTicketCLoseEmails()) {
                            pref = new EmailPref( user.getUserID(), user.getEmail(), user.getPreferences().getIncludeAttatchments() );

                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug( "user {} {} disabled notifications for ticket Close notifications", user.getFirstName(), user.getLastName() );
                            }
                            pref = new EmailPref( user.getUserID(), user.getEmail(), true, false );
                        }
                        break;

                    case NotificationStatus.OnCreate:
                        if (user.getPreferences() != null && user.getPreferences().getEmailsNotifications() != null && user.getPreferences().getEmailsNotifications() && user.getPreferences().getTicketCreationEmails() != null && user.getPreferences().getTicketCreationEmails()) {
                            pref = new EmailPref( user.getUserID(), user.getEmail(), user.getPreferences().getIncludeAttatchments() );
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug( "user {} {} disabled notifications for ticket create notifications", user.getFirstName(), user.getLastName() );
                            }
                            pref = new EmailPref( user.getUserID(), user.getEmail(), true, false );
                        }
                        break;

                    case NotificationStatus.OnUpdate:
                        if (user.getPreferences() != null && user.getPreferences().getEmailsNotifications() != null && user.getPreferences().getEmailsNotifications() && user.getPreferences().getTicketEditEmails() != null && user.getPreferences().getTicketEditEmails()) {
                            pref = new EmailPref( user.getUserID(), user.getEmail(), user.getPreferences().getIncludeAttatchments() );
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug( "user {} {} disabled notifications for ticket create notifications", user.getFirstName(), user.getLastName() );
                            }
                            pref = new EmailPref( user.getUserID(), user.getEmail(), true, false );
                        }
                        break;
                    case NotificationStatus.OnAssign:
                        pref = getPrefFromUser( user );
                        break;

                }
                if (pref != null) {
                    notifiedEmails.add( pref );
                }
            } else {
                logger.info( "User preferences is not available for user {} {} ticket notification for ticket ID {} will be skipped for status {}", user.getFirstName(), user.getLastName(), ticketID, status );
            }
        }
        listOFUsers = null;
        usersList = null;
        return notifiedEmails;
    }

    private EmailPref getNotifiedEmailsForAssign(String userID) {

        Users user = usersRepos.findByUserIDAndEnabledIsTrue( userID );

        return getPrefFromUser( user );

    }


    private List<EmailPref> getNotifiedEmailsForSLA(Topicsla topicSLA, Ticket ticketID) {
        List<EmailPref> emailPrefList = null;

        List<Slausers> slausersList = slaUsersRepo.
                findByTopicSLAAndEnabledIsTrue( topicSLA.getId() );
        if (slausersList == null || slausersList.isEmpty()) {
            logger.error( "Sla users list for TOPIC SLA {} " +
                    "is empty please add sla users for this SLA ", topicSLA );

        } else {
            emailPrefList = new ArrayList<>();
            for (Slausers slausers : slausersList) {
                EmailPref emailPref = null;

                if (slausers.getUserId() != null && !slausers.getUserId().isEmpty()) {
                    // user is defined ignore emails
                    Users user = usersRepos.
                            findByUserIDAndEnabledIsTrue( slausers.getUserId() );
                    emailPref = getPrefFromUser( user );
                    emailPref.setSlaEmail( true );

                } else if (slausers.getEmails() != null &&
                        !slausers.getEmails().isEmpty()) {
                    emailPref = new EmailPref();
                    emailPref.setEmailID( slausers.getEmails() );
                } else {
                    logger.error( "sla user record defined for TOPIC SLA {}" +
                                    " , SLA User ID {} doesn't have email ID or user" +
                                    " ID, this must be fixed" +
                                    " to avoid missing emails notification ",
                            topicSLA, slausers.getId() );
                }
                if (emailPref != null)
                    emailPrefList.add( emailPref );
            }

        }
        return emailPrefList;
    }


    /**
     * @param tempID
     * @param ticketID
     * @param topicsla
     * @param user
     * @param operation
     * @return
     * @throws GeneralException
     */
    private Emailtemplates formatTemplate(Integer tempID, Long ticketID, Topicsla topicsla, String user, String operation) throws GeneralException {

        if (tempID == null || !emailTemplatesRepo.existsById( tempID ))
            throw new GeneralException( "Email template is NULL !!!!" );

        Emailtemplates emailtemplates = emailTemplatesRepo.findById( tempID ).orElse( null );
        Map<String, String> substitutes = new HashMap<>();
        Map<String, String> titleSubs = new HashMap<>();
        Ticket ticket = ticketsRepo.findById( ticketID ).orElse( null );
        if (operation != null && !operation.isEmpty()) {
            String mappedOperation = "";
            switch (operation) {
                case TicketOperation.CLOSE:
                    mappedOperation = "Ticket Closed";
                    break;
                case TicketOperation.CHGDPT:
                    mappedOperation = "Ticket Department Changed";
                    break;
                case TicketOperation.ASSIGN:
                    mappedOperation = "Ticket Assigned";
                    break;
                case TicketOperation.CREATE:
                    mappedOperation = "New Ticket Created";
                    break;
                case TicketOperation.MODIFYINFO:
                    mappedOperation = "Ticket Information Changed";
                    break;
                case TicketOperation.REOPEN:
                    mappedOperation = "Ticket Was ReOpened";
                    break;
                case TicketOperation.REPLY:
                    mappedOperation = "New Reply Added";
                    break;
                case TicketOperation.RESOLVE:
                    mappedOperation = "Ticket Resolved";
                    break;
                default:
                    mappedOperation = "Action";
                    break;
            }
            substitutes.put( "ActionTaken", mappedOperation );
            titleSubs.put( "ActionTaken", mappedOperation );

        } else {
            substitutes.put( "ActionTaken", "" );
            titleSubs.put( "ActionTaken", "" );
        }
        if (ticket != null) {
            substitutes.put( "Ticket_ID", ticket.getId().toString() );
            titleSubs.put( "Ticket_ID", ticket.getId().toString() );
            substitutes.put( "Topic_En", ticket.getTopic().getEnglishLabel() );
            substitutes.put( "MainCat_En", ticket.getTopic().getSubCategory().getMainCategory().getEnglishLabel() );
            substitutes.put( "SubCat_En", ticket.getTopic().getSubCategory().getEnglishLabel() );
            substitutes.put( "OriginalTopic_En", ticket.getOriginalTopic().getEnglishLabel() );
            substitutes.put( "OriginalMainCat_En", ticket.getOriginalTopic().getSubCategory().getMainCategory().getEnglishLabel() );
            substitutes.put( "OriginalSubCat_En", ticket.getOriginalTopic().getSubCategory().getEnglishLabel() );
            substitutes.put( "Topic_Ar", ticket.getTopic().getArabicLabel() );
            substitutes.put( "MainCat_Ar", ticket.getTopic().getSubCategory().getMainCategory().getArabicLabel() );
            substitutes.put( "SubCat_Ar", ticket.getTopic().getSubCategory().getArabicLabel() );
            substitutes.put( "OriginalTopic_Ar", ticket.getOriginalTopic().getArabicLabel() );
            substitutes.put( "OriginalMainCat_Ar", ticket.getOriginalTopic().getSubCategory().getMainCategory().getArabicLabel() );
            substitutes.put( "OriginalSubCat_Ar", ticket.getOriginalTopic().getSubCategory().getArabicLabel() );
            substitutes.put( "CurrentStatus_En", ticketStatusRepo.findById( ticket.getCurrentStatus() ).orElse( new Status() ).getEnglishLabel() );
            substitutes.put( "CurrentStatus_Ar", ticketStatusRepo.findById( ticket.getCurrentStatus() ).orElse( new Status() ).getArabicLabel() );
            substitutes.put( "CreatedBy", ticket.getCreatedBy() );
            substitutes.put( "ModifiedBy", ticket.getModifiedBy() == null ? "" : ticket.getModifiedBy() );
            substitutes.put( "CreationDate", df.format( ticket.getCreationDate() ) );
            substitutes.put( "ModificationDate", ticket.getModificationDate() == null ? "" : df.format( ticket.getModificationDate() ) );
            substitutes.put( "CrossedAllSLA", getTrueFalseLabel( ticket.isCrossedAllSLA() ) );
            substitutes.put( "SourceChannel_Ar", sourceChannelRepo.findById( ticket.getSourceChannel() ).orElse( new SourceChannel( "", "" ) ).getArabicLabel() );
            substitutes.put( "SourceChannel_En", sourceChannelRepo.findById( ticket.getSourceChannel() ).orElse( new SourceChannel( "", "" ) ).getEnglishLabel() );
            substitutes.put( "Subject", ticket.getSubject() );
            substitutes.put( "TicketType_Ar", ticketTypeRepo.findById( ticket.getTicketType() ).orElse( new Tickettypes( null, "", "" ) ).getArabicLabel() );
            substitutes.put( "TicketType_En", ticketTypeRepo.findById( ticket.getTicketType() ).orElse( new Tickettypes( null, "", "" ) ).getEnglishLabel() );
            substitutes.put( "Details", ticket.getDetails() != null ? ticket.getDetails() : "" );
            substitutes.put( "AssignedTo", ticket.getAssignedTo() != null ? ticket.getAssignedTo() : "" );
            substitutes.put( "Priority", ticket.getPriority().toString() != null ? ticket.getPriority().toString() : "" );
            if (ticket.getCustomerAccount() != null) {
                substitutes.put( "CustomerAccount_CustomerNameAR", ticket.getCustomerAccount().getCustomerNameAR() != null ? ticket.getCustomerAccount().getCustomerNameAR() : "" );
                substitutes.put( "CustomerAccount_customerNameEn", ticket.getCustomerAccount().getCustomerNameEn() != null ? ticket.getCustomerAccount().getCustomerNameEn() : "" );
                substitutes.put( "CustomerAccount_Email", ticket.getCustomerAccount().getEmail() != null ? ticket.getCustomerAccount().getEmail() : "" );
                substitutes.put( "CustomerAccount_Mobile", ticket.getCustomerAccount().getMobile() != null ? ticket.getCustomerAccount().getMobile() : "" );
                substitutes.put( "CustomerAccount_Nin", ticket.getCustomerAccount().getNin() != null ? ticket.getCustomerAccount().getNin() : "" );
                substitutes.put( "CustomerAccount_Segment", ticket.getCustomerAccount().getSegment() != null ? ticket.getCustomerAccount().getSegment() : "" );
                substitutes.put( "CustomerAccount_CustomerCIF", ticket.getCustomerAccount().getCustomerCIF() != null ? ticket.getCustomerAccount().getCustomerCIF() : "" );
                substitutes.put( "CustomerAccount_BranchName", ticket.getCustomerAccount().getBranchName() != null ? ticket.getCustomerAccount().getBranchName() : "" );
            } else {
                substitutes.put( "CustomerAccount_CustomerNameAR", "" );
                substitutes.put( "CustomerAccount_customerNameEn", "" );
                substitutes.put( "CustomerAccount_Email", "" );
                substitutes.put( "CustomerAccount_Mobile", "" );
                substitutes.put( "CustomerAccount_Nin", "" );
                substitutes.put( "CustomerAccount_Segment", "" );
                substitutes.put( "CustomerAccount_CustomerCIF", "" );
                substitutes.put( "CustomerAccount_BranchName", "" );
            }
            if (ticket.getLastTicketData() != null) {
                Ticketdata ticketdata = ticketDataRepo.findById( ticket.getLastTicketData() ).orElse( null );
                if (ticketdata != null) {
                    substitutes.put( "TicketData_Title", ticketdata.getTitle() != null ? ticketdata.getTitle() : "" );
                    substitutes.put( "TicketData_TicketData", ticketdata.getTicketData() != null ? ticketdata.getTicketData() : "" );
                    substitutes.put( "TicketData_CreatedBy", ticketdata.getCreatedBy() != null ? ticketdata.getCreatedBy() : "" );
                    substitutes.put( "TicketData_CreationDate", ticketdata.getCreationDate() != null ? df.format( ticketdata.getCreationDate() ) : "" );
                }
            } else {
                substitutes.put( "TicketData_Title", "" );
                substitutes.put( "TicketData_TicketData", "" );
                substitutes.put( "TicketData_CreatedBy", "" );
                substitutes.put( "TicketData_CreationDate", "" );
            }
       /* substitutes.put("Solved",null);
        substitutes.put("Closed",null);
        substitutes.put("Deleted",null);*/
        }


        StringSubstitutor titleSubtit = new StringSubstitutor( titleSubs );
        StringSubstitutor sub = new StringSubstitutor( substitutes );


        emailtemplates.setTemplateData( sub.replace( emailtemplates.getTemplateData() ) );
        emailtemplates.setTemplateTitle( titleSubtit.replace( emailtemplates.getTemplateTitle() ) );
        if (logger.isDebugEnabled()) {
            logger.debug( "Formatted Email template name {} ,\n title  {} is\n {}",
                    emailtemplates.getTemplateName(), emailtemplates.getTemplateTitle(), emailtemplates.getTemplateData() );
        }
        return emailtemplates;
    }

    private String getTrueFalseLabel(Boolean value) {
        if (value == null)
            return "No";
        if (value)
            return "Yes";
        return "No";
    }

    private EmailPref getPrefFromUser(Users user) {
        EmailPref emailPref = null;
        if (user != null) {
            emailPref = new EmailPref( user.getUserID(), user.getEmail(), user.getPreferences() != null ? user.getPreferences().getEmailsNotifications() : false );
        }

        if (emailPref != null) {
            if (user.getPreferences() != null && user.getPreferences().getEmailsNotifications() != null && user.getPreferences().getEmailsNotifications()) {
                emailPref.setIncludeAttatchments( user.getPreferences().getIncludeAttatchments() );
                emailPref.setDisabled( false );
            } else {
                emailPref.setDisabled( true );
            }
        }
        return emailPref;
    }
}
