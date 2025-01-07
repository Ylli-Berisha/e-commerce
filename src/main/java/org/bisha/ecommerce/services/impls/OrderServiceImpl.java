package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.OrderDto;
import org.bisha.ecommerce.enums.OrderStatus;
import org.bisha.ecommerce.mappers.OrderMapper;
import org.bisha.ecommerce.models.Order;
import org.bisha.ecommerce.repositories.OrderRepository;
import org.bisha.ecommerce.repositories.UserRepository;
import org.bisha.ecommerce.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        validateOrderDto(orderDto);
        Order order = orderMapper.toEntity(orderDto);
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        validateId(id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDtos(orders);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        validateId(id);
        validateOrderDto(orderDto);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderItems(orderMapper.toEntity(orderDto).getOrderItems());
        order.setAddress(orderDto.getAddress());
        order.setOrderedAt(orderDto.getOrderDate());
        order.setTotalPrice(orderDto.getTotalAmount());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }


    @Override
    public OrderDto cancelOrderById(Long orderId) {
        validateId(orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        validateOrderStatusForCancellation(order);
        order.setStatus(OrderStatus.CANCELLED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        validateId(userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Order> orders = orderRepository.findByUser(user);
        return orderMapper.toDtos(orders);
    }

    @Override
    public List<OrderDto> getOrdersByStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Invalid order status");
        }
        List<Order> orders = orderRepository.findByStatus(status);
        return orderMapper.toDtos(orders);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) {
        validateId(orderId);
        if (status == null) {
            throw new IllegalArgumentException("Invalid order status");
        }
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
    }

    private void validateOrderDto(OrderDto orderDto) {
        if (orderDto == null || orderDto.getOrderItems().isEmpty() ||
                orderDto.getUser() == null || orderDto.getAddress() == null ||
                orderDto.getOrderDate() == null || orderDto.getTotalAmount() == 0) {
            throw new IllegalArgumentException("Invalid order details");
        }
    }

    private void validateOrderStatusForCancellation(Order order) {
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Order already cancelled");
        }
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.RETURNED || order.getStatus() == OrderStatus.SHIPPED) {
            throw new IllegalArgumentException("Cannot cancel order in current status");
        }
    }
}