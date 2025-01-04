package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.models.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);

    List<ProductDto> toDtos(List<Product> products);
}