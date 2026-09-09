package com.example.hotel.controller.admin;

import com.example.hotel.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final ReviewRepository reviewRepository;

    @GetMapping
    public String listReviews(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
        return "admin/reviews/index";
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam("id") String id) {
        reviewRepository.deleteById(id);
        return "redirect:/admin/reviews?deleted";
    }
}
