package com.sh.crm.services.storage;

import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class FileStorageService {
    private static final Logger log = LoggerFactory.getLogger( FileStorageService.class );
    private Path fileStorageLocation;
    @Value("${mini.crm.uploadFolder}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        log.debug( "Logger directory configured value {}", uploadDir );
        this.fileStorageLocation = Paths.get( uploadDir )
                .toAbsolutePath().normalize();
        log.info( "The absolute path of upload directory will be {}", fileStorageLocation.toString() );
        try {
            Files.createDirectories( this.fileStorageLocation );
            log.trace( "Upload directory created successfully" );
        } catch (Exception ex) {
            LoggingUtils.logStackTrace( log, ex, "error" );

        }
    }

    public FileInfo storeFile(MultipartFile file) throws GeneralException {

        log.debug( "Storying multiple part file {}", file.getName() );
        String fileName = StringUtils.cleanPath( file.getOriginalFilename() );
        FileInfo info = new FileInfo();
        info.setOriginalFileName( fileName );

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains( ".." )) {
                throw new GeneralException( "Sorry! Filename contains invalid path sequence " + fileName );
            }
            if (fileName.contains( "." )) {
                info.setFileExt( fileName.substring( fileName.indexOf( '.' ) ) );
            }
            String newName = UUID.randomUUID().toString();
            info.setNewFileName( newName );

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve( newName );

            Files.copy( file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING );

            return info;
        } catch (IOException ex) {
            throw new GeneralException( "Could not store file " + fileName + ". Please try again!", ex );
        }
    }

    public Resource loadFileAsResource(String fileName) throws GeneralException {
        try {
            Path filePath = this.fileStorageLocation.resolve( fileName ).normalize();
            Resource resource = new UrlResource( filePath.toUri() );
            if (resource.exists()) {
                return resource;
            } else {
                throw new GeneralException( Errors.FILE_NOT_FOUND );
            }
        } catch (MalformedURLException ex) {
            throw new GeneralException( Errors.FILE_NOT_FOUND, ex );

        }
    }

    public Path loadFile(String fileName) throws GeneralException {
        try {
            Path filePath = this.fileStorageLocation.resolve( fileName ).normalize();

            if (Files.exists( filePath )) {
                return filePath;
            } else {
                throw new GeneralException( Errors.FILE_NOT_FOUND );
            }
        } catch (Exception ex) {
            throw new GeneralException( Errors.FILE_NOT_FOUND, ex );

        }
    }

    public UploadFileResponse getFileContent(UploadFileResponse fileInfo) throws Exception {

        Path path = loadFile( fileInfo.getFileName() );
        byte[] fileContent = Files.readAllBytes( path );
        fileInfo.setContent( fileContent );


        return fileInfo;

    }

    @Async
    public void deleteFiles(List<UploadFileResponse> files) {
        if (files != null) {
            for (UploadFileResponse file : files) {
                try {
                    Path filePath = this.fileStorageLocation.resolve( file.getFileName() ).normalize();
                    if (log.isDebugEnabled())
                        log.debug( "deleting file {} ", filePath.toString() );
                    Files.deleteIfExists( filePath );
                    if (log.isDebugEnabled())
                        log.debug( "file {} deleted successfully", filePath );
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            files = null;
        }
    }

}