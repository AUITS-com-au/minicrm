package com.sh.crm.jpa.repos.custom;

import com.sh.crm.jpa.entities.CustomerAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerAccountsRepoCustom {
    public CustomerAccounts checkExistingAccount(Long ID, String nin, String cif);


}
