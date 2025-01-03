package org.bisha.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.enums.OrderStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "delivered_at")
    private String deliveredAt;

    @Column(name = "cancelled_at")
    private String cancelledAt;
}