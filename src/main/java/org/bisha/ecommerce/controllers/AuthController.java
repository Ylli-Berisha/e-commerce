package org.bisha.ecommerce.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.bisha.ecommerce.dtos.UserLoginDto;
import org.bisha.ecommerce.dtos.UserRegisterDto;
import org.bisha.ecommerce.exceptions.ResourceNotFoundException;
import org.bisha.ecommerce.exceptions.WrongPasswordException;
import org.bisha.ecommerce.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "signin";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDto userLoginDto, BindingResult bindingResult, HttpServletResponse response, Model model) {
        Logger logger = LoggerFactory.getLogger(AuthController.class);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userLoginDto", userLoginDto);
            return "signin";
        }

        try {
            UserLoginDto responseDto = userService.login(userLoginDto);

            Cookie cookie = new Cookie("username", responseDto.getUsername());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return "redirect:/home";
        } catch (ResourceNotFoundException e) {
            logger.error("User not found", e);
            bindingResult.rejectValue("username", "error.username", "User not found");
        } catch (WrongPasswordException e) {
            logger.error("Wrong password", e);
            bindingResult.rejectValue("password", "error.password", "Wrong password");
        } catch (Exception e) {
            logger.error("Login failed", e);
            bindingResult.reject("login", "Login failed due to an unexpected error");
        }

        model.addAttribute("userLoginDto", userLoginDto);
        return "signin";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegisterDto userRegisterDto, BindingResult bindingResult, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRegisterDto", userRegisterDto);
            return "register";
        }

        try {
            UserRegisterDto responseDto = userService.register(userRegisterDto);

            Cookie registerCookie = new Cookie("username", responseDto.getUsername());
            registerCookie.setHttpOnly(true);
            registerCookie.setPath("/");
            registerCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(registerCookie);

            return "redirect:/";
        }
        catch (Exception e) {
            bindingResult.reject("register", "Registration failed: " + e.getMessage());
            model.addAttribute("userRegisterDto", userRegisterDto);
            return "register";
        }
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        Cookie cookie = new Cookie("username", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }
}