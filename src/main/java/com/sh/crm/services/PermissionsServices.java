package com.sh.crm.services;

import com.sh.crm.jpa.repos.users.PermissionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionsServices {
    @Autowired
    private PermissionsRepo permissionsRepo;


}
