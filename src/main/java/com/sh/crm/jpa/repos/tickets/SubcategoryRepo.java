package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepo extends JpaRepository<Subcategory, Integer> {
    List<Subcategory> findByEnabledTrue();

    List<Subcategory> findByMainCategory_IdAndEnabledTrue(Integer mainCat);
}
