package com.example.hotel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomTypeDTO {
    private String id;

    @NotBlank(message = "Tên loại phòng không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Giá không được để trống")
    @Min(value = 0, message = "Giá không hợp lệ")
    private BigDecimal price;

    @NotNull(message = "Sức chứa không được để trống")
    @Min(value = 1, message = "Sức chứa tối thiểu là 1")
    private Integer capacity;

    private BigDecimal area;
    private String status = "ACTIVE";
}
