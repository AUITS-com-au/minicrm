package com.sh.crm.rest.controllers.ticket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Maincategory;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(value = "categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainCategoryRestController extends BasicController<Maincategory> {
    @TicketsAdmin
    public ResponseEntity<?> create(@RequestBody Maincategory maincategory, Principal principal) {
        if (maincategory != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to create new main category {}", maincategory );
            maincategory.setEnabled( true );
            maincategory.setId( null );
            try {
                mainCategoryRepo.save( maincategory );
                if (log.isDebugEnabled())
                    log.debug( "Main category {} created successfully", maincategory );
                return ResponseEntity.ok( mainCategoryRepo.findAll() );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }

        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ) );
    }

    @Override
    @TicketsAdmin
    protected Iterable<?> all() {
        return mainCategoryRepo.findAll();
    }

    @Override
    protected Iterable<?> active() {
        return mainCategoryRepo.findByEnabledTrue();
    }

    @TicketsAdmin
    public ResponseEntity<?> edit(@RequestBody Maincategory maincategory, Principal principal) throws GeneralException {
        if (maincategory != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to modify main category {}", maincategory );
            try {
                Optional<Maincategory> optional = mainCategoryRepo.findById( maincategory.getId() );
                if (!optional.isPresent())
                    throw new GeneralException( Errors.CANNOT_EDIT_OBJECT );
                Maincategory original = optional.get();
                original.setEnabled( maincategory.getEnabled() );
                original.setArabicLabel( maincategory.getArabicLabel() );
                original.setEnglishLabel( maincategory.getEnglishLabel() );
                original.setConfiguration( maincategory.getConfiguration() );
                mainCategoryRepo.save( original );
                if (log.isDebugEnabled())
                    log.debug( "Main category {} modified successfully", original );
                return ResponseEntity.ok( mainCategoryRepo.findAll() );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
    }

    @TicketsAdmin
    @GetMapping("change/{mainCat}/{newStatus}")
    public ResponseEntity<?> changeStatus(@PathVariable("mainCat") Integer mainCat,
                                          @PathVariable("newStatus") Boolean newStatus) {
        if (mainCat != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to change main category {} " +
                        "status to {}", mainCat, newStatus );
            try {
                Optional<Maincategory> optionalMaincategory = mainCategoryRepo.findById( mainCat );
                if (!optionalMaincategory.isPresent())
                    throw new GeneralException( Errors.CANNOT_EDIT_OBJECT );
                Maincategory maincategory = optionalMaincategory.get();
                maincategory.setEnabled( newStatus );
                mainCategoryRepo.save( maincategory );
                return ResponseEntity.ok( mainCategoryRepo.findAll() );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );

    }

    @GetMapping("config/{mainCat}")
    ResponseEntity<?> getConfiguration(@PathVariable("mainCat") Integer mainCat) throws IOException, GeneralException {
        Maincategory maincategory =
                mainCategoryRepo.findById( mainCat ).orElse( null );
        if (maincategory == null)
            throw new GeneralException( Errors.CANNOT_EDIT_OBJECT );
        if (maincategory.getConfiguration() == null || maincategory.getConfiguration().equalsIgnoreCase( "" ))
            return null;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode object = mapper.readTree( maincategory.getConfiguration() );

        return ResponseEntity.ok( object );
    }


    @TicketsAdmin
    public ResponseEntity<?> delete(@RequestBody Maincategory maincategory, Principal principal) throws GeneralException {
        if (maincategory != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to delete main category {}", maincategory );
            try {
                Optional<Maincategory> optionalMaincategory = mainCategoryRepo.findById( maincategory.getId() );
                if (!optionalMaincategory.isPresent())
                    throw new GeneralException( Errors.CANNOT_EDIT_OBJECT );
                Maincategory maincategory1 = optionalMaincategory.get();
                maincategory1.setEnabled( false );
                mainCategoryRepo.save( maincategory1 );
                return ResponseEntity.ok( mainCategoryRepo.findAll() );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
    }

    @GetMapping(value = "/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    Maincategory findByID(@PathVariable("ID") Integer topicID) {
        Optional<Maincategory> optional = mainCategoryRepo.findById( topicID );
        Maincategory maincategory = null;
        if (optional.isPresent())
            maincategory = optional.get();
        return maincategory;
    }

    @Override
    protected Iterable<?> authorizedList(Principal principal) {

        return topicsPermissionsRepo.getUserMainCats( principal.getName() );
    }
}
