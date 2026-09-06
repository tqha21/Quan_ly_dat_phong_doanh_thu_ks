package com.example.hotel.service.impl;

import com.example.hotel.dto.RoomDTO;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.RoomType;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.repository.RoomTypeRepository;
import com.example.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(String id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));
        return mapToDTO(room);
    }

    @Override
    @Transactional
    public void saveRoom(RoomDTO dto) {
        // Validation: Kiểm tra trùng số phòng
        roomRepository.findByRoomNumber(dto.getRoomNumber()).ifPresent(existing -> {
            if (dto.getId() == null || !existing.getId().equals(dto.getId())) {
                throw new IllegalArgumentException("Số phòng đã tồn tại trong hệ thống!");
            }
        });

        RoomType type = roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phòng"));

        Room entity;
        if (dto.getId() == null || dto.getId().isEmpty()) {
            entity = new Room();
            entity.setId("R" + UUID.randomUUID().toString().substring(0, 7).toUpperCase());
        } else {
            entity = roomRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));
        }

        entity.setRoomNumber(dto.getRoomNumber());
        entity.setFloor(dto.getFloor());
        entity.setRoomType(type);
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());

        roomRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteRoom(String id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));
        
        if ("OCCUPIED".equals(room.getStatus())) {
            throw new IllegalArgumentException("Không thể xóa phòng đang có khách!");
        }
        
        room.setStatus("INACTIVE"); // Soft delete
        roomRepository.save(room);
    }

    @Override
    public List<RoomDTO> searchAvailableRooms(java.time.LocalDate checkIn, java.time.LocalDate checkOut) {
        if (checkIn == null || checkOut == null) return java.util.Collections.emptyList();
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new IllegalArgumentException("Ngày trả phòng phải lớn hơn ngày nhận phòng");
        }
        return roomRepository.findAvailableRooms(checkIn, checkOut)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private RoomDTO mapToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setFloor(room.getFloor());
        dto.setRoomTypeId(room.getRoomType().getId());
        dto.setRoomTypeName(room.getRoomType().getName());
        dto.setPrice(room.getRoomType().getPrice());
        dto.setCapacity(room.getRoomType().getCapacity());
        dto.setDescription(room.getDescription());
        dto.setStatus(room.getStatus());
        return dto;
    }
}
