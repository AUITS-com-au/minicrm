package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Sla;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlaRepo extends JpaRepository<Sla, Integer> {
}
