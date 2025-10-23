package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.model.MedicalRecord;
import com.phenikaa.hospital_management.repository.MedicalRecordRepository;
import com.phenikaa.hospital_management.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Hiển thị form tạo bệnh án cho bác sĩ
     * Chỉ bác sĩ mới có quyền tạo bệnh án
     */
    @GetMapping("/records/create-for-appointment/{appointmentId}")
    @PreAuthorize("hasRole('DOCTOR')") 
    public String showCreateRecordForm(/*...*/) { /*...*/ return "create-medical-record"; }

    @PostMapping("/records/create-for-appointment/{appointmentId}")
    @PreAuthorize("hasRole('DOCTOR')") 
    public String processCreateRecord(/*...*/) { /*...*/ return "redirect:/doctor/dashboard"; }
    
    /**
     * Hiển thị chi tiết bệnh án cho bệnh nhân
     * Chỉ bệnh nhân sở hữu bệnh án mới có thể xem
     */
    @GetMapping("/patient/records/{id}")
    // @PreAuthorize sẽ kiểm tra quyền truy cập
    @PreAuthorize("hasRole('PATIENT') and @medicalRecordRepository.findById(#id).get().getPatient().getUsername() == authentication.name")
    public String showMedicalRecordDetail(@PathVariable("id") Long id, Model model) {
        
        // Lấy Entity MedicalRecord từ CSDL
        MedicalRecord record = medicalRecordRepository.findById(id)
                // Ném lỗi nếu không tìm thấy (sẽ được GlobalExceptionHandler bắt)
                .orElseThrow(() -> new com.phenikaa.hospital_management.exception.ResourceNotFoundException("Medical record not found with id: " + id));
        
        // Gửi toàn bộ Entity ra view
        model.addAttribute("record", record); 
        
        return "medical-record-detail";
    }
}