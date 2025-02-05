package org.bisha.ecommercefinal.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.bisha.ecommercefinal.dtos.OrderItemDto;
import org.bisha.ecommercefinal.dtos.UserDto;
import org.bisha.ecommercefinal.enums.Role;
import org.bisha.ecommercefinal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<UserDto> getUserById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable @Min(1) Long id) {
        UserDto deletedUser = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUser);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable @NotBlank String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable @Email String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @GetMapping("/activity/{active}")
    public ResponseEntity<List<UserDto>> getUsersByActivity(@PathVariable boolean active) {
        return ResponseEntity.ok(userService.getUsersByActivity(active));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, @PathVariable @Min(1) Long userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/activate/{userId}")
    public ResponseEntity<UserDto> activateUser(@PathVariable @Min(1) Long userId) {
        UserDto activatedUser = userService.activateUser(userId);
        return ResponseEntity.ok(activatedUser);
    }

    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<UserDto> deactivateUser(@PathVariable @Min(1) Long userId) {
        UserDto deactivatedUser = userService.deactivateUser(userId);
        return ResponseEntity.ok(deactivatedUser);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<UserDto> resetPassword(@RequestParam @Email String email, @RequestBody @NotBlank String newPassword) {
        UserDto userWithResetPassword = userService.resetPassword(email, newPassword);
        return ResponseEntity.ok(userWithResetPassword);
    }

    @GetMapping("/bought-products/{userId}")
    public ResponseEntity<List<OrderItemDto>> getBoughtProductsByUserId(@PathVariable @Min(1) Long userId) {
        return ResponseEntity.ok(userService.getBoughtProductsByUserId(userId));
    }

    @PostMapping("/buy-product/{userId}/{productId}")
    public ResponseEntity<Void> buyProduct(@PathVariable @Min(1) Long userId, @PathVariable @Min(1) Long productId, @RequestParam @Min(1) int quantity) {
        userService.buyProduct(userId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/return-product/{userId}/{productId}")
    public ResponseEntity<Void> returnProduct(@PathVariable @Min(1) Long userId, @PathVariable @Min(1) Long productId, @RequestParam @Min(1) int quantity) {
        userService.returnProduct(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-model")
    public ResponseEntity<UserDto> getDto() {
        return ResponseEntity.ok(new UserDto());
    }

}
