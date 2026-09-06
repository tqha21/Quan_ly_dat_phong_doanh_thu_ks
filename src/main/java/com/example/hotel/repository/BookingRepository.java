package com.example.hotel.repository;

import com.example.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<Booking> findByBookingCode(String bookingCode);
    List<Booking> findByCustomer_IdOrderByCreatedAtDesc(String customerId);
    List<Booking> findByStatusOrderByCreatedAtDesc(String status);
    
    long countByStatus(String status);
    
    List<Booking> findAllByOrderByCreatedAtDesc();
}
