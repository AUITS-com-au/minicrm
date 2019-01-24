package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Tickettypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepo extends JpaRepository<Tickettypes, Integer> {
}
