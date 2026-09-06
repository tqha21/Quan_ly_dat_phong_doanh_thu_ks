package com.example.hotel.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private String id;
    private String bookingCode;
    private String customerName;
    private String customerPhone;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalAmount;
    private String status; // PENDING, CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED
    private String note;
    private LocalDateTime createdAt;
    
    // Thuộc tính phụ để hiển thị
    private String roomNumber;
    private String roomTypeName;
}
