package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketStatusRepo extends JpaRepository<Status, Integer> {
}
