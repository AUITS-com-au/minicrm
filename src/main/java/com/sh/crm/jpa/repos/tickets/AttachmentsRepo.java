package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsRepo extends JpaRepository<Attachments, Long> {
}
