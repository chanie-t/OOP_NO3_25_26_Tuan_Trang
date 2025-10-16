package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    // GET de hien thi danh sach
    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients"; // Trả về file patients.html
    }

    // GET de hien thi form them moi
    @GetMapping("/patients/add")
    public String showAddPatientForm(Model model) {
        // Tạo đối tượng Patient rỗng để Thymeleaf binding dữ liệu
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "add-patient"; // Trả về file add-patient.html
    }

    // POST de xu ly du lieu tu form
    @PostMapping("/patients")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients"; // Chuyển hướng về trang danh sách
    }
}