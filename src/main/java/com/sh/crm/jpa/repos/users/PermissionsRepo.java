package com.sh.crm.jpa.repos.users;

import com.sh.crm.jpa.entities.Permissions;
import org.springframework.data.repository.CrudRepository;

public interface PermissionsRepo extends CrudRepository<Permissions, Integer>, GetUsersPermissionsCustom {

}
