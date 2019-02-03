package com.sh.crm.rest.controllers.accounts;

import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.utils.Utils;
import com.sh.crm.jpa.entities.CustomerAccounts;
import com.sh.crm.rest.general.BasicController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('TICKET:USER','TICKET:ADMIN','Administrator')")
public class CustomerAccountsRestController extends BasicController<CustomerAccounts> {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> create(@RequestBody CustomerAccounts accounts, Principal principal) throws GeneralException {

        CustomerAccounts existing = customerAccountsRepo.checkExistingAccount( accounts.getId(), accounts.getNin(), accounts.getCustomerCIF() );

        if (existing != null)
            throw new GeneralException( Errors.CANNOT_CREATE_OBJECT, "Account Already Exists" );

        if ((accounts.getCustomerCIF() == null || accounts.getCustomerCIF().isEmpty()) && (accounts.getNin() == null || accounts.getNin().isEmpty()))
            throw new GeneralException( Errors.CANNOT_CREATE_OBJECT, "Customer Account is NOT Valid" );

        return ResponseEntity.ok( customerAccountsRepo.save( accounts ) );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> edit(@RequestBody CustomerAccounts accounts, Principal principal) throws GeneralException {
        CustomerAccounts originalAcct = customerAccountsRepo.checkExistingAccount( accounts.getId(), accounts.getNin(), accounts.getCustomerCIF() );
        if (originalAcct == null)
            throw new GeneralException( Errors.CANNOT_EDIT_OBJECT, "Account DOES NOT Exist" );
        Utils.copyAccount( accounts, originalAcct );
        if (accounts.getNin() != null)
            originalAcct.setNin( accounts.getNin() );
        if (accounts.getCustomerCIF() != null)
            originalAcct.setCustomerCIF( accounts.getCustomerCIF() );

        return ResponseEntity.ok( customerAccountsRepo.save( originalAcct ) );
    }

    @Override
    public ResponseEntity<?> delete(CustomerAccounts object, Principal principal) throws GeneralException {
        return null;
    }
}
