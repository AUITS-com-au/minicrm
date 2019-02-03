package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.CustomerAccounts;
import com.sh.crm.jpa.repos.custom.CustomerAccountsRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAccountsRepo extends JpaRepository<CustomerAccounts, Long>, CustomerAccountsRepoCustom {
    // @Query("select distinct a from CustomerAccounts a where a.id=?1 or a.nin=?2 or a.customerCIF=?3")
    // CustomerAccounts findIDOrAccountOrNinOrCIF(Long ID, String nin, String cif);
    List<CustomerAccounts> findByNin(String nin);

    List<CustomerAccounts> findByCustomerCIF(String customerCIF);

}
