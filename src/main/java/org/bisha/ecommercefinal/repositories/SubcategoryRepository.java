package org.bisha.ecommercefinal.repositories;

import org.bisha.ecommercefinal.models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    Optional<Subcategory> findByName(String name);
}
