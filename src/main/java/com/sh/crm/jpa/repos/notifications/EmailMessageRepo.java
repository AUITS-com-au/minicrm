package com.sh.crm.jpa.repos.notifications;

import com.sh.crm.jpa.entities.Emailmessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailMessageRepo extends JpaRepository<Emailmessage, Long> {
}
