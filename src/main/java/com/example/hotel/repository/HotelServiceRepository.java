package com.example.hotel.repository;

import com.example.hotel.entity.HotelService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelServiceRepository extends JpaRepository<HotelService, String> {
    List<HotelService> findByStatus(String status);
}
