package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Ticket;
import com.sh.crm.jpa.entities.Ticketdata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TikcetDataRepo extends JpaRepository<Ticketdata, Long> {
    List<Ticketdata> findByTicketIDOrderByCreationDateDesc(Ticket ticket);
}
