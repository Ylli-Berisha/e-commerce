package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.WishlistDto;
import org.bisha.ecommerce.models.Wishlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper extends SimpleMapper<Wishlist, WishlistDto> {
}
