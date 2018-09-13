package com.sh.crm.rest.controllers;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.repos.PermissionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@PreAuthorize("hasAnyAuthority('Administrator','RolesAdmin')")
public class PermissionsRestController {
    @Autowired
    private PermissionsRepo permissionsRepo;

    @GetMapping("all")
    Iterable<Permissions> getAll() {
        return permissionsRepo.findAll();
    }
}
