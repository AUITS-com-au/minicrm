package com.sh.crm.jpa.repos.users;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.repos.custom.GetUsersPermissionsCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class PermissionsRepoImpl implements GetUsersPermissionsCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Permissions> usersPermission(String username) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetPermissions" );
        //findUsersPerms.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        findUsersPerms.setParameter( 1, username );

        //  .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)

        return findUsersPerms.getResultList();
    }
}
