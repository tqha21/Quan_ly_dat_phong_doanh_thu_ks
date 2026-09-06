package com.example.hotel.repository;

import com.example.hotel.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImage, String> {
    List<RoomImage> findByRoom_Id(String roomId);
}
