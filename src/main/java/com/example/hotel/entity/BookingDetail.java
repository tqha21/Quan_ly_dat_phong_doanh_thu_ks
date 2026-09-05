package com.example.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetail {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer quantity = 1;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;
}
