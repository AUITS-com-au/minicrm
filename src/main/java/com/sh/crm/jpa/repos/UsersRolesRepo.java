package com.sh.crm.jpa.repos;

import com.sh.crm.jpa.entities.Roles;
import com.sh.crm.jpa.entities.Userroles;
import com.sh.crm.jpa.entities.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRolesRepo extends CrudRepository<Userroles, Long> {
    void deleteByUserID(Users userID);
    List<Userroles> findByUserID(Integer userID);
    @Query( value = "SELECT  ur.roleID FROM  Userroles ur   where ur.userID=?1")
    List<Roles> getUserRoles(Integer id);
}
