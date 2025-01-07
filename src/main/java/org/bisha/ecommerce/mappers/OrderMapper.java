package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.OrderDto;
import org.bisha.ecommerce.models.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends SimpleMapper<Order, OrderDto> {
}
