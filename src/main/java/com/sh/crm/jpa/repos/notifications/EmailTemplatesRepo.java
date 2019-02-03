package com.sh.crm.jpa.repos.notifications;

import com.sh.crm.jpa.entities.Emailtemplates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplatesRepo extends JpaRepository<Emailtemplates, Integer> {
}
