package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.CategoryDto;

import java.util.List;

public interface SubcategoryService {
    List<CategoryDto> getAllSubcategoriesByCategories();

    CategoryDto getSubcategoryById(Long id);

    CategoryDto saveSubcategory(CategoryDto category);

    CategoryDto deleteSubcategory(Long id);

    CategoryDto updateSubcategoryById(CategoryDto category, Long id);

    CategoryDto getSubcategoryByName(String name);
}
