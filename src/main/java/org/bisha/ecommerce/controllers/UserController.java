package org.bisha.ecommerce.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.bisha.ecommerce.dtos.OrderItemDto;
import org.bisha.ecommerce.dtos.UserDto;
import org.bisha.ecommerce.enums.Role;
import org.bisha.ecommerce.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable @Min(1) Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto saveUser(@RequestBody @Valid UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable @Min(1) Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/username/{username}")
    public UserDto getUserByUsername(@PathVariable @NotBlank String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable @Email String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/role/{role}")
    public List<UserDto> getUsersByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/activity/{active}")
    public List<UserDto> getUsersByActivity(@PathVariable boolean active) {
        return userService.getUsersByActivity(active);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody @Valid UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PutMapping("/change-password/{userId}")
    public UserDto changePassword(@PathVariable @Min(1) Long userId, @RequestParam @NotBlank String newPassword) {
        return userService.changePassword(userId, newPassword);
    }

    @PutMapping("/activate/{userId}")
    public UserDto activateUser(@PathVariable @Min(1) Long userId) {
        return userService.activateUser(userId);
    }

    @PutMapping("/deactivate/{userId}")
    public UserDto deactivateUser(@PathVariable @Min(1) Long userId) {
        return userService.deactivateUser(userId);
    }

    @PutMapping("/reset-password")
    public UserDto resetPassword(@RequestParam @Email String email, @RequestParam @NotBlank String newPassword) {
        return userService.resetPassword(email, newPassword);
    }

    @GetMapping("/bought-products/{userId}")
    public List<OrderItemDto> getBoughtProductsByUserId(@PathVariable @Min(1) Long userId) {
        return userService.getBoughtProductsByUserId(userId);
    }

    @PostMapping("/buy-product/{userId}/{productId}")
    public void buyProduct(@PathVariable @Min(1) Long userId, @PathVariable @Min(1) Long productId
            , @RequestParam @Min(1) int quantity) {
        userService.buyProduct(userId, productId, quantity);
    }

    @PostMapping("/return-product/{userId}/{productId}")
    public void returnProduct(@PathVariable @Min(1) Long userId, @PathVariable @Min(1) Long productId
            , @RequestParam @Min(1) int quantity) {
        userService.returnProduct(userId, productId, quantity);
        //method not finished
    }

    @GetMapping("/get-model")
    public UserDto getDto() {
        return new UserDto();
    }

}