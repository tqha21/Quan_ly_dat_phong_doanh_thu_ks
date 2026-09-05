package com.example.hotel.controller.admin;

import com.example.hotel.entity.User;
import com.example.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String index(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "admin/users/index";
    }

    @PostMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public String toggle(@PathVariable String id, RedirectAttributes ra){
        User u=userRepository.findById(id).orElseThrow();
        u.setStatus("ACTIVE".equals(u.getStatus()) ? "LOCKED" : "ACTIVE");
        userRepository.save(u);
        ra.addFlashAttribute("successMessage","Cập nhật trạng thái tài khoản thành công");
        return "redirect:/admin/users";
    }
}
