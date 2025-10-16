package com.phenikaa.hospital_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Xử lý yêu cầu đến trang chủ ("/")
     * @return Tên của template index.html
     */
    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Trả về file /resources/templates/index.html
    }
}