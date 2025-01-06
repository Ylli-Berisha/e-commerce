package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ReviewDto;
import org.bisha.ecommerce.models.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends SimpleMapper<Review, ReviewDto> {
}
