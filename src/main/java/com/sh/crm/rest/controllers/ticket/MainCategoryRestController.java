package com.sh.crm.rest.controllers.ticket;

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

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(value = "categories", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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
                return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
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
                mainCategoryRepo.save( original );
                if (log.isDebugEnabled())
                    log.debug( "Main category {} modified successfully", original );
                return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
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
                return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
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
