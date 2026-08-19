package com.example.hotel.controller;

import com.example.hotel.dto.RegisterRequest;
import com.example.hotel.exception.UserAlreadyExistsException;
import com.example.hotel.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                               BindingResult bindingResult, Model model) {
        // Validation từ backend (Jakarta Validation)
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            authService.registerCustomer(registerRequest);
            // Đăng ký thành công, redirect sang trang login kèm thông báo
            return "redirect:/login?registered=true";
        } catch (UserAlreadyExistsException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
    }
}
