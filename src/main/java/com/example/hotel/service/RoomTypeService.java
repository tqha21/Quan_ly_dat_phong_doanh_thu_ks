package com.example.hotel.service;

import com.example.hotel.dto.RoomTypeDTO;
import java.util.List;

public interface RoomTypeService {
    List<RoomTypeDTO> getAllRoomTypes();
    RoomTypeDTO getRoomTypeById(String id);
    void saveRoomType(RoomTypeDTO roomTypeDTO);
    void deleteRoomType(String id);
}
