package com.example.hotel.controller.customer;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.Review;
import com.example.hotel.entity.User;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.ReviewRepository;
import com.example.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/customer/reviews")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @GetMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String showReviewForm(@RequestParam("bookingId") String bookingId, Model model, Principal principal) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn đặt phòng"));

        // Xác thực người dùng
        User customer = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));

        if (!booking.getCustomer().getId().equals(customer.getId())) {
            return "redirect:/403";
        }

        if (!"CHECKED_OUT".equals(booking.getStatus())) {
            model.addAttribute("errorMessage", "Bạn chỉ có thể đánh giá phòng sau khi đã trả phòng.");
            return "customer/error-page"; // Or redirect back
        }

        model.addAttribute("booking", booking);
        return "customer/review-form";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String submitReview(@RequestParam("bookingId") String bookingId,
                               @RequestParam("rating") int rating,
                               @RequestParam("comment") String comment,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn đặt phòng"));
                
        User customer = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
                
        if (!booking.getCustomer().getId().equals(customer.getId())) {
            return "redirect:/403";
        }

        if (!"CHECKED_OUT".equals(booking.getStatus())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Chỉ có thể đánh giá sau khi trả phòng.");
            return "redirect:/customer/reviews/create?bookingId=" + bookingId;
        }

        Review review = new Review();
        review.setId("RV" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        review.setBooking(booking);
        review.setCustomer(customer);
        review.setRoom(booking.getBookingDetails().get(0).getRoom()); // Assuming single room per booking
        review.setRating(rating);
        review.setComment(comment);
        review.setStatus("APPROVED");
        
        reviewRepository.save(review);
        
        redirectAttributes.addFlashAttribute("successMessage", "Cảm ơn bạn đã gửi đánh giá!");
        return "redirect:/customer/bookings"; // Assumes this route exists for viewing customer bookings
    }
}
