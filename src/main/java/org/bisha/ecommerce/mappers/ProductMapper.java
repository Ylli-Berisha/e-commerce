package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.models.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends SimpleMapper<Product, ProductDto> {
}