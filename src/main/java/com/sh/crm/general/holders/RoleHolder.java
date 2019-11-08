package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.entities.Roles;

import java.util.List;

public class RoleHolder {

    private Roles role;
    private List<Permissions> permissions;

    public RoleHolder() {

    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }
}
