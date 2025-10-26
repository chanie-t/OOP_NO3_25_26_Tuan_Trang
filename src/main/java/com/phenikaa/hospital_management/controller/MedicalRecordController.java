package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
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
     * Chỉ bác sĩ sở hữu lịch hẹn mới được xem form
     */
    @GetMapping("/records/create-for-appointment/{appointmentId}")
    @PreAuthorize("hasRole('DOCTOR') and @appointmentRepository.findById(#appointmentId).get().getDoctor().getUsername() == authentication.name") 
    public String showCreateRecordForm(@PathVariable("appointmentId") Long appointmentId, Model model) { 
        model.addAttribute("appointmentId", appointmentId);
        return "create-medical-record"; 
    }

    /**
     * Chỉ bác sĩ sở hữu lịch hẹn mới được tạo bệnh án
     */
    @PostMapping("/records/create-for-appointment/{appointmentId}")
    @PreAuthorize("hasRole('DOCTOR') and @appointmentRepository.findById(#appointmentId).get().getDoctor().getUsername() == authentication.name") 
    public String processCreateRecord(@PathVariable("appointmentId") Long appointmentId,
                                      @RequestParam("diagnosis") String diagnosis,
                                      @RequestParam("prescription") String prescription,
                                      RedirectAttributes redirectAttributes) {
        try {
            medicalRecordService.createMedicalRecord(appointmentId, diagnosis, prescription);
            redirectAttributes.addFlashAttribute("successMessage", "Đã tạo bệnh án thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tạo bệnh án: " + e.getMessage());
        }
        return "redirect:/doctor/dashboard"; 
    }
    
    /**
     * Hiển thị chi tiết bệnh án cho bệnh nhân
     * Chỉ bệnh nhân sở hữu bệnh án mới có thể xem
     */
    @GetMapping("/patient/records/{id}")
    @PreAuthorize("hasRole('PATIENT') and @medicalRecordRepository.findById(#id).get().getPatient().getUsername() == authentication.name")
    public String showMedicalRecordDetail(@PathVariable("id") Long id, Model model) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
        
        model.addAttribute("record", record); 
        
        return "medical-record-detail";
    }

    /**
     * Hiển thị form cập nhật bệnh án
     * Chỉ bác sĩ đã tạo bệnh án mới có quyền sửa
     */
    @GetMapping("/records/edit/{id}")
    @PreAuthorize("hasRole('DOCTOR') and @medicalRecordRepository.findById(#id).get().getDoctor().getUsername() == authentication.name")
    public String showUpdateRecordForm(@PathVariable("id") Long id, Model model) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
        
        model.addAttribute("record", record);
        return "edit-medical-record";
    }

    /**
     * Xử lý cập nhật bệnh án
     */
    @PostMapping("/records/edit/{id}")
    @PreAuthorize("hasRole('DOCTOR') and @medicalRecordRepository.findById(#id).get().getDoctor().getUsername() == authentication.name")
    public String processUpdateRecord(@PathVariable("id") Long id,
                                      @RequestParam("diagnosis") String diagnosis,
                                      @RequestParam("prescription") String prescription,
                                      RedirectAttributes redirectAttributes) {
        try {
            medicalRecordService.updateMedicalRecord(id, diagnosis, prescription);
            redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật bệnh án thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật: " + e.getMessage());
        }
        return "redirect:/doctor/dashboard";
    }

    /**
     * Xử lý xóa bệnh án
     */
    @PostMapping("/records/delete/{id}")
    @PreAuthorize("hasRole('DOCTOR') and @medicalRecordRepository.findById(#id).get().getDoctor().getUsername() == authentication.name")
    public String deleteRecord(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            medicalRecordService.deleteMedicalRecord(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa bệnh án thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa: " + e.getMessage());
        }
        return "redirect:/doctor/dashboard";
    }
}