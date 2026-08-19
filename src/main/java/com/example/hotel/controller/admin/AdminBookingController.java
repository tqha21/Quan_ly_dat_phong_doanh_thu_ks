package com.example.hotel.controller.admin;

import com.example.hotel.dto.BookingDTO;
import com.example.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {

    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public String listBookings(Model model) {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/bookings/index";
    }

    @PostMapping("/{id}/update-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public String updateBookingStatus(@PathVariable("id") String id, 
                                      @RequestParam("status") String status,
                                      RedirectAttributes redirectAttributes) {
        try {
            bookingService.updateBookingStatus(id, status);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/bookings";
    }
}
