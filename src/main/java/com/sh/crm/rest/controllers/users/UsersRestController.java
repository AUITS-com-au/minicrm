package com.sh.crm.rest.controllers.users;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.UserHolder;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.jpa.repos.users.UserGroupsRepo;
import com.sh.crm.jpa.repos.users.UsersRepos;
import com.sh.crm.jpa.repos.users.UsersRolesRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.UsersAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@UsersAdmin
public class UsersRestController extends BasicController<UserHolder> {
    @Autowired
    private UsersRepos userRepo;
    @Autowired
    private UsersRolesRepo usersRolesRepo;
    @Autowired
    private UserGroupsRepo userGroupsRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("all")
    public List<Users> all() {
        log.debug( "Getting all users list" );
        return userRepo.findAll();
    }

    @Override
    public Iterable<?> active() {
        List<Users> usersList = userRepo.findByEnabledAndSystemUser( true, false );
        log.debug( "Getting active users list" );
        return usersList;

    }

    @GetMapping("validate/{username}")
    ResponseEntity<?> validateUser(@PathVariable("username") String userName) {
        log.debug( "checking existence of user: " + userName );
        if (userRepo.findByUserID( userName ) != null) {
            return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
        } else
            return new ResponseEntity( new ResponseCode( Errors.USER_NOT_EXISTS ), HttpStatus.OK );

    }

    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid UserHolder userHolder, Principal principal) throws GeneralException {
        log.debug( "received a request to create user: " + userHolder.getUser().toString() );
        if (userHolder.getUser().getUserID() != null) {
            if (userRepo.findByUserID( userHolder.getUser().getUserID() ) != null) {
                throw new GeneralException( Errors.USER_ALREADY_EXISTS, userHolder.getUser().getUserID() );
            }
            Users user = userHolder.getUser();
            if (!user.getLDAPUser())
                user.setPassword( encoder.encode( userHolder.getPassword() ) );
            user.setEnabled( true );
            user.setSystemUser( false );
            user.setLoginAttempts( 5 );
            try {
                user = userRepo.save( user );
                log.info( "User : " + user.getUserID() + " created successfully" );
            } catch (Exception e) {
                log.error( "Error Creating user: " + user + ", error: " + e );
                e.printStackTrace();
                throw new
                        GeneralException( Errors.CANNOT_CREATE_USER, e );
            }
            createRoles( user, userHolder.getSelectedRoles() );
            createGroups( user, userHolder.getSelectedGroups() );
            log.info( "User groups and Roles for User: " + user.getUserID() + " created sucessfully" );
            return ResponseEntity.ok( new ResponseCode( "0", Errors.SUCCESSFUL.getDesc() ) );
        }
        return ResponseEntity.ok( new ResponseCode( Errors.CANNOT_CREATE_USER ) );
    }


    @Transactional
    public ResponseEntity<?> edit(@RequestBody @Valid UserHolder userHolder, Principal principal) throws GeneralException {
        Users user = userHolder.getUser();
        log.debug( "Received request to edit user: " + userHolder );
        try {
            if (user != null && user.getId() != null && user.getUserID() != null) {
                Users origianlUser = userRepo.findById( user.getId() ).orElse( null );
                //validateSuperUser(user);
                if (origianlUser == null) {
                    throw new GeneralException( Errors.USER_EDIT_FAILED.getCode(),
                            Errors.USER_EDIT_FAILED.getDesc() + ", exception: Cannot find user" );
                }
                if (!user.getLDAPUser() && userHolder.getPassword() != null && !userHolder.getPassword().equals( "" )) {
                    // origianlUser.setLastPasswordResetDate(Calendar.getInstance().getTime());
                    origianlUser.setPassword( encoder.encode( userHolder.getPassword() ) );
                }
                if (user.getLastName() != null)
                    origianlUser.setLastName( user.getLastName() );
                if (user.getFirstName() != null)
                    origianlUser.setFirstName( user.getFirstName() );
                if (user.getEmail() != null)
                    origianlUser.setEmail( user.getEmail() );

                if (user.getEnabled() != null)
                    origianlUser.setEnabled( user.getEnabled() );

                user = userRepo.save( origianlUser );
            }
        } catch (Exception e) {
            log.error( "Error Editing user : " + user.getUserID() + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.USER_EDIT_FAILED.getCode(),
                    Errors.USER_EDIT_FAILED.getDesc() + ", exception: " + e );
        }
        if (userHolder.getSelectedRoles() != null || userHolder.getSelectedRoles() != null) {
            log.info( "User Groups/Roles modification requested for user: " + user );
            if (userHolder.getSelectedGroups() != null && !userHolder.getSelectedGroups().isEmpty()) {
                deleteGroups( user );
                createGroups( user, userHolder.getSelectedGroups() );
                log.info( "User: " + user.getUserID() + " Groups have been updated" );
            }
            if (userHolder.getSelectedRoles() != null && !userHolder.getSelectedRoles().isEmpty()) {
                deleteRoles( user );
                createRoles( user, userHolder.getSelectedRoles() );
                log.info( "User: " + user.getUserID() + " Roles have been updated" );
            }
        }
        return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );

    }

    @GetMapping("disable/{userID}")
    @Transactional
    public ResponseEntity<?> disableUser(@PathVariable("userID") Integer userID) throws GeneralException {
        Users user = userRepo.findById( userID ).orElse( null );
        // validateSuperUser(user);
        user.setEnabled( false );
        userRepo.save( user );
        return new ResponseEntity<ResponseCode>( new ResponseCode( Errors.SUCCESSFUL ), HttpStatus.OK );
    }

    @Transactional
    @GetMapping("enable/{userID}")
    public ResponseEntity<?> enableUser(@PathVariable("userID") Integer userID) throws GeneralException {
        Users user = userRepo.findById( userID ).orElse( null );
        user.setEnabled( true );
        userRepo.save( user );
        return new ResponseEntity<ResponseCode>( new ResponseCode( Errors.SUCCESSFUL ), HttpStatus.OK );
    }

    @GetMapping("groups/{userID}")
    public List<Groups> getUsersGroup(@Valid @PathVariable("userID") Integer userID) {
        List<Groups> usergroupsList = userGroupsRepo.findGroupsOfUser( userID );
        return usergroupsList;
    }

    @GetMapping("roles/{userID}")
    public List<Roles> getUsersRole(@Valid @PathVariable("userID") Integer userID) {
        List<Roles> userRoles = usersRolesRepo.getUserRoles( userID );
        return userRoles;
    }

    private void createGroups(Users user, List<Integer> groups) throws GeneralException {

        try {
            if (groups != null && !groups.isEmpty())
                for (Integer group : groups) {
                    Usergroups ug = new Usergroups();
                    ug.setGroupID( new Groups( group ) );
                    ug.setUserID( user );
                    userGroupsRepo.save( ug );
                    ug = null;
                }
        } catch (Exception e) {
            log.error( "Error Creating user groups: " + user.getUserID() + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.USER_CREATED_GROUPS_FAILED.getCode(),
                    Errors.USER_CREATED_GROUPS_FAILED.getDesc() + ", exception: " + e );
        }
    }

    private void createRoles(Users user, List<Integer> roles) throws GeneralException {
        try {
            if (roles != null && !roles.isEmpty())
                for (Integer role : roles) {
                    Userroles ur = new Userroles();
                    ur.setRoleID( new Roles( role ) );
                    ur.setUserID( user );
                    usersRolesRepo.save( ur );
                    ur = null;
                }
        } catch (Exception e) {
            log.error( "Error Creating user roles: " + user.getUserID() + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.USER_CREATED_ROLES_FAILED.getCode(),
                    Errors.USER_CREATED_ROLES_FAILED.getDesc() + ", exception: " + e );
        }

    }

    private void deleteRoles(Users userID) throws GeneralException {
        try {
            usersRolesRepo.deleteByUserID( userID );
        } catch (Exception e) {
            log.error( "Error Deleting user roles: " + userID + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.USER_DELETE_ROLES_FAILED.getCode(),
                    Errors.USER_DELETE_ROLES_FAILED.getDesc() + ", exception: " + e );
        }
    }

    private void deleteGroups(Users userID) throws GeneralException {

        try {
            userGroupsRepo.deleteByUserID( userID );
        } catch (Exception e) {
            log.error( "Error Deleting user Groups: " + userID + ", error: " + e );
            e.printStackTrace();
            throw new GeneralException( Errors.USER_DELETE_GROUPS_FAILED.getCode(),
                    Errors.USER_DELETE_GROUPS_FAILED.getDesc() + ", exception: " + e );

        }
    }

    @GetMapping("id/{userID}")
    public ResponseEntity<Users> getUserbyId(@Valid @PathVariable("userID") Integer userID) {

        Users user = userRepo.findById( userID ).orElse( null );
        if (user != null) {
            return ResponseEntity.ok( user );
        } else
            return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<?> delete(UserHolder object, Principal principal) throws GeneralException {
        return null;
    }
}
