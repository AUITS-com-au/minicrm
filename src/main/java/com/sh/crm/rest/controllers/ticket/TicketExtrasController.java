package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.general.holders.TicketExtras;
import com.sh.crm.general.holders.TicketPriority;
import com.sh.crm.general.holders.TicketPriorityHolder;
import com.sh.crm.rest.general.BasicGeneralController;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class TicketExtrasController extends BasicGeneralController {


    private final static List<TicketPriorityHolder> priorities =
            Arrays.asList( new TicketPriorityHolder( TicketPriority.URGENT ), new TicketPriorityHolder( TicketPriority.VERY_HIGH ),
                    new TicketPriorityHolder( TicketPriority.HIGH ), new TicketPriorityHolder( TicketPriority.NORMAL ),
                    new TicketPriorityHolder( TicketPriority.LOW ) );

    public TicketExtras getTicketExtras() {
        TicketExtras ticketExtras = new TicketExtras();

        ticketExtras.setTicketactionsList( ticketActionsRepo.findAll() );
        ticketExtras.setTicketPriorityList( priorities );
        ticketExtras.setTicketStatusList( ticketStatusRepo.findAll() );
        ticketExtras.setTickettypesList( ticketTypeRepo.findAll() );
        ticketExtras.setChannelsList( sourceChannelRepo.findAll() );
        return ticketExtras;
    }

}
