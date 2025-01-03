package org.bisha.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @OneToMany
    private List<Image> imageURLs;

    @OneToMany
    private List<Review> reviews;

    @Column(name = "stock")
    private int stock;

    @ManyToOne
    private Category category;

    @Column(name = "brand")
    private String brand;

    @Column(name = "rating")
    private double rating;

    @ManyToOne
    private Subcategory subcategory;

    @Column(name = "is_available")
    private boolean available;

    @Column(name = "created_at")
    private LocalDate createdAt;
}