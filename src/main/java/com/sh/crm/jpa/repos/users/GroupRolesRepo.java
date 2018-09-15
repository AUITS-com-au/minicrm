package com.sh.crm.jpa.repos.users;

import com.sh.crm.jpa.entities.Grouproles;
import com.sh.crm.jpa.entities.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRolesRepo extends JpaRepository<Grouproles, Integer> {

    void deleteByGroupID(Groups group);

}
