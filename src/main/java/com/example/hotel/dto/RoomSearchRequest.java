package com.example.hotel.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class RoomSearchRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
}
