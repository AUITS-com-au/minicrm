package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Ticket;
import com.sh.crm.jpa.entities.Ticketdata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketDataRepo extends JpaRepository<Ticketdata, Long> {
    List<Ticketdata> findByTicketIDOrderByCreationDateDesc(Ticket ticket);
    List<Ticketdata> findByActionID_ActionIDAndTicketID_IdOrderByCreationDateDesc(Integer actionID, Long ticketID);

}
