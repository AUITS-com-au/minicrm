package com.sh.crm.jpa.repos.custom;

import com.sh.crm.jpa.entities.Permissions;

import java.util.List;

public interface GetUsersPermissionsCustom {
    List<Permissions> usersPermission(Integer ID);
}
