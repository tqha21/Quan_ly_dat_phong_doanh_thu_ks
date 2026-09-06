package com.example.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomDTO {
    private String id;

    @NotBlank(message = "Số phòng không được để trống")
    private String roomNumber;

    @NotNull(message = "Tầng không được để trống")
    private Integer floor;

    @NotBlank(message = "Vui lòng chọn loại phòng")
    private String roomTypeId;
    
    private String roomTypeName; // Chỉ để hiển thị trên UI
    
    private java.math.BigDecimal price;
    private Integer capacity;

    private String description;
    
    private String status = "AVAILABLE"; // AVAILABLE, OCCUPIED, MAINTENANCE, INACTIVE
}
