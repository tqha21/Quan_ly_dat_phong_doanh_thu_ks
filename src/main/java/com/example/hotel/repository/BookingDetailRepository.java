package com.example.hotel.repository;

import com.example.hotel.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, String> {
}
