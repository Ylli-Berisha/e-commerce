package org.bisha.ecommerce.repositories;

import org.bisha.ecommerce.enums.OrderStatus;
import org.bisha.ecommerce.models.Order;
import org.bisha.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findByUser(User user);

    Optional<List<Order>> findByOrderedAtAfter(LocalDateTime date);

    Optional<List<Order>> findByStatus(OrderStatus status);

    Optional<List<Order>> findByTotalPriceGreaterThan(double amount);

    Optional<List<Order>> findByTotalPriceLessThan(double amount);

    Optional<List<Order>> findByTotalPriceBetween(double min, double max);
}