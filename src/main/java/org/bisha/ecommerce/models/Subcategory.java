package org.bisha.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "subcategories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    @Column(name = "name")
    private String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Parent category cannot be null")
    @ManyToOne
    private Category parentCategory;
}