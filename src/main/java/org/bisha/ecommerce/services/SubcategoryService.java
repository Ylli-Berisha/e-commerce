package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;

import java.util.List;

public interface SubcategoryService {
    List<SubcategoryDto> getSubcategoriesByCategoryName(String categoryName);

    CategoryDto getSubcategoryById(Long id);

    CategoryDto saveSubcategory(CategoryDto category);

    CategoryDto deleteSubcategory(Long id);

    CategoryDto updateSubcategoryById(CategoryDto category, Long id);

    CategoryDto getSubcategoryByName(String name);
}
