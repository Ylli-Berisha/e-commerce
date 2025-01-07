package org.bisha.ecommerce.repositories;

import org.bisha.ecommerce.models.Category;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByCategory(Category category);

    List<Product> findByBrand(String brand);

    List<Product> findByRatingGreaterThanEqual(double rating);

    List<Product> findByAvailable(boolean isAvailable);

    List<Product> findByCreatedAtAfter(LocalDate date);

    List<Product> findByStockGreaterThan(int stock);

    List<Product> findBySubcategory(Subcategory subcategory);
}