package com.example.hotel.controller.admin;

import com.example.hotel.dto.RoomTypeDTO;
import com.example.hotel.service.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping
    public String listRoomTypes(Model model) {
        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypes());
        return "admin/room-types/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("roomTypeDTO", new RoomTypeDTO());
        return "admin/room-types/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("roomTypeDTO", roomTypeService.getRoomTypeById(id));
        return "admin/room-types/form";
    }

    @PostMapping("/save")
    public String saveRoomType(@Valid @ModelAttribute("roomTypeDTO") RoomTypeDTO roomTypeDTO,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/room-types/form";
        }
        roomTypeService.saveRoomType(roomTypeDTO);
        return "redirect:/admin/room-types?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoomType(@PathVariable String id) {
        roomTypeService.deleteRoomType(id);
        return "redirect:/admin/room-types?deleted";
    }
}
