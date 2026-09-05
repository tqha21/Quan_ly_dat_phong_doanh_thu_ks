package com.example.hotel.controller.admin;

import com.example.hotel.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin/revenue")
@RequiredArgsConstructor
public class AdminRevenueController {
    private final PaymentRepository paymentRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String revenue(Model model){
        LocalDate now=LocalDate.now();
        var value=paymentRepository.calculateRevenueBetweenDates(
                now.withDayOfMonth(1).atStartOfDay(),
                now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX));
        model.addAttribute("revenue", value==null?0:value);
        return "admin/revenue/index";
    }
}
