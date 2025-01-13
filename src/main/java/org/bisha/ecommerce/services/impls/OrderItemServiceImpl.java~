package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.OrderItemDto;
import org.bisha.ecommerce.mappers.OrderItemMapper;
import org.bisha.ecommerce.models.OrderItem;
import org.bisha.ecommerce.repositories.OrderItemRepository;
import org.bisha.ecommerce.repositories.OrderRepository;
import org.bisha.ecommerce.repositories.ProductRepository;
import org.bisha.ecommerce.repositories.UserRepository;
import org.bisha.ecommerce.services.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
        if (orderItemRepository.existsById(orderItem.getId())) {
            throw new IllegalArgumentException("Order item already exists");
        }
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDto(savedOrderItem);
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));
        existingOrderItem.setProduct(productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found")));
        existingOrderItem.setUser(userRepository.findById(orderItemDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        existingOrderItem.setOrder(orderRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found")));
        existingOrderItem.setPrice(orderItemDto.getPrice());

        OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
        return orderItemMapper.toDto(updatedOrderItem);
    }

    @Override
    public OrderItemDto deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));
        orderItemRepository.deleteById(id);
        return orderItemMapper.toDto(orderItem);
    }
}