package com.sh.crm.jpa.repos;

import com.sh.crm.jpa.entities.Groups;
import com.sh.crm.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupsRepo extends JpaRepository<Groups, Integer> {
    List<Groups> findByEnabled(boolean enabled);

    Groups findByGroupName(String groupName);

}
