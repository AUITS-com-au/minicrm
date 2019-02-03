package com.sh.crm.services.basic;

import com.sh.crm.jpa.repos.global.GlobalConfigRepo;
import com.sh.crm.jpa.repos.notifications.EmailHistoryRepo;
import com.sh.crm.jpa.repos.notifications.EmailMessageRepo;
import com.sh.crm.jpa.repos.notifications.EmailTemplatesRepo;
import com.sh.crm.jpa.repos.notifications.SubscriptionRepo;
import com.sh.crm.jpa.repos.tickets.*;
import com.sh.crm.jpa.repos.users.UsersRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class BasicService {
    protected static Logger logger = LoggerFactory.getLogger( BasicService.class );
    protected static DateFormat df = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss a" );
    @Autowired
    protected TicketsRepo ticketsRepo;
    @Autowired
    protected TicketDataRepo ticketDataRepo;
    @Autowired
    protected TicketTypeRepo ticketTypeRepo;
    @Autowired
    protected SourceChannelRepo sourceChannelRepo;
    @Autowired
    protected TopicRepo topicRepo;
    @Autowired
    protected TicketStatusRepo ticketStatusRepo;
    @Autowired
    protected TicketHistoryRepo historyRepo;
    @Autowired
    protected GlobalConfigRepo globalConfigRepo;
    @Autowired
    protected EmailTemplatesRepo emailTemplatesRepo;
    @Autowired
    protected EmailHistoryRepo emailHistoryRepo;
    @Autowired
    protected EmailMessageRepo emailMessageRepo;
    @Autowired
    protected SubscriptionRepo subscriptionRepo;
    @Autowired
    protected UsersRepos usersRepos;
    @Autowired
    protected TicketAttachmentsRepo ticketAttachmentsRepo;
    @Autowired
    protected SlaUsersRepo slaUsersRepo;
    @Autowired
    protected SlaRepo slaRepo;
    @Autowired
    protected TicketActionsRepo ticketActionsRepo;

    @Autowired
    protected EscalationHistoryRepo escalationHistoryRepo;
}
