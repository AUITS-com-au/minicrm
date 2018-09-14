package com.sh.crm.jpa.repos;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.entities.Rolepermissions;
import com.sh.crm.jpa.entities.Roles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolesPermissionsRepo extends CrudRepository<Rolepermissions, Integer> {
    @Query("select role.permissionID from Rolepermissions role where role.roleID=?1")
    List<Permissions> getRolePermissions(Roles role);

    void deleteAllByRoleID(Roles roles);
}
