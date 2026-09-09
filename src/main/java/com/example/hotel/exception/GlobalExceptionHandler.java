package com.example.hotel.exception;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public String handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "Rất tiếc! Phòng bạn chọn vừa được khách hàng khác đặt. Vui lòng chọn phòng khác hoặc thử lại.");
        return "redirect:/rooms"; // Chuyển hướng người dùng về trang danh sách phòng
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/home";
    }
}
