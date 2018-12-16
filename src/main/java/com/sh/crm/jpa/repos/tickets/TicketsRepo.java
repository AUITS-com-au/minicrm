package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketsRepo extends PagingAndSortingRepository<Ticket, Long>, TicketsRepoCustom {
    Page<Ticket> findByDeletedFalseOrderByCreationDateDesc(Pageable pageable);
}
