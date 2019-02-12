package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentsRepo extends JpaRepository<Attachments, Long> {
    List<Attachments> findByIdIn(List<Long> ids);

    @Query(value = "SELECT  [ID]      ,[FileName]      ,[FileType]    " +
            "  ,[Hash]      ,null  as [RAWContent]      ,[FilePath]    " +
            "  ,[CreatedBy]      ,[CreationDate]      ,[ModifiedBy]    " +
            "  ,[ModificationDate]    " +
            "  ,[FileDesc]  FROM  [attachments] where id in ?1", nativeQuery = true)
    List<Attachments> findInfo(List<Long> ids);
}
