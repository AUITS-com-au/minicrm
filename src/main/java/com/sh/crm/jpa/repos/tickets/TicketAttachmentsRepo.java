package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.TicketAttachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketAttachmentsRepo extends JpaRepository<TicketAttachments, Long> {
    List<TicketAttachments> findByTicketID(long ticketID);

    List<TicketAttachments> findByDataID(long dataID);

    @Query("select distinct ta.attachmentID from TicketAttachments ta where ta.dataID=?1")
    List<Long> getAttachmentsByDataID(long dataID);

    @Query("select distinct ta.attachmentID from TicketAttachments ta where ta.ticketID=?1 and ta.dataID is null")
    List<Long> getAttachmentsIDByTicketID(long ticketID);
}
