package org.bisha.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "URL is mandatory")
    @Column(name = "url")
    private String url;

    @NotNull(message = "Product cannot be null")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}