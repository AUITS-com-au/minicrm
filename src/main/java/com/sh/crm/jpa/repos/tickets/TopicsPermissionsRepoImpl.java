package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.GetAuthorizedTopics;
import com.sh.crm.jpa.entities.*;
import com.sh.crm.jpa.repos.custom.GetUsersTopicsCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopicsPermissionsRepoImpl implements GetUsersTopicsCustom {
    @PersistenceContext
    private EntityManager em;

    private CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    private CriteriaQuery<?> getCriteriaQuery(CriteriaBuilder cb, Class<?> c) {
        return cb.createQuery( c );
    }

    private Root<?> getRoot(CriteriaQuery<?> cq, Class<?> c) {
        return cq.from( c );
    }


    public List<Maincategory> getAuthorizedMainCat(GetAuthorizedTopics request) throws GeneralException {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery cq = getCriteriaQuery( getCriteriaBuilder(), Maincategory.class );
        Root root = getRoot( cq, GeneratedTopicPermissions.class );
        Expression<Maincategory> join = root.join( GeneratedTopicPermissions_.TOPIC, JoinType.LEFT ).get( Topic_.subCategory ).get( Subcategory_.mainCategory );

        predicates = fillPredicates( request.getPermissions(), predicates, root );
        predicates.add( getCriteriaBuilder().equal( root.get( GeneratedTopicPermissions_.userName ), request.getAssigne() ) );
        predicates.add( getCriteriaBuilder().isTrue( ((Path<Maincategory>) join).get( Maincategory_.enabled ) ) );

        cq.where( predicates.toArray( new Predicate[]{} ) );

        cq.select( join ).distinct( true );
        TypedQuery<Maincategory> query = em.createQuery( cq );
        return query.getResultList();
    }

    public List<Subcategory> getAuthorizedSubCat(GetAuthorizedTopics request) throws GeneralException {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery cq = getCriteriaQuery( getCriteriaBuilder(), Subcategory.class );
        Root root = getRoot( cq, GeneratedTopicPermissions.class );
        Expression<Subcategory> join = root.join( GeneratedTopicPermissions_.TOPIC, JoinType.LEFT ).get( Topic_.subCategory );
        predicates = fillPredicates( request.getPermissions(), predicates, root );
        predicates.add( getCriteriaBuilder().equal( root.get( GeneratedTopicPermissions_.userName ), request.getAssigne() ) );
        predicates.add( getCriteriaBuilder().isTrue( ((Path<Subcategory>) join).get( Subcategory_.enabled ) ) );
        if (request.getMainCat() != null) {
            Expression<Maincategory> joinMainCat = ((Path<Subcategory>) join).get( Subcategory_.mainCategory );
            predicates.add( getCriteriaBuilder().equal( ((Path<Maincategory>) joinMainCat).get( Maincategory_.id ), request.getMainCat() ) );
        }
        cq.where( predicates.toArray( new Predicate[]{} ) );
        cq.select( join ).distinct( true );
        TypedQuery<Subcategory> query = em.createQuery( cq );
        return query.getResultList();
    }

    @Override
    public List<Topic> getAuthorizedTopics(GetAuthorizedTopics request) throws GeneralException {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery cq = getCriteriaQuery( getCriteriaBuilder(), Topic.class );
        Root root = getRoot( cq, GeneratedTopicPermissions.class );

        predicates = fillPredicates( request.getPermissions(), predicates, root );
        predicates.add( getCriteriaBuilder().equal( root.get( GeneratedTopicPermissions_.userName ), request.getAssigne() ) );
        predicates.add( getCriteriaBuilder().isTrue( root.get( GeneratedTopicPermissions_.topic ).get( Topic_.enabled ) ) );
        if (request.getSubCat() != null) {
            Expression<Subcategory> join = root.join( GeneratedTopicPermissions_.TOPIC, JoinType.LEFT ).
                    get( Topic_.subCategory );
            predicates.add( getCriteriaBuilder().equal( ((Path<Subcategory>) join).get( Subcategory_.id ), request.getSubCat() ) );
        }
        cq.where( predicates.toArray( new Predicate[]{} ) );
        cq.select( root.get( GeneratedTopicPermissions_.topic ) ).distinct( true );
        TypedQuery<Topic> query = em.createQuery( cq );
        return query.getResultList();
    }

    private List<Predicate> fillPredicates(List<String> permissions, List<Predicate> predicates, Root root) throws GeneralException {
        for (String permission : permissions) {
            switch (permission) {
                case "read":
                    predicates.add( getCriteriaBuilder().isTrue( root.get( GeneratedTopicPermissions_.CAN_READ ) ) );
                    break;
                case "assign":
                    predicates.add( getCriteriaBuilder().isTrue( root.get( GeneratedTopicPermissions_.CAN_ASSIGN ) ) );
                    break;
                case "chgDpt":
                case "chgdpt":
                    predicates.add( getCriteriaBuilder().isTrue( root.get( GeneratedTopicPermissions_.CAN_CHG_DPT ) ) );
                    break;
                case "close":
                    predicates.add( getCriteriaBuilder().isTrue( root.get( GeneratedTopicPermissions_.CAN_CLOSE ) ) );
                    break;
                case "create":
                    predicates.add( getCriteriaBuilder().isTrue( root.get( GeneratedTopicPermissions_.canCreate ) ) );
                    break;
                case "reopen":
                    predicates.add( getCriteriaBuilder().isTrue( root.get( GeneratedTopicPermissions_.canReopen ) ) );
                    break;
                default:
                    throw new GeneralException( Errors.GENERAL_ERROR );
            }
        }

        return predicates;
    }

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
