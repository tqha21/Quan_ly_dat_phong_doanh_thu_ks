package com.example.hotel.service.impl;

import com.example.hotel.entity.Booking;
import com.example.hotel.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Override
    public void sendBookingConfirmation(Booking booking) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null || booking.getCustomer().getEmail() == null) return;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(booking.getCustomer().getEmail());
            message.setSubject("Xác nhận đặt phòng " + booking.getBookingCode());
            message.setText("Xin chào " + booking.getCustomer().getFullName() + ",\n\n"
                    + "Khách sạn đã tiếp nhận đặt phòng " + booking.getBookingCode() + ".\n"
                    + "Nhận phòng: " + booking.getCheckInDate() + "\n"
                    + "Trả phòng: " + booking.getCheckOutDate() + "\n"
                    + "Tổng tiền: " + booking.getTotalAmount() + " VNĐ\n"
                    + "Trạng thái: " + booking.getStatus());
            mailSender.send(message);
        } catch (RuntimeException ignored) {
            // Email is optional and must not make a successful booking fail.
        }
    }
}