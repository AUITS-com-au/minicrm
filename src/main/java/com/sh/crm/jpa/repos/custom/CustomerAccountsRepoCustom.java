package com.sh.crm.jpa.repos.custom;

import com.sh.crm.jpa.entities.CustomerAccounts;

public interface CustomerAccountsRepoCustom {
    public CustomerAccounts checkExistingAccount(Long ID, String nin, String cif);


}
