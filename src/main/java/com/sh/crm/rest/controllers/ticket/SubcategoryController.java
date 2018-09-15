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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subcategories")
public class SubcategoryController extends BasicController<Subcategory> {

    @Autowired
    private SubcategoryRepo subcategoryRepo;

    @Override
    @TicketsAdmin
    public ResponseEntity<?> create(Subcategory subcategory) throws GeneralException {
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
    public ResponseEntity<?> edit(Subcategory subcategory) throws GeneralException {
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

    @Override
    @TicketsAdmin
    protected Iterable<?> all() {
        return subcategoryRepo.findAll();
    }

    @Override
    public ResponseEntity<?> delete(Subcategory subcategory) throws GeneralException {
        return null;
    }
}
