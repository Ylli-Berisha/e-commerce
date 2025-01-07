package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.OrderItemDto;
import org.bisha.ecommerce.models.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends SimpleMapper<OrderItem, OrderItemDto> {
}
