package com.sh.crm.rest.controllers.users;


import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.GroupHolder;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.jpa.repos.users.GroupRolesRepo;
import com.sh.crm.jpa.repos.users.GroupsRepo;
import com.sh.crm.jpa.repos.users.UserGroupsRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.GroupsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(value = "groups", produces = MediaType.APPLICATION_JSON_VALUE)
@GroupsAdmin
public class GroupsRestController extends BasicController<GroupHolder> {

    @Autowired
    private GroupsRepo groupRepo;
    @Autowired
    private GroupRolesRepo groupRolesRepo;
    @Autowired
    private UserGroupsRepo userGroupsRepo;

    @GetMapping("all")
    public Iterable<Groups> all() {
        return groupRepo.findAll();
    }

    @GetMapping("/active/{status}")
    Iterable<Groups> findByStatus(@PathVariable("status") boolean status) {
        return groupRepo.findByEnabled( status );
    }


    public ResponseEntity<?> create(@RequestBody @Valid GroupHolder groupHolder, Principal principal) throws GeneralException {
        log.debug( "create group {} requested", groupHolder.toString() );
        Groups group = new Groups();
        group.setEnabled( true );
        group.setGroupName( groupHolder.getName() );
        try {
            group = groupRepo.save( group );
            log.info( "Group {} has been created", group );
        } catch (Exception e) {
            log.error( "Error creating group: " + groupHolder.getName() + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.GROUP_CREATE_FAILED, e.toString() );
        }
        try {
            if (group.getId() != null) {
                if (groupHolder.getRoles() != null && !groupHolder.getRoles().isEmpty())
                    createGroupRoles( group, groupHolder.getRoles() );
                if (groupHolder.getUsers() != null && !groupHolder.getUsers().isEmpty())
                    createGroupUsers( group, groupHolder.getUsers() );
            }
        } catch (Exception e) {
            log.error( "Error creating user group: " + groupHolder.getName() + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.GROUP_CREATED_OTHER_FAILED, e.toString() );

        }
        return ResponseEntity.ok( group );
    }


    public ResponseEntity<?> edit(@RequestBody @Valid GroupHolder groupHolder, Principal principal) throws GeneralException {
        log.debug( "Received modify Groups request: " + groupHolder.toString() );
        Groups group = groupRepo.findById( groupHolder.getGroup().getId() ).orElse( null );
        if (group == null)
            throw new GeneralException( Errors.GROUP_EDIT_FAILED, "Cannot find group" );

        group.setGroupName( groupHolder.getName() );
        try {
            group = groupRepo.save( group );
        } catch (Exception e) {
            log.error( "Error modifying group: {} , Exception {}", groupHolder.getName(), e );
            e.printStackTrace();
            throw new GeneralException( Errors.GROUP_EDIT_FAILED, e.toString() );
        }
        log.debug( "Groups modified successfully, name: " + group.getGroupName() );
        try {

            if (groupHolder.getRoles() != null && !groupHolder.getRoles().isEmpty()) {
                deleteGroupRoles( group );
                createGroupRoles( group, groupHolder.getRoles() );
            }
            if (groupHolder.getUsers() != null && !groupHolder.getUsers().isEmpty()) {
                deleteGroupUsers( group );
                createGroupUsers( group, groupHolder.getUsers() );
            }


        } catch (Exception e) {
            log.error( "Error modifying  group: " + groupHolder.getName() + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.GROUP_EDIT_OTHER_FAILED, e.toString() );
        }
        return ResponseEntity.ok( group );
    }

    @GetMapping("users/{groupID}")
    public List<Users> getUsers(@PathVariable("groupID") Integer groupID) {
        return userGroupsRepo.getGroupUsers( groupID );
    }

    @GetMapping("{groupID}")
    public ResponseEntity<Groups> getById(@PathVariable("groupID") Integer id) {
        Groups group = groupRepo.findById( id ).orElse( null );
        if (group != null) {
            return ResponseEntity.ok( group );
        }
        return new ResponseEntity( new ResponseCode( Errors.GROUP_NOT_EXISTS ), HttpStatus.BAD_REQUEST );
    }

    @GetMapping("validate/{groupname:.+}")
    public ResponseEntity<?> validateGroup(@PathVariable("groupname") String groupname) {

        if (groupRepo.findByGroupName( groupname ) != null) {
            return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
        } else
            return new ResponseEntity( new ResponseCode( Errors.GROUP_NOT_EXISTS ), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<?> delete(GroupHolder object, Principal principal) throws GeneralException {
        return null;
    }

    public void createGroupRoles(Groups group, List<Roles> roles) {
        for (Roles role : roles) {
            Grouproles gr = new Grouproles();
            gr.setGroupID( group );
            gr.setRoleID( role );
            groupRolesRepo.save( gr );
            gr = null;
        }
    }

    public void deleteGroupRoles(Groups groupID) {
        groupRolesRepo.deleteByGroupID( groupID );
    }


    public void createGroupUsers(Groups group, List<Users> users) {
        for (Users user : users) {
            Usergroups ug = new Usergroups();
            ug.setGroupID( group );
            ug.setUserID( user );
            userGroupsRepo.save( ug );
            ug = null;
        }
    }

    public void deleteGroupUsers(Groups groupID) {
        userGroupsRepo.deleteByGroupID( groupID );
    }

}
