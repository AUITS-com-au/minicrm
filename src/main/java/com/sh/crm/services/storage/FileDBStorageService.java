package com.sh.crm.services.storage;

import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.jpa.entities.Attachments;
import com.sh.crm.jpa.repos.tickets.AttachmentsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class FileDBStorageService {
    private static final Logger log = LoggerFactory.getLogger( FileDBStorageService.class );

    @Autowired
    private AttachmentsRepo attachmentsRepo;


    public Attachments storeFile(MultipartFile file) throws GeneralException {

        if (log.isDebugEnabled())
            log.debug( "Storing multiple part file {}", file.getName() );
        String fileName = StringUtils.cleanPath( file.getOriginalFilename() );


        Attachments attachments = new Attachments();
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains( ".." )) {
                throw new GeneralException( "Sorry! Filename contains invalid path sequence " + fileName );
            }

            attachments.setFileName( fileName );
            attachments.setFileType( file.getContentType() );

            attachments.setRAWContent( file.getBytes() );
            // Copy file to the target location (Replacing existing file with the same name)

            return attachmentsRepo.save( attachments );
        } catch (IOException ex) {
            throw new GeneralException( "Could not store file " + fileName + ". Please try again!", ex );
        }
    }

    public DownloadFile getFile(Long attachmentID) throws Exception {

        Attachments attachments = attachmentsRepo.findById( attachmentID ).orElse( null );
        if (attachments == null)
            return null;

        DownloadFile downloadFile = new DownloadFile();
        Resource resource = new ByteArrayResource( attachments.getRAWContent() );

        downloadFile.setContentType( attachments.getFileType() );
        downloadFile.setFileName( attachments.getFileName() );
        downloadFile.setLength( attachments.getRAWContent().length );

        downloadFile.setResource( resource );
        return downloadFile;
    }


    @Async
    public void deleteFiles(List<Long> files) {
        if (files != null) {
            for (Long attachments : files) {
                try {
                    attachmentsRepo.deleteById( attachments );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            files = null;
        }
    }

}