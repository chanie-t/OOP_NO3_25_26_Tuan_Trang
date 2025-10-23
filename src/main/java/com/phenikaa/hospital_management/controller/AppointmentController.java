package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.dto.AppointmentRequestDTO;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.PatientRepository;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository; 

    //Hiển thị form để bệnh nhân đặt lịch hẹn
    @GetMapping("/patient/appointments/new/{doctorId}")
    public String showAppointmentForm(@PathVariable Long doctorId, Model model) {
        // tạo DTO rỗng -> view
        AppointmentRequestDTO dto = new AppointmentRequestDTO();
        dto.setDoctorId(doctorId);
        
        model.addAttribute("appointmentRequest", dto); 
        model.addAttribute("doctor", doctorRepository.findById(doctorId).orElse(null));
        return "appointment-form"; //
    }

    // Xử lý khi bệnh nhân gửi form đặt lịch
    @PostMapping("/patient/appointments/create")
    public String createAppointment(@Valid @ModelAttribute("appointmentRequest") AppointmentRequestDTO requestDTO,
                                    BindingResult bindingResult,
                                    Authentication authentication,
                                    Model model) {

        // Lấy thông tin patient đang đăng nhập
        String username = authentication.getName();
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        // Nếu validation  thất bại
        if (bindingResult.hasErrors()) {
            // Cần tải lại tên bác sĩ để hiển thị lại form
            model.addAttribute("doctor", doctorRepository.findById(requestDTO.getDoctorId()).orElse(null));
            return "appointment-form"; // Trả về form và hiển thị lỗi
        }

        // Gọi service với DTO để tạo lịch hẹn
        appointmentService.createAppointment(patient.getId(), requestDTO);

        return "redirect:/patient/dashboard?book_success"; //
    }
    
    // Xử lý khi bệnh nhân hủy lịch
    @PostMapping("/patient/appointments/cancel/{id}")
    @PreAuthorize("@appointmentRepository.findById(#id).get().getPatient().getUsername() == authentication.name")
    public String cancelAppointment(@PathVariable("id") Long id, 
                                  RedirectAttributes redirectAttributes) {
        appointmentService.cancelAppointment(id);
        redirectAttributes.addFlashAttribute("cancel_success", "Hủy lịch hẹn thành công.");
        return "redirect:/patient/dashboard";
    }

    // Xử lý khi bác sĩ hoàn thành lịch
    @PostMapping("/doctor/appointments/complete/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public String completeAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.completeAppointment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã hoàn thành lịch hẹn.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/doctor/dashboard";
    }
}