package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.general.holders.SearchTicketsContainer;
import com.sh.crm.general.holders.SearchTicketsResult;
import com.sh.crm.general.holders.SearchTicketsSorting;
import com.sh.crm.jpa.entities.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TicketsRepoImpl implements TicketsRepoCustom {
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

    private List<Predicate> getPredicates(SearchTicketsContainer st, Root<Ticket> root) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Ticket, Topic> joinTopic = root.join( Ticket_.topic, JoinType.LEFT );
        Expression<Topic> topicExpression = root.get( Ticket_.topic );
        //Expression<Topic> originalTopicExp = root.get( Ticket_.originalTopic );
        if (st.getSearchUser() != null && !st.getSearchUser().isEmpty() && !st.getSkipValidation()) {
            ListJoin<Topic, GeneratedTopicPermissions> permissionsJoin = joinTopic.join( Topic_.generatedTopicPermissionsList, JoinType.LEFT );
            Expression<String> userExp = permissionsJoin.get( GeneratedTopicPermissions_.userName );
            predicates.add( getCriteriaBuilder().equal( userExp, st.getSearchUser() ) );
            Expression<Boolean> canReadExp = permissionsJoin.get( GeneratedTopicPermissions_.canRead );
//            Expression<Boolean> canWriteExp = permissionsJoin.get( (GeneratedTopicPermissions_.canModify) );
//            Expression<Boolean> canWriteReplyExp= permissionsJoin.get( (GeneratedTopicPermissions_.canReply) );
//            Expression<Boolean> canReopenExp = permissionsJoin.get( (GeneratedTopicPermissions_.canReopen) );
//            Expression<Boolean> canResolve = permissionsJoin.get( (GeneratedTopicPermissions_.canResolve) );
//            Expression<Boolean> canRunReport = permissionsJoin.get( (GeneratedTopicPermissions_.canRunReport) );
            predicates.add( getCriteriaBuilder().isTrue( canReadExp ) );
        }

        if (st.getClosed() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.closed ), st.getClosed() ) );
        }
        if (st.getCrossedAllSla() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.crossedAllSLA ), st.getCrossedAllSla() ) );
        }
        if (st.getDeleted() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.deleted ), st.getDeleted() ) );
        }
        if (st.getAssignedTo() != null && !st.getAssignedTo().isEmpty()) {
            predicates.add( root.get( Ticket_.assignedTo ).in( st.getAssignedTo() ) );
        }
        if (st.getCreatedBy() != null && !st.getCreatedBy().isEmpty()) {
            predicates.add( root.get( Ticket_.createdBy ).in( st.getCreatedBy() ) );
        }
        if (st.getCustomerAccounts() != null && !st.getCustomerAccounts().isEmpty()) {
            predicates.add( root.get( Ticket_.customerAccount ).in( st.getCustomerAccounts() ) );
        }
        if (st.getStartDate() != null && st.getEndDate() != null && st.getStartDate() > 0 && st.getEndDate() > 0) {
            predicates.add( getCriteriaBuilder().between( root.get( Ticket_.creationDate ), new Date( st.getStartDate() ), new Date( st.getEndDate() ) ) );
        }
        if (st.getSolved() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.solved ),
                    st.getSolved() ) );
        }
        if (st.getLanguage() != null && !st.getLanguage().isEmpty()) {
            predicates.add( root.get( Ticket_.language ).in( st.getLanguage() ) );
        }
        if (st.getPriority() != null && !st.getPriority().isEmpty()) {
            predicates.add( root.get( Ticket_.priority ).in( st.getPriority() ) );
        }
        if (st.getTypes() != null && !st.getTypes().isEmpty()) {
            predicates.add( root.get( Ticket_.ticketType ).in( st.getTypes() ) );
        }
        if (st.getSourceChannels() != null && !st.getSourceChannels().isEmpty()) {
            predicates.add( root.get( Ticket_.sourceChannel ).in( st.getSourceChannels() ) );
        }
        if (st.getStatus() != null && !st.getStatus().isEmpty()) {
            predicates.add( root.get( Ticket_.currentStatus ).in( st.getStatus() ) );
        }
        if (st.getTopics() != null && !st.getTopics().isEmpty()) {
            predicates.add( root.get( Ticket_.topic ).get( "ID" ).in( st.getTopics() ) );
        }
        if (st.getSubCats() != null && !st.getSubCats().isEmpty()) {
            predicates.add( root.get( Ticket_.topic ).get( Topic_.subCategory ).get( "ID" ).in( st.getSubCats() ) );
        }
        if (st.getMainCats() != null && !st.getMainCats().isEmpty()) {
            predicates.add( root.get( Ticket_.topic ).get( Topic_.subCategory ).get( Subcategory_.mainCategory ).get( "ID" ).in( st.getMainCats() ) );
        }
        if (st.getOriginalTopics() != null && !st.getOriginalTopics().isEmpty()) {
            predicates.add( root.get( Ticket_.originalTopic ).get( "ID" ).in( st.getOriginalTopics() ) );
        }
        if (st.getOriginalSubCats() != null && !st.getOriginalSubCats().isEmpty()) {
            predicates.add( root.get( Ticket_.originalTopic ).get( Topic_.subCategory ).get( "ID" ).in( st.getOriginalSubCats() ) );
        }
        if (st.getOriginalMainCats() != null && !st.getOriginalMainCats().isEmpty()) {
            predicates.add( root.get( Ticket_.originalTopic ).get( Topic_.subCategory ).get( Subcategory_.mainCategory ).get( "ID" ).in( st.getOriginalMainCats() ) );
        }

        return predicates;
    }

    private List<Order> getOrderBy(SearchTicketsSorting sorting) {
        List<Order> orderList = new ArrayList<>();
        return orderList;
    }

    @Override
    public SearchTicketsResult searchTickets(SearchTicketsContainer searchTicketsContainer) {
        SearchTicketsResult result = new SearchTicketsResult();


        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = (CriteriaQuery<Ticket>) getCriteriaQuery( cb, Ticket.class );
        Root<Ticket> root = (Root<Ticket>) getRoot( cq, Ticket.class );
        List<Predicate> predicates = getPredicates( searchTicketsContainer, root );
        if (predicates != null && !predicates.isEmpty()) {
            cq.where( predicates.toArray( new Predicate[]{} ) ).distinct( true );
        }
        if (searchTicketsContainer.getSorting() != null) {
            List<Order> orderList = getOrderBy( searchTicketsContainer.getSorting() );
            if (!orderList.isEmpty()) {
                cq.orderBy( orderList );
            }
        }
        TypedQuery<Ticket> query = em.createQuery( cq );
        query.setMaxResults( searchTicketsContainer.getSize() );
        query.setFirstResult( searchTicketsContainer.getPage() );
        List<Ticket> rs = query.getResultList();

        if (rs != null && !rs.isEmpty()) {
            result.setContent( rs );

            result.setNumberOfElements( rs.size() );
            CriteriaQuery<Long> countCQ = cb.createQuery( Long.class );
            Root root2 = countCQ.from( Ticket.class );
            countCQ.select( cb.countDistinct( root2.get( Ticket_.id ) ) );
            List<Predicate> predicates1 = getPredicates( searchTicketsContainer, root2 );
            countCQ.where( predicates1.toArray( new Predicate[]{} ) );
            Long count = em.createQuery( countCQ ).getSingleResult();
            result.setTotalElements( count );
            result.setSize( searchTicketsContainer.getSize() );
            result.setNumber( searchTicketsContainer.getPage() );
            if (searchTicketsContainer.getPage() == 0)
                result.setFirst( true );
            if (searchTicketsContainer.getSize() > 0) {
                int totalPages = (int) Math.ceil( count / (double) searchTicketsContainer.getSize() );
                result.setTotalPages( totalPages );
                if (result.getNumber() == totalPages - 1) {
                    result.setLast( true );
                }
            }
            countCQ = null;
        }
        cb = null;
        cq = null;
        searchTicketsContainer = null;
        predicates = null;
        return result;
    }
}
