package com.example.hotel.controller.admin;

import com.example.hotel.entity.HotelService;
import com.example.hotel.repository.HotelServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin/services")
@RequiredArgsConstructor
public class HotelServiceController {
    private final HotelServiceRepository hotelServiceRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("services", hotelServiceRepository.findAll());
        return "admin/services/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("service", new HotelService());
        return "admin/services/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("service", hotelServiceRepository.findById(id).orElseThrow());
        return "admin/services/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("service") HotelService service) {
        if (service.getId() == null || service.getId().isBlank()) {
            service.setId("SV" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (service.getStatus() == null || service.getStatus().isBlank()) {
            service.setStatus("ACTIVE");
        }
        hotelServiceRepository.save(service);
        return "redirect:/admin/services?success";
    }

    @PostMapping("/toggle/{id}")
    public String toggle(@PathVariable String id) {
        HotelService service = hotelServiceRepository.findById(id).orElseThrow();
        service.setStatus("ACTIVE".equals(service.getStatus()) ? "INACTIVE" : "ACTIVE");
        hotelServiceRepository.save(service);
        return "redirect:/admin/services";
    }
}
