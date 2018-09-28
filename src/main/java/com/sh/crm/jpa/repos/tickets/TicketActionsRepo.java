package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Ticketactions;
import com.sh.crm.jpa.entities.Ticketdata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketActionsRepo extends JpaRepository<Ticketactions, Integer> {
}
