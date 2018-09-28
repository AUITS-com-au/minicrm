package com.sh.crm.jpa.repos.users;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.repos.custom.GetUsersPermissionsCustom;
import org.springframework.data.repository.CrudRepository;

public interface PermissionsRepo extends CrudRepository<Permissions, Integer>, GetUsersPermissionsCustom {

}
