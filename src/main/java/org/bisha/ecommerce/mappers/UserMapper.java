package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.UserDto;
import org.bisha.ecommerce.models.User;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);

    List<UserDto> toDtos(List<User> users);

    User updateEntityFromDto(UserDto userDto, User user);
}