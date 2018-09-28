package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Maincategory;
import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.jpa.repos.custom.GetUsersTopicsCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.HashSet;
import java.util.Set;

public class TopicsPermissionsRepoImpl implements GetUsersTopicsCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Topic> getUserTopics(Integer userID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserTopics" );

        findUsersPerms.setParameter( 1, userID );

        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public Set<Topicspermissions> getUserTopicsPermissions(Integer userID) {
        return getUserTopicsPermissions( userID, -1 );
    }

    @Override
    public Set<Topicspermissions> getUserTopicsPermissions(Integer userID, Integer topicID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserTopicPermissions" );

        findUsersPerms.setParameter( 1, userID );
        findUsersPerms.setParameter( 2, topicID );
        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public Set<Topic> getUserTopics(Integer userID, Integer subcat) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserTopicsBySubCat" );

        findUsersPerms.setParameter( 1, userID );
        findUsersPerms.setParameter( 2, subcat );
        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public Set<Subcategory> getUserSubCats(Integer userID, Integer mainCat) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserSubCats" );

        findUsersPerms.setParameter( 1, userID );
        findUsersPerms.setParameter( 2, mainCat );
        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public Set<Maincategory> getUserMainCats(Integer userID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserMainCats" );

        findUsersPerms.setParameter( 1, userID );

        return new HashSet<>( findUsersPerms.getResultList() );
    }


}
