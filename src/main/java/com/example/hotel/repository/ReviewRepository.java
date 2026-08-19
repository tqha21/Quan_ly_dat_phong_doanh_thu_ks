package com.example.hotel.repository;

import com.example.hotel.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByRoom_IdAndStatus(String roomId, String status);
}
