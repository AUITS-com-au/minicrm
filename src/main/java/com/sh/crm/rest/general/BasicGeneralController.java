package com.sh.crm.rest.general;

import com.sh.crm.general.utils.Utils;
import com.sh.crm.jpa.entities.Users;
import com.sh.crm.jpa.repos.tickets.*;
import com.sh.crm.jpa.repos.users.UsersRepos;
import com.sh.crm.security.util.SecurityUtils;
import com.sh.crm.services.notification.NotificationData;
import com.sh.crm.services.storage.FileDBStorageService;
import com.sh.crm.services.tickets.TicketServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.Event;
import reactor.bus.EventBus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class BasicGeneralController {
    private static final DateFormat df = new SimpleDateFormat( "yyMMdd" );
    // private final static int TOPIC_LEN = 4;
    private final static int SEQ_LEN = 5;
    public final Logger log = LoggerFactory.getLogger( getClass() );
    @Autowired
    protected MainCategoryRepo mainCategoryRepo;
    @Autowired
    protected TopicsPermissionsRepo topicsPermissionsRepo;
    @Autowired
    protected TopicRepo topicRepo;
    @Autowired
    protected SubcategoryRepo subcategoryRepo;
    @Autowired
    protected UsersRepos usersRepos;
    @Autowired
    protected CustomerAccountsRepo customerAccountsRepo;
    @Autowired
    protected TicketsRepo ticketsRepo;
    @Autowired
    protected TicketExtDataRepo ticketExtDataRepo;
    @Autowired
    protected TicketDataRepo tikcetDataRepo;
    @Autowired
    protected TicketActionsRepo ticketActionsRepo;
    @Autowired
    protected TicketLocksRepo ticketLocksRepo;
    @PersistenceContext
    protected EntityManager em;
    @Autowired
    protected TopicSlaRepo topicSlaRepo;
    @Autowired
    protected TicketServices ticketServices;
    @Autowired
    protected EventBus eventBus;
    @Autowired
    protected FileDBStorageService fileDBStorageService;
    @Autowired
    protected TicketAttachmentsRepo ticketAttachmentRepo;
    @Autowired
    protected AttachmentsRepo attachmentsRepo;

    protected Users getAuthorizedUser() {
        return usersRepos.findByUserID( SecurityUtils.getPrincipal() );
    }

    protected Users getAuthorizedUser(Principal principal) {
        return usersRepos.findByUserID( principal.getName() );
    }

    private BigInteger generateNextID() {
        return (BigInteger) em.createNativeQuery( "select (NEXT VALUE FOR dbo.ticketNumber) AS TicketID" ).getSingleResult();
    }

    protected String getNextTicketID(Integer topicID) {
        String dateValue = getFormattedDateOfToday();
        BigInteger currentSequence = generateNextID();
        String currentSeqPadded = Utils.padSeq( currentSequence.longValue(), SEQ_LEN );
        if (log.isDebugEnabled()) {
            log.debug( "generate ticket ID with topic {} , current date {}, and generated value {}", topicID, dateValue, currentSeqPadded );
        }
        return dateValue + currentSeqPadded;
    }

    protected void notifyAction(long actionID) {
        NotificationData notificationData = new NotificationData( actionID );
        eventBus.notify( "NC", Event.wrap( notificationData ) );
    }

    private String getFormattedDateOfToday() {
        return df.format( Calendar.getInstance().getTime() );
    }
}
