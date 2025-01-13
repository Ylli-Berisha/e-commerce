package org.bisha.ecommerce.repositories;

import org.bisha.ecommerce.dtos.OrderDto;
import org.bisha.ecommerce.models.OrderItem;
import org.bisha.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByProductId(Long productId);

    List<OrderItem> findByPriceBetween(double minPrice, double maxPrice);

    List<OrderItem> findByUser(User user);
}
