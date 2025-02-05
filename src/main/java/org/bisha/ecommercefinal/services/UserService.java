package org.bisha.ecommercefinal.services;


import org.bisha.ecommercefinal.dtos.*;
import org.bisha.ecommercefinal.enums.Role;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto userDto);

    UserDto deleteUser(Long id);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByRole(Role role);

    List<UserDto> getUsersByActivity(boolean active);

    UserDto updateUser(UserDto userDto, Long userId);

    UserDto changePassword(Long userId, String newPassword);

    UserDto activateUser(Long userId);

    UserDto deactivateUser(Long userId);

    UserDto resetPassword(String email, String newPassword);

    List<OrderItemDto> getBoughtProductsByUserId(Long userId);

    ProductDto buyProduct(Long userId, Long productId, int quantity);

    List<ProductDto> buyProducts(Long userId, HashMap<Long, Integer> productIdsAndQuantities);

    ProductDto returnProduct(Long userId, Long productId, int quantity);

    void validateUser(Long userId);

    UserDto login(UserLoginDto userLoginDto);

    UserDto register(UserRegisterDto userRegisterDto);
}
