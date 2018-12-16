package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.repos.tickets.SubcategoryRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("subcategories")
public class SubcategoryRestController extends BasicController<Subcategory> {

    @Autowired
    private SubcategoryRepo subcategoryRepo;

    @Override
    @TicketsAdmin
    public ResponseEntity<?> create(Subcategory subcategory, Principal principal) throws GeneralException {
        if (subcategory != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to create new sub category {}", subcategory );
            subcategory.setEnabled( true );
            subcategory.setId( null );

            try {
                subcategoryRepo.save( subcategory );
                if (log.isDebugEnabled())
                    log.debug( "Sub-category {} created successfully", subcategory );
                return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
            } catch (Exception e) {
                LoggingUtils.logStackTrace( log, e, LoggingUtils.ERROR );
            }

        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ) );
    }

    @Override
    @TicketsAdmin
    public ResponseEntity<?> edit(Subcategory subcategory, Principal principal) throws GeneralException {
        if (subcategory != null) {
            if (log.isDebugEnabled())
                log.debug( "Received request to edit sub category {}", subcategory );
            try {
                subcategoryRepo.save( subcategory );
                if (log.isDebugEnabled())
                    log.debug( "Sub-category {} modified successfully", subcategory );
                return ResponseEntity.ok( new ResponseCode( Errors.SUCCESSFUL ) );
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

    @Override
    @TicketsAdmin
    protected Iterable<?> all() {
        return subcategoryRepo.findAll();
    }

    @GetMapping(value = "/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<?> findByID(@PathVariable("ID") Integer id) {
        return ResponseEntity.ok( subcategoryRepo.findOne( id ) );
    }

    @Override
    public ResponseEntity<?> delete(Subcategory subcategory, Principal principal) throws GeneralException {
        return null;
    }

}
