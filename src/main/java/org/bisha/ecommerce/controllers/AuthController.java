package org.bisha.ecommerce.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.bisha.ecommerce.dtos.UserLoginDto;
import org.bisha.ecommerce.dtos.UserRegisterDto;
import org.bisha.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDto userLoginDto, BindingResult bindingResult, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            UserLoginDto responseDto = userService.login(userLoginDto);

            Cookie cookie = new Cookie("username", responseDto.getUsername());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return "redirect:/home";
        } catch (Exception e) {
            bindingResult.reject("login", "Invalid username or password");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegisterDto userRegisterDto, BindingResult bindingResult, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            UserRegisterDto responseDto = userService.register(userRegisterDto);

            Cookie registerCookie = new Cookie("username", responseDto.getUsername());
            registerCookie.setHttpOnly(true);
            registerCookie.setPath("/");
            registerCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(registerCookie);

            return "redirect:/login";
        } catch (Exception e) {
            bindingResult.reject("register", "Registration failed");
            return "auth/register";
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