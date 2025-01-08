package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ShoppingCartDto;
import org.bisha.ecommerce.models.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper extends SimpleMapper<ShoppingCart, ShoppingCartDto> {
}
