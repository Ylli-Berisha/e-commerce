package org.bisha.ecommerce.repositories;

import org.bisha.ecommerce.models.User;
import org.bisha.ecommerce.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);

    List<Wishlist> findByCreatedAtAfter(LocalDate date);

    List<Wishlist> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByUser(User user);

}
