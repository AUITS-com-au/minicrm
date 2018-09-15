package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepo extends JpaRepository<Subcategory, Integer> {
}
