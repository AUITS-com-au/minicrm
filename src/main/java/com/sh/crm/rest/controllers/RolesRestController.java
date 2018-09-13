package com.sh.crm.rest.controllers;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.RoleHolder;
import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.entities.Rolepermissions;
import com.sh.crm.jpa.entities.Roles;
import com.sh.crm.jpa.repos.RolesPermissionsRepo;
import com.sh.crm.jpa.repos.RolesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasAnyAuthority('Administrator','RolesAdmin')")
public class RolesRestController {
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private RolesPermissionsRepo rolesPermissionsRepo;

    private static final Logger log = LoggerFactory.getLogger(RolesRestController.class);

    @GetMapping("all")
    List<Roles> getAll() {
        return rolesRepo.findAll();
    }

    @GetMapping("permissions/{roleID}")
    List<Permissions> getPermissions(@PathVariable("roleID") Integer roleID) {
        return rolesPermissionsRepo.getRolePermissions(new Roles(roleID));
    }

    @PostMapping("create")
    ResponseEntity<?> create(@RequestBody RoleHolder roleHolder) {
        if (roleHolder != null && roleHolder.getRole() != null && roleHolder.getPermissions() != null && !roleHolder.getPermissions().isEmpty()) {
            Roles role = roleHolder.getRole();
            role.setId(null);
            try {
                rolesRepo.save(role);
                for (Permissions permissions : roleHolder.getPermissions()) {
                    Rolepermissions rp = new Rolepermissions();
                    rp.setPermissionID(permissions);
                    rp.setRoleID(role);
                    rolesPermissionsRepo.save(rp);
                    rp = null;
                }
                return new ResponseEntity<ResponseCode>(new ResponseCode(Errors.SUCCESSFUL), HttpStatus.OK);

            } catch (Exception e) {
                e.printStackTrace();
                throw new GeneralException(Errors.GROUP_CREATE_FAILED, e.toString());
            }
        }

    }

}
