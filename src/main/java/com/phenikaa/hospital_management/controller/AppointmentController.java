package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.PatientRepository;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;


    @GetMapping("/patient/appointments/new/{doctorId}")
    public String showAppointmentForm(@PathVariable Long doctorId, Model model) {
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("doctor", doctorRepository.findById(doctorId).orElse(null));
        return "appointment-form";
    }

    @PostMapping("/patient/appointments/create")
    public String createAppointment(@RequestParam Long doctorId,
                                    @RequestParam String appointmentTime,
                                    @RequestParam String reason,
                                    Authentication authentication) {

        // Lấy username của patient đang đăng nhập
        String username = authentication.getName();
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        appointmentService.createAppointment(
                patient.getId(),
                doctorId,
                LocalDateTime.parse(appointmentTime),
                reason);

        return "redirect:/patient/dashboard?book_success";
    }
}