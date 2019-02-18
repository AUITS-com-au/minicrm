package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Ticket;
import com.sh.crm.jpa.entities.Ticketlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TicketLocksRepo extends JpaRepository<Ticketlock, Long> {
    List<Ticketlock> getByTicketIDAndExpiresOnAfterAndExpiredIsFalse(Ticket ticketID, Date expiresOnAfter);

    @Transactional
    @Modifying
    @Query("update Ticketlock tl set tl.expired=true,tl.expiresOn=CURRENT_TIMESTAMP where tl.userID=?1")
    int invalidateExistingUserLocks(String user);

    @Transactional
    @Modifying
    @Query("update Ticketlock lock set lock.expired=true where lock.lockID=?1")
    void invalidateLock(long lockID);

}
