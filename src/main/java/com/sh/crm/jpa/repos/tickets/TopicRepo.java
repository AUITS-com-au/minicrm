package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepo extends JpaRepository<Topic, Integer> {

    List<Topic> findBySubCategory(Subcategory sub);
}
