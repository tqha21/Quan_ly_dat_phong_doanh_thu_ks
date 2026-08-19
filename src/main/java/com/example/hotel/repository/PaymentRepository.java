package com.example.hotel.repository;

import com.example.hotel.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    
    /**
     * TÍNH DOANH THU: 
     * Tổng tiền của các payment có trạng thái là 'PAID' trong khoảng thời gian.
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentStatus = 'PAID' AND p.paidAt BETWEEN :startDate AND :endDate")
    BigDecimal calculateRevenueBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
