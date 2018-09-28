package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.CustomerAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountsRepo extends JpaRepository<CustomerAccounts, Long> {
}
