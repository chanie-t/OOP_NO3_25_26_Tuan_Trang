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

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDTO", new UserRegistrationDTO());
        return "register"; 
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("registrationDTO") UserRegistrationDTO registrationDTO,
                                      BindingResult bindingResult,
                                      Model model) {
        
        // Kiểm tra lỗi validation (bao gồm cả @UniqueUsername và @UniqueEmail)
        if (bindingResult.hasErrors()) {
            return "register"; // trả về form và hiển thị lỗi
        }

        try {
            patientService.registerNewPatient(registrationDTO);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình đăng ký.");
            // Giữ lại DTO đã nhập để người dùng không phải nhập lại
            model.addAttribute("registrationDTO", registrationDTO); 
            return "register"; // Trả về trang đăng ký với thông báo lỗi chung
        }
    }
}