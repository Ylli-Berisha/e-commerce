package org.bisha.ecommerce.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Subcategory;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private String name;

    private String description;

    private List<Subcategory> subcategories;
}
