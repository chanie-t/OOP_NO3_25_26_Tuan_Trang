package com.phenikaa.hospital_management.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyGlobal {

    private static final Logger log = LoggerFactory.getLogger(MyGlobal.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", ex.getMessage());
        mav.setViewName("error/404"); 
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access Denied: Người dùng đã cố gắng truy cập tài nguyên không có quyền. Lỗi: {}", ex.getMessage());
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Bạn không có quyền truy cập chức năng này.");
        mav.setViewName("error/403"); 
        return mav;
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.warn("Illegal State Exception: {}", ex.getMessage());
        
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        String referer = request.getHeader("Referer");
        // Trả về trang trước đó hoặc trang chủ nếu không có referer
        return "redirect:" + (referer != null ? referer : "/"); 
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.warn("Data Integrity Violation: {}", ex.getMessage());
        
        // Lỗi này thường xảy ra do race condition khi 2 người cùng đăng ký 1 email/username
        // Hoặc khi cập nhật profile
        redirectAttributes.addFlashAttribute("errorMessage", "Thông tin (ví dụ: Email hoặc Tên đăng nhập) đã bị trùng lặp. Vui lòng thử lại.");
        
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/"); 
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        log.error("Unhandled exception occurred", ex); // Log lỗi nghiêm trọng
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Đã có lỗi không mong muốn xảy ra: " + ex.getMessage());
        mav.setViewName("error/500");
        return mav;
    }
}