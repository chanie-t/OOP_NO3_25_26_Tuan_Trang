package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/patient/dashboard")
    public String showPatientDashboard() {
        return "patient-dashboard";
    }

    @GetMapping("/patient/doctors")
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctor-list";
    }
}