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
            predicates.add( getCriteriaBuilder().isTrue( canReadExp ) );
        }

        if (st.getClosed() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.closed ), st.getClosed() ) );
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
        if (st.getStartDate() != null) {
            predicates.add( getCriteriaBuilder().greaterThanOrEqualTo( root.get( Ticket_.creationDate ), st.getStartDate() ) );
        }
        if (st.getEndDate() != null) {
            predicates.add( getCriteriaBuilder().lessThanOrEqualTo( root.get( Ticket_.creationDate ), st.getEndDate() ) );
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
            predicates.add( root.get( Ticket_.topic ).get( "id" ).in( st.getTopics() ) );
        }
        if (st.getSubCats() != null && !st.getSubCats().isEmpty()) {
            predicates.add( root.get( Ticket_.topic ).get( Topic_.subCategory ).get( "id" ).in( st.getSubCats() ) );
        }
        if (st.getMainCats() != null && !st.getMainCats().isEmpty()) {
            predicates.add( root.get( Ticket_.topic ).get( Topic_.subCategory ).get( Subcategory_.mainCategory ).get( "id" ).in( st.getMainCats() ) );
        }
        if (st.getOriginalTopics() != null && !st.getOriginalTopics().isEmpty()) {
            predicates.add( root.get( Ticket_.originalTopic ).get( "id" ).in( st.getOriginalTopics() ) );
        }
        if (st.getOriginalSubCats() != null && !st.getOriginalSubCats().isEmpty()) {
            predicates.add( root.get( Ticket_.originalTopic ).get( Topic_.subCategory ).get( "id" ).in( st.getOriginalSubCats() ) );
        }
        if (st.getOriginalMainCats() != null && !st.getOriginalMainCats().isEmpty()) {
            predicates.add( root.get( Ticket_.originalTopic ).get( Topic_.subCategory ).get( Subcategory_.mainCategory ).get( "id" ).in( st.getOriginalMainCats() ) );
        }
        if (st.getCrossedAllSLA() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.crossedAllSLA ), st.getCrossedAllSLA() ) );
        }
        if (st.getTotalCrossedTime() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.totalCrossedTime ), st.getTotalCrossedTime() ) );
        }
        if (st.getNumberOfCrossedSLA() != null) {
            predicates.add( getCriteriaBuilder().equal( root.get( Ticket_.numberOfCrossedSLA ), st.getNumberOfCrossedSLA() ) );
        }

        if (st.getCustomerContainer() != null) {
            Join<Ticket, CustomerAccounts> joinCustomerAcc = root.join( Ticket_.customerAccount, JoinType.LEFT );
            List<Predicate> customerPredicates = new ArrayList<>();

            if (st.getCustomerContainer().getCustomerBasic() != null && !st.getCustomerContainer().getCustomerBasic().isEmpty()) {
                customerPredicates.add( getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.CUSTOMER_CI_F ), getFormattedForSQLLike( st.getCustomerContainer().getCustomerBasic() ) ) );
            }
            if (st.getCustomerContainer().getCustomerMobile() != null && !st.getCustomerContainer().getCustomerMobile().isEmpty()) {
                customerPredicates.add( getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.MOBILE ), getFormattedForSQLLike( st.getCustomerContainer().getCustomerMobile() ) ) );
            }
            if (st.getCustomerContainer().getCustomerName() != null && !st.getCustomerContainer().getCustomerName().isEmpty()) {
                customerPredicates.add( getCriteriaBuilder().or( getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.CUSTOMER_NAME_AR ), getFormattedForSQLLike( st.getCustomerContainer().getCustomerName() ) ), getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.CUSTOMER_NAME_EN ), getFormattedForSQLLike( st.getCustomerContainer().getCustomerName() ) ) ) );
            }
            if (st.getCustomerContainer().getCustomerBranch() != null && !st.getCustomerContainer().getCustomerBranch().isEmpty()) {
                customerPredicates.add( getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.BRANCH_NAME ), getFormattedForSQLLike( st.getCustomerContainer().getCustomerBranch() ) ) );
            }
            if (st.getCustomerContainer().getCustomerSegment() != null && !st.getCustomerContainer().getCustomerSegment().isEmpty()) {
                customerPredicates.add( getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.SEGMENT ), getFormattedForSQLLike( st.getCustomerContainer().getCustomerSegment() ) ) );
            }
            if (st.getCustomerContainer().getNan() != null && !st.getCustomerContainer().getNan().isEmpty()) {
                customerPredicates.add( getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.NIN ), getFormattedForSQLLike( st.getCustomerContainer().getNan() ) ) );
            }
            if (st.getCustomerContainer().getCustomerEmail() != null && !st.getCustomerContainer().getCustomerEmail().isEmpty()) {
                customerPredicates.add( getCriteriaBuilder().like( joinCustomerAcc.get( CustomerAccounts_.EMAIL ), getFormattedForSQLLike( st.getCustomerContainer().getCustomerEmail() ) ) );
            }
            if (customerPredicates != null && customerPredicates.size() > 0) {
                predicates.add( getCriteriaBuilder().or( customerPredicates.toArray( new Predicate[]{} ) ) );
            }
        }
        return predicates;
    }

    private String getFormattedForSQLLike(String str) {
        return "%" + str + "%";
    }

    private Order getOrderBy(Root root, SearchTicketsSorting sorting) {
        Order orderBy = null;
        if (sorting != null && sorting.getSortBy() != null && !sorting.getSortBy().isEmpty()) {
            orderBy = sorting.getSortType() == 1 ? getCriteriaBuilder().desc( root.get( sorting.getSortBy() ) ) :
                    getCriteriaBuilder().asc( root.get( sorting.getSortBy() ) );
        }
        return orderBy;
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
            Order orderby = getOrderBy( root, searchTicketsContainer.getSorting() );
            if (orderby != null) {
                cq.orderBy( orderby );
            }
        } else {
            cq.orderBy( getCriteriaBuilder().desc( root.get( Ticket_.priority ) ) );
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
