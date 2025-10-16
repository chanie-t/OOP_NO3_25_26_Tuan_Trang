package com.phenikaa.hospital_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorController {

    /**
     * Phương thức này xử lý yêu cầu GET đến /doctor/dashboard
     * và hiển thị trang dashboard cho bác sĩ.
     */
    @GetMapping("/doctor/dashboard")
    public String showDoctorDashboard() {
        // Trả về file HTML tên là doctor-dashboard.html
        return "doctor-dashboard";
    }
}