package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Maincategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainCategoryRepo extends JpaRepository<Maincategory, Integer> {

    List<Maincategory> findByEnabledTrue();

}
