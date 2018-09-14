package com.sh.crm.rest.controllers;

import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.services.storage.FileInfo;
import com.sh.crm.services.storage.FileStorageService;
import com.sh.crm.services.storage.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileRestController {

    private static final Logger logger = LoggerFactory.getLogger( FileRestController.class );

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws GeneralException {
        FileInfo fileInfo = fileStorageService.storeFile( file );

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path( "/downloadFile/" )
//                .path( fileInfo.getNewFileName() )
//                .toUriString();

        return new UploadFileResponse( fileInfo.getNewFileName(),
                file.getContentType(), file.getSize(), fileInfo.getOriginalFileName() );
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws GeneralException {
        return Arrays.asList( files )
                .stream()
                .map( file -> {
                    UploadFileResponse response = null;
                    try {
                        response = uploadFile( file );
                    } catch (Exception e) {
                        LoggingUtils.logStackTrace( logger, e, "error" );
                    }
                    return response;
                } )
                .collect( Collectors.toList() );
    }

    @PostMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestBody UploadFileResponse fileInfo, HttpServletRequest request) throws GeneralException, Exception {
        // Load file as Resource
        // Resource resource = fileStorageService.loadFileAsResource( fileInfo.getNewFileName() );
        Path path = fileStorageService.loadFile( fileInfo.getFileName() );
        Resource resource = new UrlResource( path.toUri() );
        // Try to determine file's content type
        String contentType = fileInfo.getFileType();

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType( MediaType.parseMediaType( contentType ) )
                .header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.getOrignalFileName() + "\"" )
                .body( resource );
    }
}