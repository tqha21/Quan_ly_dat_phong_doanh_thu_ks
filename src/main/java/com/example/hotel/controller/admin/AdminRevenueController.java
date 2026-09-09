package com.example.hotel.controller.admin;

import com.example.hotel.entity.Payment;
import com.example.hotel.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/revenue")
@RequiredArgsConstructor
public class AdminRevenueController {

    private final PaymentRepository paymentRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String revenuePage(Model model) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);
        
        BigDecimal monthlyRevenue = paymentRepository.calculateRevenueBetweenDates(startOfMonth, endOfMonth);
        model.addAttribute("monthlyRevenue", monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);
        
        List<Payment> allPayments = paymentRepository.findAll().stream()
                .filter(p -> "PAID".equals(p.getPaymentStatus()))
                .collect(Collectors.toList());
        model.addAttribute("payments", allPayments);
        
        return "admin/revenue/index"; // Might not be implemented yet
    }

    @GetMapping("/export-csv")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportRevenueCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"revenue_report.csv\"");
        
        // Fix for excel utf-8 reading
        response.getOutputStream().write(0xEF);
        response.getOutputStream().write(0xBB);
        response.getOutputStream().write(0xBF);

        PrintWriter writer = response.getWriter();
        writer.println("Mã Thanh Toán,Mã Đặt Phòng,Số Tiền,Phương Thức,Ngày Thanh Toán");

        List<Payment> paidPayments = paymentRepository.findAll().stream()
                .filter(p -> "PAID".equals(p.getPaymentStatus()))
                .collect(Collectors.toList());

        for (Payment p : paidPayments) {
            String bookingCode = p.getBooking() != null ? p.getBooking().getBookingCode() : "";
            writer.printf("%s,%s,%s,%s,%s\n",
                    p.getId(),
                    bookingCode,
                    p.getAmount(),
                    p.getPaymentMethod(),
                    p.getPaidAt());
        }
        writer.flush();
    }
}
