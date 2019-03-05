package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Slausers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SlaUsersRepo extends JpaRepository<Slausers, Long> {
    List<Slausers> findByTopicSLAAndEnabledIsTrue(Integer topicSLA);

    @Query("select distinct slau.userId from Slausers slau where slau.topicSLA=?1 ")
    List<String> getSlaUsersId(Integer topicSla);

    @Transactional
    void deleteAllByTopicSLA(Integer topicSla);
}
