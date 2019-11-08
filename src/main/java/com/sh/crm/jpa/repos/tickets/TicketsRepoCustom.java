package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.general.holders.SearchTicketsContainer;
import com.sh.crm.general.holders.SearchTicketsResult;

public interface TicketsRepoCustom {
    SearchTicketsResult searchTickets(SearchTicketsContainer searchTicketsContainer);
}
