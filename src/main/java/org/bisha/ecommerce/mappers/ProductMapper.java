package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
}