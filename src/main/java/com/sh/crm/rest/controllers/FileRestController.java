package com.sh.crm.rest.controllers;

import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Attachments;
import com.sh.crm.services.storage.DownloadFile;
import com.sh.crm.services.storage.FileDBStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/upload")
public class FileRestController {

    private static final Logger logger = LoggerFactory.getLogger( FileRestController.class );


    @Autowired
    private FileDBStorageService fileDBStorageService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws GeneralException {
        // FileInfo fileInfo = fileStorageService.storeFile( file );

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path( "/downloadFile/" )
//                .path( fileInfo.getNewFileName() )
//                .toUriString();

        Attachments attachments = fileDBStorageService.storeFile( file );

        return ResponseEntity.ok( attachments.getId() );
    }

    @RequestMapping(value = "uploadMultipleFiles",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws GeneralException {
        logger.debug( "received request to upload multiple files" );
        return Arrays.asList( files )
                .stream()
                .map( file -> {
                    Attachments attachments = null;
                    try {
                        attachments = fileDBStorageService.storeFile( file );
                    } catch (Exception e) {
                        LoggingUtils.logStackTrace( logger, e, "error" );
                    }
                    return attachments.getId();
                } )
                .collect( Collectors.toList() );
    }


    @GetMapping("downloadFile/{attachmentID}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("attachmentID") Long attachmentID, HttpServletRequest request) throws GeneralException, Exception {
        // Load file as Resource
        // Resource resource = fileStorageService.loadFileAsResource( fileInfo.getNewFileName() );
        DownloadFile downloadFile = fileDBStorageService.getFile( attachmentID );
        Resource resource = downloadFile.getResource();
        String contentType = downloadFile.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType( MediaType.parseMediaType( contentType ) )
                .header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFile.getFileName() + "\"" )
                .body( resource );
    }


}
