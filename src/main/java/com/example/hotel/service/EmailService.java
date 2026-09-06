package com.example.hotel.service;

import com.example.hotel.entity.Booking;

public interface EmailService {
    void sendBookingConfirmation(Booking booking);
}