package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import com.phenikaa.hospital_management.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private PatientService patientService;

    /**
     * Hiển thị trang đăng nhập
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; //
    }

    /**
     * Hiển thị form đăng ký
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Gửi DTO rỗng ra view để binding
        model.addAttribute("registrationDTO", new UserRegistrationDTO());
        return "register"; //
    }

    /**
     * Xử lý khi người dùng gửi form đăng ký
     */
    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("registrationDTO") UserRegistrationDTO registrationDTO,
                                      BindingResult bindingResult,
                                      Model model) {
        
        // Kiểm tra lỗi validation (bao gồm cả @UniqueUsername và @UniqueEmail)
        if (bindingResult.hasErrors()) {
            return "register"; // Trả về form và hiển thị lỗi
        }

        patientService.registerNewPatient(registrationDTO);
        return "redirect:/login?success";
    }
}