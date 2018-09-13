package com.sh.crm.jpa.repos;

import com.sh.crm.jpa.entities.Permissions;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class PermissionsRepoImpl implements GetUsersPermissionsCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Permissions> usersPermission(Integer id) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery("GetPermissions");
        //findUsersPerms.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        findUsersPerms.setParameter(1, id);

        //  .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)

        return findUsersPerms.getResultList();
    }
}
