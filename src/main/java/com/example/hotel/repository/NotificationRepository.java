package com.example.hotel.repository;

import com.example.hotel.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByUser_IdOrderByCreatedAtDesc(String userId);
    List<Notification> findByUser_IdAndIsReadFalse(String userId);
}
