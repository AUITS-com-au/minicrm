package com.sh.crm.jpa.repos.ws;

import com.sh.crm.jpa.entities.ServiceAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceAuditLogRepo extends JpaRepository<ServiceAuditLog, Long> {
}
