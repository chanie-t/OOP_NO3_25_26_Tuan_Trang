package com.phenikaa.hospital_management.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        // lấy thông báo lỗi từ Exception
        String errorMessage = ex.getMessage();
        
        // thêm thông báo lỗi vào model để trang error.html có thể hiển thị
        model.addAttribute("errorMessage", errorMessage);
        
        // trả về trang error
        return "error";
    }
}