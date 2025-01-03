package org.bisha.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Category;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubcategoryDto {
    private String name;

    private String description;

    private Category parentCategory;
}
