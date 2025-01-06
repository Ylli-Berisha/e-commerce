package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ImageDto;
import org.bisha.ecommerce.models.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper extends SimpleMapper<Image, ImageDto> {
}
