package org.bisha.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Order status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @PositiveOrZero(message = "Total price must be zero or positive")
    @Column(name = "total_price")
    private double totalPrice;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "Telephone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid telephone number")
    @Column(name = "telephone_number")
    private String telephoneNumber;

    @NotNull(message = "Order date cannot be null")
    @Column(name = "created_at")
    private LocalDateTime orderedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @NotNull(message = "Order items cannot be null")
    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
}