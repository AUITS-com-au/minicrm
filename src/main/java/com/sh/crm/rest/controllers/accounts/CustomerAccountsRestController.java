package com.sh.crm.rest.controllers.accounts;

import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.SearchTicketsCustomerContainer;
import com.sh.crm.general.utils.Utils;
import com.sh.crm.jpa.entities.CustomerAccounts;
import com.sh.crm.rest.general.BasicController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(value = "accounts", produces = MediaType.APPLICATION_JSON_VALUE)

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

    @PostMapping("search")
    @PutMapping("search")
    public Collection<CustomerAccounts> searchCustomer(@RequestBody SearchTicketsCustomerContainer container) {
        log.debug( "searching for customer with criteria {}", container );
        return customerAccountsRepo.findDistinctByCustomerCIFOrCustomerNameARLikeOrCustomerNameEnLikeOrEmailOrNinLikeOrMobileLike( container.getCustomerBasic(), container.getCustomerName(), container.getCustomerName(), container.getCustomerEmail(), container.getNan(), container.getCustomerMobile() );
    }

}
