package com.phenikaa.hospital_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        // Trả về tên của file HTML trong templates (ví dụ: login.html)
        return "login";
    }
}