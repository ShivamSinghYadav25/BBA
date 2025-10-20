package com.busticket.Booking.Application.controller;

import com.busticket.Booking.Application.model.User;
import com.busticket.Booking.Application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // ===== Registration =====
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }

        try {
            userService.createUser(user); // password will be BCrypt encoded in service
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register";
        }
    }

    // ===== Login page =====
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Spring Security handles POST /login automatically
    }

    // ===== Dashboard =====
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard"; // Thymeleaf page after successful login
    }
}
