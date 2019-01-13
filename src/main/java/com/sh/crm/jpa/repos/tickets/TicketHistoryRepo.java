package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketHistoryRepo extends JpaRepository<TicketHistory, Long> {
}
