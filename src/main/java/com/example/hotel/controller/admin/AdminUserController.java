package com.example.hotel.controller.admin;

import com.example.hotel.entity.User;
import com.example.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "Người dùng");
        model.addAttribute("pageSub", "Quản lý toàn bộ tài khoản hệ thống (Admin, Staff, Customer).");
        return "admin/users/index";
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public String listCustomers(Model model) {
        List<User> customers = userRepository.findAll().stream()
                .filter(u -> "ROLE_CUSTOMER".equals(u.getRole().getCode()))
                .collect(Collectors.toList());
        model.addAttribute("users", customers);
        model.addAttribute("pageTitle", "Khách hàng");
        model.addAttribute("pageSub", "Danh sách hồ sơ khách hàng đã đăng ký tài khoản.");
        return "admin/users/index";
    }
}
