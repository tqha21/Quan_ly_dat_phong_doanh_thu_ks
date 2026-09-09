package com.example.hotel.repository;

import com.example.hotel.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, String> {
}
