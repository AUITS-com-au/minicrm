package com.sh.crm.jpa.repos.users;

import com.sh.crm.jpa.entities.Userpreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferencesRepo extends JpaRepository<Userpreferences, Long> {

    Userpreferences findByUserID(String userid);
}
