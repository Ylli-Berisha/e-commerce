package org.bisha.ecommerce.services;


import org.bisha.ecommerce.dtos.OrderItemDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.UserDto;
import org.bisha.ecommerce.enums.Role;
import org.bisha.ecommerce.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto userDto);

    UserDto deleteUser(Long id);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByRole(Role role);

    List<UserDto> getUsersByActivity(boolean active);

    UserDto updateUser(UserDto userDto);

    UserDto changePassword(Long userId, String newPassword);

    UserDto activateUser(Long userId);

    UserDto deactivateUser(Long userId);

    UserDto resetPassword(String email, String newPassword);

    List<OrderItemDto> getBoughtProductsByUserId(Long userId);
}
