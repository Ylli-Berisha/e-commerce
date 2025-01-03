package org.bisha.ecommerce.repositories;

import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.Review;
import org.bisha.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByProduct(Product product);

    Optional<List<Review>> findByUser(User user);

    Optional<List<Review>> findByCreatedAtAfter(LocalDateTime createdAt);

    Optional<List<Review>> findByCreatedAtBefore(LocalDateTime createdAt);
}