package org.bisha.ecommerce.repositories;

import org.bisha.ecommerce.models.Category;
import org.bisha.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByNameContaining(String name);

    Optional<List<Product>> findByPriceBetween(double minPrice, double maxPrice);

    Optional<List<Product>> findByCategory(Category category);

    Optional<List<Product>> findByBrand(String brand);

    Optional<List<Product>> findByRatingGreaterThanEqual(double rating);

    Optional<List<Product>> findByAvailable(boolean isAvailable);

    Optional<List<Product>> findByCreatedAtAfter(LocalDate date);

    Optional<List<Product>> findByStockGreaterThan(int stock);

    Optional<List<Product>> findBySubcategoryId(Long subcategoryId);
}