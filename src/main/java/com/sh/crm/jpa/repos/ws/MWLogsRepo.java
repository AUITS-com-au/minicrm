package com.sh.crm.jpa.repos.ws;

import com.sh.crm.jpa.entities.MWLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MWLogsRepo extends JpaRepository<MWLogs, Long> {

    @Modifying
    @Transactional
    @Query("update MWLogs mwlogs set mwlogs.resServiceName=?1 ,mwlogs.fullResponse=?2 where mwlogs.id=?3")
    void updateMWLogs(String responseServiceName, String fullResponse, long id);
}
