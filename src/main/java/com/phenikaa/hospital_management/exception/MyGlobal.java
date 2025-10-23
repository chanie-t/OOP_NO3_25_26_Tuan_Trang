package com.phenikaa.hospital_management.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyGlobal {

    // Xử lý khi không tìm thấy tài nguyên (lỗi 404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", ex.getMessage());
        mav.setViewName("error/404"); 
        return mav;
    }

    // Xử lý khi người dùng không có quyền truy cập (lỗi 403)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Bạn không có quyền truy cập chức năng này.");
        mav.setViewName("error/403"); 
        return mav;
    }

    // Xử lý các lỗi logic chung
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        // Quay lại trang trước đó
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    // Xử lý tất cả các lỗi khác (lỗi 500)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Đã có lỗi không mong muốn xảy ra: " + ex.getMessage());
        mav.setViewName("error/500");
        return mav;
    }
}