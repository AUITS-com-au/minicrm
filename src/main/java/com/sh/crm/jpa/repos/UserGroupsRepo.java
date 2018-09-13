package com.sh.crm.jpa.repos;

import com.sh.crm.jpa.entities.Groups;
import com.sh.crm.jpa.entities.Usergroups;
import com.sh.crm.jpa.entities.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserGroupsRepo extends CrudRepository<Usergroups, Long> {
    List<Usergroups> findByUserID(int userID);
    @Query(value = "select ug.groupID from Usergroups ug where ug.userID=?1")
    List<Groups> findGroupsOfUser(int userID);
    @Query("select g.userID from Usergroups g where g.groupID.id=?1")
    List<Users> getGroupUsers(Integer groups);
    void deleteByUserID(Users userID);
    void deleteByGroupID(Groups groups);
}