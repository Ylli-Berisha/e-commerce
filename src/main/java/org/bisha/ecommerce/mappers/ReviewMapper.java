package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ReviewDto;
import org.bisha.ecommerce.models.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewDto reviewDto);
    ReviewDto toDto(Review review);
}
