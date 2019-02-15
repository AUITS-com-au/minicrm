package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Ticketactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface TicketActionsRepo extends JpaRepository<Ticketactions, Integer> {

    Set<Ticketactions> findDistinctByEnabledTrueAndActionIDIn(Collection<Integer> ids);
}
