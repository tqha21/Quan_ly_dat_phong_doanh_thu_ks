package com.example.hotel.controller;

import com.example.hotel.dto.RoomDTO;
import com.example.hotel.dto.RoomSearchRequest;
import com.example.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PublicController {

    private final RoomService roomService;
    private final com.example.hotel.repository.ReviewRepository reviewRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        RoomSearchRequest request = new RoomSearchRequest();
        request.setCheckIn(LocalDate.now());
        request.setCheckOut(LocalDate.now().plusDays(1)); // Mặc định 1 đêm
        model.addAttribute("searchRequest", request);
        return "customer/home";
    }

    @GetMapping("/rooms")
    public String searchRooms(@ModelAttribute("searchRequest") RoomSearchRequest request, Model model) {
        try {
            if (request.getCheckIn() == null) request.setCheckIn(LocalDate.now());
            if (request.getCheckOut() == null) request.setCheckOut(LocalDate.now().plusDays(1));
            
            List<RoomDTO> availableRooms = roomService.searchAvailableRooms(request.getCheckIn(), request.getCheckOut());
            model.addAttribute("rooms", availableRooms);
            model.addAttribute("searchRequest", request);
            return "customer/rooms";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/home";
        }
    }

    @GetMapping("/rooms/{id}")
    public String roomDetail(@org.springframework.web.bind.annotation.PathVariable("id") String roomId, Model model) {
        RoomDTO room = roomService.getRoomById(roomId);
        model.addAttribute("room", room);
        
        List<com.example.hotel.entity.Review> reviews = reviewRepository.findByRoom_IdAndStatus(roomId, "APPROVED");
        model.addAttribute("reviews", reviews);
        
        return "customer/room-detail";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/debug/users")
    @org.springframework.web.bind.annotation.ResponseBody
    public java.util.List<com.example.hotel.entity.User> debugUsers(com.example.hotel.repository.UserRepository userRepo) {
        return userRepo.findAll();
    }
}
