package com.sh.crm.jpa.repos.notifications;

import com.sh.crm.jpa.entities.Emailhistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailHistoryRepo extends JpaRepository<Emailhistory, Long> {
}
