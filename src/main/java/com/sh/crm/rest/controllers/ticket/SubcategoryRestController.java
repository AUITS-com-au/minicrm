package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Maincategory;
import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.repos.tickets.SubcategoryRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(value = "subcategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubcategoryRestController extends BasicController<Subcategory> {

    @Autowired
    private SubcategoryRepo subcategoryRepo;

    @Override
    @TicketsAdmin
    public ResponseEntity<?> create(@RequestBody Subcategory subcategory, Principal principal) throws GeneralException {
        if (subcategory != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to create new sub category {}", subcategory );
            subcategory.setEnabled( true );
            subcategory.setId( null );
            try {
                Maincategory maincategory = subcategory.getMainCategory();
                maincategory = mainCategoryRepo.findById( maincategory.getId() ).orElse( null );
                subcategory.setMainCategory( maincategory );
                subcategoryRepo.save( subcategory );
                if (log.isDebugEnabled())
                    log.debug( "Sub-category {} created successfully", subcategory );
                return ResponseEntity.ok( subcategoryRepo.findAll() );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ) );
    }

    @Override
    @TicketsAdmin
    public ResponseEntity<?> edit(@RequestBody Subcategory subcategory, Principal principal) throws GeneralException {
        if (subcategory != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to edit sub category {}", subcategory );
            try {
                Maincategory maincategory = subcategory.getMainCategory();
                maincategory = mainCategoryRepo.findById( maincategory.getId() ).orElse( null );
                subcategory.setMainCategory( maincategory );
                subcategoryRepo.save( subcategory );
                if (log.isDebugEnabled())
                    log.debug( "Sub-category {} modified successfully", subcategory );
                return ResponseEntity.ok( subcategoryRepo.findAll() );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }

        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
    }

    @GetMapping("/authorized/{mainCat}")
    public Iterable<?> getAuthorized(Principal principal, @PathVariable("mainCat") Integer mainCat) throws GeneralException {
        return topicsPermissionsRepo.getUserSubCats( principal.getName(), mainCat );
    }

    @GetMapping("/active/{mainCat}")
    public Iterable<?> getActive(Principal principal, @PathVariable("mainCat") Integer mainCat) throws GeneralException {
        return subcategoryRepo.findByMainCategory_IdAndEnabledTrue( mainCat );
    }

    @TicketsAdmin
    @GetMapping("/all/{mainCat}")
    public Iterable<?> getAllByCat(Principal principal, @PathVariable("mainCat") Integer mainCat) throws GeneralException {
        return subcategoryRepo.findByMainCategory_IdOrderByCreatedByDesc( mainCat );
    }

    @TicketsAdmin
    @GetMapping("change/{subCat}/{newStatus}")
    public ResponseEntity<?> changeStatus(@PathVariable("subCat") Integer subCat,
                                          @PathVariable("newStatus") Boolean newStatus) {
        if (subCat != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to change sub category {} " +
                        "status to {}", subCat, newStatus );
            try {
                Optional<Subcategory> optSub = subcategoryRepo.findById( subCat );
                if (!optSub.isPresent())
                    throw new GeneralException( Errors.CANNOT_EDIT_OBJECT );
                Subcategory subcategory = optSub.get();
                subcategory.setEnabled( newStatus );
                subcategoryRepo.save( subcategory );
                return ResponseEntity.ok( subcategoryRepo.findAll() );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );

    }

    @Override
    @TicketsAdmin
    protected Iterable<?> all() {
        return subcategoryRepo.findAll();
    }

    @GetMapping(value = "/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<?> findByID(@PathVariable("ID") Integer id) {
        return ResponseEntity.ok( subcategoryRepo.findById( id ).orElse( null ) );
    }

    @Override
    public ResponseEntity<?> delete(Subcategory subcategory, Principal principal) throws GeneralException {
        return null;
    }

}
