package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ShoppingCartItemDto;
import org.bisha.ecommerce.models.ShoppingCartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingCartItemMapper extends SimpleMapper<ShoppingCartItem, ShoppingCartItemDto> {
}
