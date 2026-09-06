package com.example.hotel.service.impl;

import com.example.hotel.dto.RoomTypeDTO;
import com.example.hotel.entity.RoomType;
import com.example.hotel.repository.RoomTypeRepository;
import com.example.hotel.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomTypeDTO> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public RoomTypeDTO getRoomTypeById(String id) {
        RoomType rt = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phòng"));
        return mapToDTO(rt);
    }

    @Override
    @Transactional
    public void saveRoomType(RoomTypeDTO dto) {
        RoomType entity;
        if (dto.getId() == null || dto.getId().isEmpty()) {
            entity = new RoomType();
            entity.setId("RT" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        } else {
            entity = roomTypeRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phòng"));
        }
        
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCapacity(dto.getCapacity());
        entity.setArea(dto.getArea());
        entity.setStatus(dto.getStatus());
        
        roomTypeRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteRoomType(String id) {
        RoomType entity = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phòng"));
        
        // Soft delete bằng cách set status thành INACTIVE để không làm mất history các booking cũ
        entity.setStatus("INACTIVE");
        roomTypeRepository.save(entity);
    }
    
    private RoomTypeDTO mapToDTO(RoomType entity) {
        RoomTypeDTO dto = new RoomTypeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCapacity(entity.getCapacity());
        dto.setArea(entity.getArea());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
