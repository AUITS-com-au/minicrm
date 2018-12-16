package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.general.holders.SearchTicketsContainer;
import com.sh.crm.general.holders.SearchTicketsResult;
import com.sh.crm.jpa.entities.Ticket;

import java.util.List;

public interface TicketsRepoCustom {
    SearchTicketsResult searchTickets(SearchTicketsContainer searchTicketsContainer);
}
