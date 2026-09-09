package com.example.hotel.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
public class AdminMiscController {

    @GetMapping("/settings")
    public String comingSoon() {
        return "admin/coming-soon";
    }
}
