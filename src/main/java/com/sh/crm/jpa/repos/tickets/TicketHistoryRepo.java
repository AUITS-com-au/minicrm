package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketHistoryRepo extends JpaRepository<TicketHistory, Long> {

    List<TicketHistory> findByTicketID(Long ticket);
}
