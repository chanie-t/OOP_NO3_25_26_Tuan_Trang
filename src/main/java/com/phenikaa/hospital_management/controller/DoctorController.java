package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.dto.ProfileUpdateDTO;
import com.phenikaa.hospital_management.mapper.MedicalRecordMapper; // <-- THÊM IMPORT
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.MedicalRecord; // <-- THÊM IMPORT
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.MedicalRecordRepository; // <-- THÊM IMPORT
import com.phenikaa.hospital_management.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List; // <-- THÊM IMPORT

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorService doctorService;

    // --- BỔ SUNG 2 DÒNG TIÊM REPO VÀ MAPPER ---
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @GetMapping("/doctor/dashboard")
    public String showDoctorDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Doctor doctor = doctorService.findByUsername(username);

        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointmentRepository.findByDoctorId(doctor.getId()));

        // 1. Lấy danh sách MedicalRecord từ csdl theo doctorId
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByDoctorId(doctor.getId());
        // 2. Dùng mapper để chuyển đổi sang dto (lấy tóm tắt chẩn đoán)
        // 3. Gửi dsach dto ra view
        model.addAttribute("medicalRecords", medicalRecordMapper.toDTOList(medicalRecords));


        return "doctor-dashboard";
    }

    /**
     * form cập nhật hồ sơ Bác sĩ
     */
    @GetMapping("/doctor/profile")
    public String showProfileForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        Doctor doctor = doctorService.findByUsername(username);

        // Chuyển entity sang dto
        ProfileUpdateDTO profileDTO = new ProfileUpdateDTO();
        profileDTO.setFullName(doctor.getFullName());
        profileDTO.setEmail(doctor.getEmail());
        profileDTO.setPhoneNumber(doctor.getPhoneNumber());
        profileDTO.setDateOfBirth(doctor.getDateOfBirth());
        profileDTO.setCurrentUsername(username);

        model.addAttribute("profileDTO", profileDTO);
        model.addAttribute("userRole", "doctor"); // Để form biết action
        return "profile-form";
    }

    /**
     * Xử lý cập nhật hồ sơ Bác sĩ
     */
    @PostMapping("/doctor/profile")
    public String updateProfile(@Valid @ModelAttribute("profileDTO") ProfileUpdateDTO profileDTO,
                                BindingResult bindingResult,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            model.addAttribute("userRole", "doctor");
            return "profile-form";
        }

        try {
            doctorService.updateProfile(username, profileDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật hồ sơ thành công!");
            return "redirect:/doctor/dashboard";
        } catch (IllegalStateException e) {
            // email trùng
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            model.addAttribute("userRole", "doctor");
            return "profile-form";
        }
    }
}