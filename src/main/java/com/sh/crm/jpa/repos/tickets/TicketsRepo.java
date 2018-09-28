package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Ticket;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketsRepo extends PagingAndSortingRepository<Ticket, Long> {
    Page<Ticket> findByDeletedFalse(Pageable pageable);
}
