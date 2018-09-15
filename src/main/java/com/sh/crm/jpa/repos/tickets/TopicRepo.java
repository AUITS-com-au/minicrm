package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepo extends JpaRepository<Topic, Integer> {
}
