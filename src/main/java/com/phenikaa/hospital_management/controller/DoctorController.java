// File: src/main/java/com/phenikaa/hospital_management/controller/DoctorController.java
package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/doctor/dashboard")
    public String showDoctorDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Doctor doctor = doctorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointmentRepository.findByDoctorId(doctor.getId()));

        return "doctor-dashboard";
    }
}