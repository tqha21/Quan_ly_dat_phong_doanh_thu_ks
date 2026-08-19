package com.example.hotel.service.impl;

import com.example.hotel.dto.BookingDTO;
import com.example.hotel.dto.BookingRequest;
import com.example.hotel.entity.Booking;
import com.example.hotel.entity.BookingDetail;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.User;
import com.example.hotel.repository.BookingDetailRepository;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingDTO createBooking(BookingRequest request, String customerEmail) {
        if (request.getCheckOutDate().isBefore(request.getCheckInDate()) || request.getCheckOutDate().isEqual(request.getCheckInDate())) {
            throw new IllegalArgumentException("Ngày trả phòng phải sau ngày nhận phòng.");
        }

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin khách hàng"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));

        // Double check chống race-condition (trùng lịch do đặt cùng lúc)
        List<String> conflicts = roomRepository.findConflictingRoomIds(
                Collections.singletonList(room.getId()), request.getCheckInDate(), request.getCheckOutDate()
        );
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Xin lỗi, phòng này vừa được người khác đặt trong khoảng thời gian bạn chọn.");
        }

        // Tính tiền = số đêm * giá phòng
        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal pricePerNight = room.getRoomType().getPrice();
        BigDecimal totalAmount = pricePerNight.multiply(new BigDecimal(nights));

        // Lưu thông tin Booking
        Booking booking = new Booking();
        booking.setId("B" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setCustomer(customer);
        booking.setBookingCode("BK" + System.currentTimeMillis() % 1000000); // Mã hóa đơn đẹp BKxxxxxx
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalAmount(totalAmount);
        booking.setStatus("PENDING"); // Đợi lễ tân xác nhận
        booking.setNote(request.getNote());
        booking = bookingRepository.save(booking);

        // Lưu thông tin Booking Detail
        BookingDetail detail = new BookingDetail();
        detail.setId("BD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        detail.setBooking(booking);
        detail.setRoom(room);
        detail.setPrice(pricePerNight);
        detail.setQuantity(1);
        detail.setSubtotal(totalAmount);
        bookingDetailRepository.save(detail);

        return mapToDTO(booking);
    }

    @Override
    public List<BookingDTO> getMyBookings(String customerEmail) {
        User customer = userRepository.findByEmail(customerEmail).orElseThrow();
        return bookingRepository.findByCustomer_IdOrderByCreatedAtDesc(customer.getId())
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public BookingDTO getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId).map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã đặt phòng"));
    }

    @Override
    @Transactional
    public void cancelBooking(String bookingId, String customerEmail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã đặt phòng"));
        
        if (!booking.getCustomer().getEmail().equals(customerEmail)) {
            throw new RuntimeException("Bạn không có quyền hủy đơn này");
        }

        if (!"PENDING".equals(booking.getStatus())) {
            throw new RuntimeException("Chỉ có thể hủy phòng ở trạng thái CHỜ XÁC NHẬN");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateBookingStatus(String bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã đặt phòng"));
                
        // Add valid transitions if needed. For now just update
        if (List.of("PENDING", "CONFIRMED", "CHECKED_IN", "CHECKED_OUT", "CANCELLED").contains(status)) {
            booking.setStatus(status);
            bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Trạng thái không hợp lệ: " + status);
        }
    }

    private BookingDTO mapToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setBookingCode(booking.getBookingCode());
        dto.setCustomerName(booking.getCustomer().getFullName());
        dto.setCustomerPhone(booking.getCustomer().getPhone());
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setStatus(booking.getStatus());
        dto.setNote(booking.getNote());
        dto.setCreatedAt(booking.getCreatedAt());

        if (booking.getBookingDetails() != null && !booking.getBookingDetails().isEmpty()) {
            Room r = booking.getBookingDetails().get(0).getRoom();
            dto.setRoomNumber(r.getRoomNumber());
            dto.setRoomTypeName(r.getRoomType().getName());
        }
        return dto;
    }
}
