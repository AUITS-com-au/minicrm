package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.CustomerAccounts;
import com.sh.crm.jpa.repos.custom.CustomerAccountsRepoCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerAccountsRepoImpl implements CustomerAccountsRepoCustom {
    private static final Logger logger = LoggerFactory.getLogger( CustomerAccountsRepoImpl.class );
    @Autowired
    private CustomerAccountsRepo customerAccountsRepo;

    @Override
    public CustomerAccounts checkExistingAccount(Long ID, String nin, String cif) {
        CustomerAccounts accounts = null;
        if (ID != null) {
            //check by ID
            accounts = customerAccountsRepo.findById( ID ).orElse( null );
            if (logger.isDebugEnabled()) {
                logger.debug( "customer account {} using ID for account ID {}", (accounts == null) ? "NOT FOUND" : "Found", ID );
            }
        }
        if (accounts == null) {
            if (logger.isDebugEnabled()) {
                logger.debug( "checking account using NIN or CIF" );
            }
            List<CustomerAccounts> accountsList = null;
            if (nin != null) {
                accountsList = customerAccountsRepo.findByNin( nin );
                if (logger.isDebugEnabled())
                    logger.debug( "checking account using NIN give result of {}", accountsList == null ? "NULL" : accountsList.size() );
            }
            if ((accountsList == null || accountsList.isEmpty()) && cif != null) {
                accountsList = customerAccountsRepo.findByCustomerCIF( cif );

                if (logger.isDebugEnabled())
                    logger.debug( "checking account using CIF give result of {}", accountsList == null ? "NULL" : accountsList.size() );
            }
            if (accountsList != null && !accountsList.isEmpty()) {
                if (logger.isDebugEnabled())
                    logger.debug( "final account search results are {} returning the first item in the list {}", accountsList.size(), accountsList.get( 0 ) );
                accounts = accountsList.get( 0 );
            }
        }
        return accounts;
    }
}
