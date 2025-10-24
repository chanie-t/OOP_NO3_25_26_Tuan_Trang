package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("registrationDTO", new UserRegistrationDTO()); 
        return "index"; // Trả về file index
    }
}