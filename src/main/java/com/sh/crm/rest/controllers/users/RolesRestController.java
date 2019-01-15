package com.sh.crm.rest.controllers.users;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.RoleHolder;
import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.entities.Rolepermissions;
import com.sh.crm.jpa.entities.Roles;
import com.sh.crm.jpa.repos.users.RolesPermissionsRepo;
import com.sh.crm.jpa.repos.users.RolesRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.RolesAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/roles")
@RolesAdmin
public class RolesRestController extends BasicController<RoleHolder> {
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private RolesPermissionsRepo rolesPermissionsRepo;


    @GetMapping("all")
    public List<Roles> all() {
        return rolesRepo.findAll();
    }

    @GetMapping("permissions/{roleID}")
    List<Permissions> getPermissions(@PathVariable("roleID") Integer roleID) {
        return rolesPermissionsRepo.getRolePermissions( new Roles( roleID ) );
    }


    public ResponseEntity<?> create(@RequestBody RoleHolder roleHolder, Principal principal) throws GeneralException {
        if (roleHolder != null && roleHolder.getRole() != null && roleHolder.getPermissions() != null && !roleHolder.getPermissions().isEmpty()) {
            Roles role = roleHolder.getRole();
            role.setId( null );
            try {
                rolesRepo.save( role );
                for (Permissions permissions : roleHolder.getPermissions()) {
                    Rolepermissions rp = new Rolepermissions();
                    rp.setPermissionID( permissions );
                    rp.setRoleID( role );
                    rolesPermissionsRepo.save( rp );
                    rp = null;
                }
                roleHolder = null;

            } catch (Exception e) {
                e.printStackTrace();
                throw new GeneralException( Errors.ROLE_CREATE_FAILED, e.toString() );
            }
        }
        return new ResponseEntity<ResponseCode>( new ResponseCode( Errors.SUCCESSFUL ), HttpStatus.OK );
    }

    public ResponseEntity<?> edit(@RequestBody RoleHolder roleHolder, Principal principal) throws GeneralException {
        if (roleHolder != null && roleHolder.getRole() != null && roleHolder.getPermissions() != null && !roleHolder.getPermissions().isEmpty()) {
            Roles role = roleHolder.getRole();
            Optional<Roles> optionalRoles = rolesRepo.findById( role.getId() );
            if (!optionalRoles.isPresent())
                throw new GeneralException( "Cannot find role" );
            try {
                Roles originalRole = optionalRoles.get();
                originalRole.setRole( role.getRole() );
                rolesRepo.save( originalRole );
                deleteRolePermissions( role );
                List<Permissions> permissions = roleHolder.getPermissions();
                for (Permissions permission : permissions) {
                    Rolepermissions rp = new Rolepermissions();
                    rp.setPermissionID( permission );
                    rp.setRoleID( originalRole );
                    rolesPermissionsRepo.save( rp );
                    rp = null;
                }
                roleHolder = null;

            } catch (Exception e) {
                e.printStackTrace();
                throw new GeneralException( Errors.ROLE_EDIT_FAILED, e.toString() );
            }
        }
        return new ResponseEntity<ResponseCode>( new ResponseCode( Errors.SUCCESSFUL ), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<?> delete(RoleHolder object, Principal principal) throws GeneralException {
        return null;
    }

    private void deleteRolePermissions(Roles role) {
        rolesPermissionsRepo.deleteAllByRoleID( role );

    }
}
