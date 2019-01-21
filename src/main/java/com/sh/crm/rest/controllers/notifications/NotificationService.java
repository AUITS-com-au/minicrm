package com.sh.crm.rest.controllers.notifications;

import com.sh.crm.jpa.repos.tickets.TicketActionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private TicketActionsRepo ticketActionsRepo;


}
