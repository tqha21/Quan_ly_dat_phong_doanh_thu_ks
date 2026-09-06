package com.example.hotel.repository;

import com.example.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByRoomType_IdAndStatus(String roomTypeId, String status);

    /**
     * TRUY VẤN TÌM PHÒNG TRỐNG TRONG KHOẢNG THỜI GIAN
     * Logic: Tìm tất cả các phòng (status = AVAILABLE) 
     * KHÔNG NẰM TRONG danh sách các phòng đã được đặt (booking không bị hủy/check-out)
     * có khoảng thời gian [checkIn, checkOut] giao nhau (overlap) với ngày khách yêu cầu.
     */
    @Query("SELECT r FROM Room r WHERE r.status = 'AVAILABLE' AND r.id NOT IN (" +
           "SELECT bd.room.id FROM BookingDetail bd JOIN bd.booking b " +
           "WHERE b.status NOT IN ('CANCELLED', 'CHECKED_OUT') " +
           "AND (b.checkInDate < :checkOut AND b.checkOutDate > :checkIn))")
    List<Room> findAvailableRooms(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    /**
     * Truy vấn kiểm tra cụ thể 1 danh sách ID phòng có trống trong khoảng thời gian hay không
     * Trả về danh sách ID các phòng BỊ TRÙNG LỊCH (để ném Exception nếu danh sách này > 0).
     */
    @Query("SELECT bd.room.id FROM BookingDetail bd JOIN bd.booking b " +
           "WHERE bd.room.id IN :roomIds " +
           "AND b.status NOT IN ('CANCELLED', 'CHECKED_OUT') " +
           "AND (b.checkInDate < :checkOut AND b.checkOutDate > :checkIn)")
    List<String> findConflictingRoomIds(@Param("roomIds") List<String> roomIds, 
                                        @Param("checkIn") LocalDate checkIn, 
                                        @Param("checkOut") LocalDate checkOut);
}
