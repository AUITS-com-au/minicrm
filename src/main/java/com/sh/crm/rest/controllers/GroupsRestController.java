package com.sh.crm.rest.controllers;


import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.GroupHolder;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.jpa.repos.GroupRolesRepo;
import com.sh.crm.jpa.repos.GroupsRepo;
import com.sh.crm.jpa.repos.UserGroupsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@PreAuthorize("hasAnyAuthority('Administrator','GroupsAdmin')")
public class GroupsRestController {
    private static final Logger log = LoggerFactory.getLogger(GroupsRestController.class);
    @Autowired
    private GroupsRepo groupRepo;
    @Autowired
    private GroupRolesRepo groupRolesRepo;
    @Autowired
    private UserGroupsRepo userGroupsRepo;

    @GetMapping("all")
    Iterable<Groups> findAll() {
        return groupRepo.findAll();
    }

    @GetMapping("/active/{status}")
    Iterable<Groups> findByStatus(@PathVariable("status") boolean status) {
        return groupRepo.findByEnabled(status);
    }


    @PostMapping("create")
    @Transactional
    public ResponseEntity<?> createGroup(@RequestBody @Valid GroupHolder groupHolder) throws GeneralException {
        log.debug("create group {} requested", groupHolder.toString());
        Groups group = new Groups();
        group.setEnabled(true);
        group.setGroupName(groupHolder.getName());
        try {
            groupRepo.save(group);
            log.info("Group {} has been created", group);
        } catch (Exception e) {
            log.error("Error creating group: " + groupHolder.getName() + ", error: " + e);
            e.printStackTrace();
            throw new GeneralException(Errors.GROUP_CREATE_FAILED, e.toString());
        }
        try {
            if (group.getId() != null) {
                if (groupHolder.getRoles() != null && !groupHolder.getRoles().isEmpty())
                    createGroupRoles(group, groupHolder.getRoles());
                if (groupHolder.getUsers() != null && !groupHolder.getUsers().isEmpty())
                    createGroupUsers(group, groupHolder.getUsers());
            }
        } catch (Exception e) {
            log.error("Error creating user group: " + groupHolder.getName() + ", error: " + e);
            e.printStackTrace();
            throw new GeneralException(Errors.GROUP_CREATED_OTHER_FAILED, e.toString());

        }
        return new ResponseEntity<ResponseCode>(new ResponseCode(Errors.SUCCESSFUL), HttpStatus.OK);
    }

    @PostMapping("edit")
    @Transactional
    public ResponseEntity<?> editGroup(@RequestBody @Valid GroupHolder groupHolder) throws GeneralException {
        log.debug("Received modify Groups request: " + groupHolder.toString());
        Groups group = groupRepo.findOne(groupHolder.getGroup().getId());
        group.setGroupName(groupHolder.getName());
        try {
            groupRepo.save(group);
        } catch (Exception e) {
            log.error("Error modifying group: {} , Exception {}", groupHolder.getName(), e);
            e.printStackTrace();
            throw new GeneralException(Errors.GROUP_EDIT_FAILED, e.toString());
        }
        log.debug("Groups modified successfully, name: " + group.getGroupName());
        try {

            if (groupHolder.getRoles() != null && !groupHolder.getRoles().isEmpty()) {
                deleteGroupRoles(group);
                createGroupRoles(group, groupHolder.getRoles());
            }
            if (groupHolder.getUsers() != null && !groupHolder.getUsers().isEmpty()) {
                deleteGroupUsers(group);
                createGroupUsers(group, groupHolder.getUsers());
            }


        } catch (Exception e) {
            log.error("Error modifying  group: " + groupHolder.getName() + ", error: " + e);
            e.printStackTrace();
            throw new GeneralException(Errors.GROUP_EDIT_OTHER_FAILED, e.toString());
        }
        return new ResponseEntity<ResponseCode>(new ResponseCode(Errors.SUCCESSFUL), HttpStatus.OK);
    }

    @GetMapping("users/{groupID}")
    public List<Users> getUsers(@PathVariable("groupID") Integer groupID) {
        return userGroupsRepo.getGroupUsers(groupID);
    }

    @GetMapping("{groupID}")
    public ResponseEntity<Groups> getById(@PathVariable("groupID") Integer id) {
        Groups group = groupRepo.findOne(id);
        if (group != null) {
            return ResponseEntity.ok(group);
        }
        return new ResponseEntity(new ResponseCode(Errors.GROUP_NOT_EXISTS), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("validate/{groupname}")
    public ResponseEntity<?> validateGroup(@PathVariable("groupname") String groupname) {

        if (groupRepo.findByGroupName(groupname) != null) {
            return ResponseEntity.ok(new ResponseCode(Errors.SUCCESSFUL));
        } else
            return new ResponseEntity(new ResponseCode(Errors.GROUP_NOT_EXISTS), HttpStatus.OK);

    }


    public void createGroupRoles(Groups group, List<Roles> roles) {
        for (Roles role : roles) {
            Grouproles gr = new Grouproles();
            gr.setGroupID(group);
            gr.setRoleID(role);
            groupRolesRepo.save(gr);
            gr = null;
        }

    }

    public void deleteGroupRoles(Groups groupID) {
        groupRolesRepo.deleteByGroupID(groupID);
    }


    public void createGroupUsers(Groups group, List<Users> users) {
        for (Users user : users) {
            Usergroups ug = new Usergroups();
            ug.setGroupID(group);
            ug.setUserID(user);
            userGroupsRepo.save(ug);
            ug = null;
        }
    }

    public void deleteGroupUsers(Groups groupID) {
        userGroupsRepo.deleteByGroupID(groupID);
    }

}
