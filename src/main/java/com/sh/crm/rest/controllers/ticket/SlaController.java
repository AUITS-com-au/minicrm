package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.jpa.entities.Sla;
import com.sh.crm.jpa.repos.tickets.SlaRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "sla", produces = MediaType.APPLICATION_JSON_VALUE)
@TicketsAdmin
public class SlaController extends BasicController<Sla> {
    @Autowired
    private SlaRepo slaRepo;


    @Override
    protected Iterable<?> all() {
        return slaRepo.findAll();
    }

    @Override
    public ResponseEntity<?> create(@RequestBody Sla sla, Principal principal) throws GeneralException {
        if (sla != null) {
            sla.setId( null );
            sla = slaRepo.save( sla );
            return ResponseEntity.ok( slaRepo.findAll() );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ) );
    }

    @Override
    public ResponseEntity<?> edit(@RequestBody Sla sla, Principal principal) throws GeneralException {
        if (sla != null && sla.getId() != null) {
            sla = slaRepo.save( sla );
            return ResponseEntity.ok( slaRepo.findAll() );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
    }

    @Override
    public ResponseEntity<?> delete(@RequestBody Sla sla, Principal principal) throws GeneralException {
        return null;
    }

}
