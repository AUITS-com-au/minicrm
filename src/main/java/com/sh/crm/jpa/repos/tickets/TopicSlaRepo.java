package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicsla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicSlaRepo extends JpaRepository<Topicsla, Integer> {
    Integer countDistinctByTopicID(Topic topic);

    @Query("select max(tsla.slaLevel) from Topicsla tsla where tsla.topicID=?1")
    Integer getMaxSlaLevel(Topic topic);
}
