package com.sh.crm.rest.general;

import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.security.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Set;

public abstract class BasicController<T> extends BasicGeneralController {

    protected Set<Topicspermissions> getCurrentUserTopicsPermissionsByTopicID(Integer topicID) {
        String principal = SecurityUtils.getPrincipal();
        return getUserTopicsPermissionsByTopicIDAndUserID( topicID, principal );
    }

    protected Set<Topicspermissions> getCurrentUserTopicsPermissions() {
        String principal = SecurityUtils.getPrincipal();
        return topicsPermissionsRepo.
                getUserTopicsPermissions(
                        principal );
    }

    protected Set<Topicspermissions> getUserTopicsPermissionsByTopicIDAndUserID(Integer topicID, String userID) {

        return topicsPermissionsRepo.
                getUserTopicsPermissions(
                        userID,
                        topicID );
    }


    @GetMapping("all")
    protected Iterable<?> all() {
        return null;
    }

    @GetMapping("authorized")
    protected Iterable<?> authorizedList(Principal principal) {
        return null;
    }


    @GetMapping("active")
    protected Iterable<?> active() {
        return null;
    }

    @PostMapping("create")
    public abstract ResponseEntity<?> create(@RequestBody T object, Principal principal) throws GeneralException;

    @PostMapping("edit")
    @Transactional
    public abstract ResponseEntity<?> edit(@RequestBody T object, Principal principal) throws GeneralException;

    @PostMapping("delete")
    @Transactional
    public abstract ResponseEntity<?> delete(@RequestBody T object, Principal principal) throws GeneralException;
}
