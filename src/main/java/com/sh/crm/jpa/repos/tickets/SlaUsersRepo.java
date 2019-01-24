package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Slausers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlaUsersRepo extends JpaRepository<Slausers, Long> {

    List<Slausers> findBySlaAndEnabledIsTrue(Integer sla);

}
