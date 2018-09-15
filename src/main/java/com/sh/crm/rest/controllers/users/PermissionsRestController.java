package com.sh.crm.rest.controllers.users;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.repos.users.PermissionsRepo;
import com.sh.crm.security.annotation.RolesAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
@RolesAdmin
public class PermissionsRestController {
    @Autowired
    private PermissionsRepo permissionsRepo;

    @GetMapping("all")
    Iterable<Permissions> getAll() {
        return permissionsRepo.findAll();
    }
}
