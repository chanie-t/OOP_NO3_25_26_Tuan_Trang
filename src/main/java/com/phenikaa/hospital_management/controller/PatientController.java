package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.mapper.AppointmentMapper;
import com.phenikaa.hospital_management.mapper.MedicalRecordMapper;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.MedicalRecord;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
// import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.MedicalRecordRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import com.phenikaa.hospital_management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private MedicalRecordMapper medicalRecordMapper;


    @GetMapping("/patient/dashboard")
    public String showPatientDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Patient not found: " + username));

        //1. Lấy danh sách Entity từ csdl
        List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(patient.getId());

        // 2. Chuyển đổi (Map) sang dto
        // 3. Gửi dto ra view, 0 gửi Entity
        model.addAttribute("appointments", appointmentMapper.toDTOList(appointments));
        model.addAttribute("medicalRecords", medicalRecordMapper.toDTOList(medicalRecords));
        
        return "patient-dashboard";
    }

    @GetMapping("/patient/doctors")
    public String listDoctors(Model model, 
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "fullName") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Doctor> doctorPage = doctorService.findAllDoctorsPaginated(pageable);
        model.addAttribute("doctorPage", doctorPage); 
        return "doctor-list";
    }
}