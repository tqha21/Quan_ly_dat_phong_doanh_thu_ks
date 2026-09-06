package com.example.hotel.controller.admin;

import com.example.hotel.dto.RoomDTO;
import com.example.hotel.service.RoomService;
import com.example.hotel.service.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomTypeService roomTypeService; // Lấy danh sách loại phòng cho thẻ select

    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/rooms/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("roomDTO", new RoomDTO());
        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypes()); 
        return "admin/rooms/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("roomDTO", roomService.getRoomById(id));
        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypes());
        return "admin/rooms/form";
    }

    @PostMapping("/save")
    public String saveRoom(@Valid @ModelAttribute("roomDTO") RoomDTO roomDTO,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roomTypes", roomTypeService.getAllRoomTypes());
            return "admin/rooms/form";
        }
        
        try {
            roomService.saveRoom(roomDTO);
            return "redirect:/admin/rooms?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("roomTypes", roomTypeService.getAllRoomTypes());
            return "admin/rooms/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            return "redirect:/admin/rooms?deleted";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/rooms";
        }
    }
}
