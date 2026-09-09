package com.example.hotel.repository;

import com.example.hotel.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, String> {
    Optional<Promotion> findByCode(String code);
}
