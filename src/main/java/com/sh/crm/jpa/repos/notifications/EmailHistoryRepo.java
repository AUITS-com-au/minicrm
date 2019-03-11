package com.sh.crm.jpa.repos.notifications;

import com.sh.crm.jpa.entities.Emailhistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailHistoryRepo extends JpaRepository<Emailhistory, Long> {


    List<Emailhistory> findByStatusIsNullOrStatusNotInAndSendDateIsNullAndTriesLessThan(List<Integer> statusList, int tries);

}
