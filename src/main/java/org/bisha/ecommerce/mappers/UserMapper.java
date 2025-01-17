package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.UserDto;
import org.bisha.ecommerce.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends SimpleMapper<User, UserDto> {
    //User updateEntityFromDto(UserDto userDto, User user);
}