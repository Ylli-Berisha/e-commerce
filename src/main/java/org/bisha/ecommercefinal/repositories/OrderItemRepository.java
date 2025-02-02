package org.bisha.ecommercefinal.repositories;

import org.bisha.ecommercefinal.models.OrderItem;
import org.bisha.ecommercefinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByProductId(Long productId);

    List<OrderItem> findByPriceBetween(double minPrice, double maxPrice);

    List<OrderItem> findByUser(User user);
}
