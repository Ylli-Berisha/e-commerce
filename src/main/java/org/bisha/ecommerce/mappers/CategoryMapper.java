package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
}
