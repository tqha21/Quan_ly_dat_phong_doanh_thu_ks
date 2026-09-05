package com.example.hotel.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BookingRequest {
    @NotBlank(message = "Thiếu thông tin phòng")
    private String roomId;

    @NotNull(message = "Thiếu ngày nhận phòng")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;

    @NotNull(message = "Thiếu ngày trả phòng")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;

    private String note;
}
