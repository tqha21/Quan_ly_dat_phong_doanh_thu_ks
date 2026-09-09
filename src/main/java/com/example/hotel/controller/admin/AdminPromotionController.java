package com.example.hotel.controller.admin;

import com.example.hotel.entity.Promotion;
import com.example.hotel.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/promotions")
@RequiredArgsConstructor
public class AdminPromotionController {

    private final PromotionRepository promotionRepository;

    @GetMapping
    public String listPromotions(Model model) {
        model.addAttribute("promotions", promotionRepository.findAll());
        model.addAttribute("promotion", new Promotion());
        return "admin/promotions/index";
    }

    @PostMapping("/save")
    public String savePromotion(@ModelAttribute Promotion promotion) {
        if (promotion.getId() != null && promotion.getId().isEmpty()) {
            promotion.setId(null);
        }
        promotionRepository.save(promotion);
        return "redirect:/admin/promotions?success";
    }

    @PostMapping("/delete")
    public String deletePromotion(@RequestParam("id") String id) {
        promotionRepository.deleteById(id);
        return "redirect:/admin/promotions?deleted";
    }
}
