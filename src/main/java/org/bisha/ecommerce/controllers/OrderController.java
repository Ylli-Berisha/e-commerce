package org.bisha.ecommerce.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bisha.ecommerce.dtos.OrderDto;
import org.bisha.ecommerce.enums.OrderStatus;
import org.bisha.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody @Valid OrderDto orderDto) {
        if (orderDto.getOrderItemIds() == null) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable @NotNull @Min(0) Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/all")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable @NotNull @Min(0) Long id, @RequestBody @Valid OrderDto orderDto) {
        if (orderDto.getOrderItemIds() == null) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        return orderService.updateOrder(id, orderDto);
    }

    @DeleteMapping("/{id}/cancel")
    public OrderDto cancelOrderById(@PathVariable @NotNull @Min(0) Long id) {
        return orderService.cancelOrderById(id);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable @NotNull @Min(0) Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/status/{status}")
    public List<OrderDto> getOrdersByStatus(@PathVariable @NotNull OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    @PutMapping("/{id}/status")
    public OrderDto updateOrderStatus(@PathVariable @NotNull @Min(0) Long id, @RequestBody @NotNull OrderStatus status) {
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/date-range")
    public List<OrderDto> getOrdersByDateRange(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return orderService.getOrdersByDateRange(startDate, endDate);
    }

    @GetMapping("/price-range")
    public List<OrderDto> getOrdersByTotalPriceRange(
            @RequestParam @NotNull @Min(0) double minPrice,
            @RequestParam @NotNull @Min(0) double maxPrice) {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price must be less than max price");
        }
        return orderService.getOrdersByTotalPriceRange(minPrice, maxPrice);
    }

    @PutMapping("/{id}/update-address-phone")
    public OrderDto updateOrderAddressAndPhone(
            @PathVariable @NotNull @Min(0) Long id,
            @RequestParam @NotNull String address,
            @RequestParam @NotNull String phone) {
        return orderService.updateOrderAddressAndPhone(id, address, phone);
    }

    @GetMapping("/get-dto")
    public OrderDto getDto() {
        return new OrderDto();
    }
}