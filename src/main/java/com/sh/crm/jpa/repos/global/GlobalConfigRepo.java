package com.sh.crm.jpa.repos.global;

import com.sh.crm.jpa.entities.Globalconfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GlobalConfigRepo extends JpaRepository<Globalconfiguration, String> {

    List<Globalconfiguration> findByType(String type);

    Globalconfiguration findByProperty(String property);

    @Query("select g.value from Globalconfiguration g where g.property=?1 ")
    String findValueByProperty(String property);
}
