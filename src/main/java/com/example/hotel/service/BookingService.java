package com.example.hotel.service;

import com.example.hotel.dto.BookingDTO;
import com.example.hotel.dto.BookingRequest;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingRequest request, String customerEmail);
    List<BookingDTO> getMyBookings(String customerEmail);
    BookingDTO getBookingById(String bookingId);
    void cancelBooking(String bookingId, String customerEmail);
    
    // Admin/Staff methods
    List<BookingDTO> getAllBookings();
    void updateBookingStatus(String bookingId, String status);
}
