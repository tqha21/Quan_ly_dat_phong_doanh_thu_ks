package com.example.hotel.controller.admin;

import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.PaymentRepository;
import com.example.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String dashboard(Model model) {
        long totalRooms = roomRepository.count();
        long pendingBookings = bookingRepository.countByStatus("PENDING");
        long checkedInBookings = bookingRepository.countByStatus("CHECKED_IN");
        
        // Revenue this month
        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);
        
        BigDecimal monthlyRevenue = paymentRepository.calculateRevenueBetweenDates(startOfMonth, endOfMonth);
        if (monthlyRevenue == null) {
            monthlyRevenue = BigDecimal.ZERO;
        }

        model.addAttribute("totalRooms", totalRooms);
        model.addAttribute("pendingBookings", pendingBookings);
        model.addAttribute("checkedInBookings", checkedInBookings);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        
        return "admin/dashboard";
    }
}
