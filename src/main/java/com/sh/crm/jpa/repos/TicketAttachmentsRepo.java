package com.sh.crm.jpa.repos;

import com.sh.crm.jpa.entities.TicketAttachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketAttachmentsRepo extends JpaRepository<TicketAttachments, Long> {
    List<TicketAttachments> findByTicketID(long ticketID);

    List<TicketAttachments> findByHistoryID(long historyID);

    @Query("select distinct ta.attachmentID from TicketAttachments ta where ta.historyID=?1")
    List<Long> getAttachmentsID(long historyID);

    @Query("select distinct ta.attachmentID from TicketAttachments ta where ta.ticketID=?1")
    List<Long> getAttachmentsIDByTicketID(long ticketID);
}
