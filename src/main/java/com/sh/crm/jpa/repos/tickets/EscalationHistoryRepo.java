package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Escalationhistory;
import com.sh.crm.jpa.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscalationHistoryRepo extends JpaRepository<Escalationhistory, Long> {
    List<Escalationhistory> findByTicketID(Ticket ticket);
}
