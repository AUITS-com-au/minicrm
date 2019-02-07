package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Maincategory;
import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.jpa.repos.custom.GetUsersTopicsCustom;
import org.hibernate.jpa.spi.StoredProcedureQueryParameterRegistration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.HashSet;
import java.util.Set;

public class TopicsPermissionsRepoImpl implements GetUsersTopicsCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Topic> getUserTopics(String userID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserTopics" );
        findUsersPerms.setParameter( 1, userID );
        return new HashSet<>( findUsersPerms.getResultList() );
    }


    @Override
    public Set<Topicspermissions> getUserTopicsPermissions(String userID) {
        return getUserTopicsPermissions( userID, -1 );
    }

    @Override
    public Set<Topicspermissions> getUserTopicsPermissions(String userID, Integer topicID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserTopicPermissions" );

        findUsersPerms.setParameter( 1, userID );
        findUsersPerms.setParameter( 2, topicID );
        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public Set<Topic> getUserTopics(String userID, Integer subcat) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserTopicsBySubCat" );
        findUsersPerms.setParameter( 1, userID );
        findUsersPerms.setParameter( 2, subcat );
        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public Set<Subcategory> getUserSubCats(String userID, Integer mainCat) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserSubCats" );

        findUsersPerms.setParameter( 1, userID );
        findUsersPerms.setParameter( 2, mainCat );
        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public Set<Maincategory> getUserMainCats(String userID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GetUserMainCats" );

        findUsersPerms.setParameter( 1, userID );

        return new HashSet<>( findUsersPerms.getResultList() );
    }

    @Override
    public void generateGroupTopicsPermission(Integer topicID, String groupName, Integer groupID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GenerateGroupTopicsPermissions" );


        //findUsersPerms.registerStoredProcedureParameter( 1, Integer.class, ParameterMode.IN );
        findUsersPerms.setParameter( 1, topicID );
        findUsersPerms.setParameter( 2, groupName );
        findUsersPerms.setParameter( 3, groupID );

        findUsersPerms.execute();
    }

    @Override
    public void generateUserTopicsPermission(Integer topicID, String username, Integer userID) {
        StoredProcedureQuery findUsersPerms =
                em.createNamedStoredProcedureQuery( "GenerateUserTopicsPermissions" );
        findUsersPerms.setParameter( 1, topicID );
        findUsersPerms.setParameter( 2, username );
        findUsersPerms.setParameter( 3, userID );
        findUsersPerms.execute();
    }
}
