package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentsRepo extends JpaRepository<Attachments, Long> {
    List<Attachments> findByIdIn(List<Long> ids);

    @Query("select at.id,at.createdBy,at.creationDate,at.fileType,at.fileName from Attachments at where at.id in ?1")
    List<Attachments> findInfo(List<Long> ids);
}
