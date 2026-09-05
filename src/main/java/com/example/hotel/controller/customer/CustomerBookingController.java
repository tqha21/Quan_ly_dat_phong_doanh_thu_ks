package com.example.hotel.controller.customer;

import com.example.hotel.dto.BookingRequest;
import com.example.hotel.dto.RoomDTO;
import com.example.hotel.service.BookingService;
import com.example.hotel.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/customer/bookings")
@RequiredArgsConstructor
public class CustomerBookingController {

    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping("/create")
    public String showBookingForm(@RequestParam String roomId,
                                  @RequestParam String checkIn,
                                  @RequestParam String checkOut,
                                  Model model) {
        RoomDTO room = roomService.getRoomById(roomId);
        
        BookingRequest request = new BookingRequest();
        request.setRoomId(roomId);
        request.setCheckInDate(java.time.LocalDate.parse(checkIn));
        request.setCheckOutDate(java.time.LocalDate.parse(checkOut));

        model.addAttribute("room", room);
        model.addAttribute("bookingRequest", request);
        return "customer/booking-form";
    }

    @PostMapping("/create")
    public String submitBooking(@Valid @ModelAttribute("bookingRequest") BookingRequest request,
                                BindingResult bindingResult,
                                Principal principal,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("room", roomService.getRoomById(request.getRoomId()));
            return "customer/booking-form";
        }

        try {
            bookingService.createBooking(request, principal.getName());
            return "redirect:/customer/bookings/history?success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("room", roomService.getRoomById(request.getRoomId()));
            return "customer/booking-form";
        }
    }

    @GetMapping("/history")
    public String bookingHistory(Principal principal, Model model) {
        model.addAttribute("bookings", bookingService.getMyBookings(principal.getName()));
        return "customer/booking-history";
    }

    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable String id, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(id, principal.getName());
            return "redirect:/customer/bookings/history?cancelled";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/customer/bookings/history";
        }
    }
}
