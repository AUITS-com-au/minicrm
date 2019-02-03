package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Escalationhistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalationHistoryRepo extends JpaRepository<Escalationhistory, Long> {
}
