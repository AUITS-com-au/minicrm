package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.TicketExtData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketExtDataRepo extends JpaRepository<TicketExtData, Long> {

    List<TicketExtData> findByTicketID(Long ticketID);
}
