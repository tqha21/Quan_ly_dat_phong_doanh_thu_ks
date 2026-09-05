package com.example.hotel.service;

import com.example.hotel.dto.RoomDTO;
import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRooms();
    RoomDTO getRoomById(String id);
    void saveRoom(RoomDTO roomDTO);
    void deleteRoom(String id);
    List<RoomDTO> searchAvailableRooms(java.time.LocalDate checkIn, java.time.LocalDate checkOut);
}
