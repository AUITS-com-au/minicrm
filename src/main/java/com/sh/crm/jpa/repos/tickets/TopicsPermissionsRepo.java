package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.jpa.repos.custom.GetUsersPermissionsCustom;
import com.sh.crm.jpa.repos.custom.GetUsersTopicsCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TopicsPermissionsRepo extends JpaRepository<Topicspermissions, Long>, GetUsersTopicsCustom {

    Set<Topicspermissions> findByTopicId(Topic topic);

    Set<Topicspermissions> findByAssigneAndType(Integer assigne, String type);
}
