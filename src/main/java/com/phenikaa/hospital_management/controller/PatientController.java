package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.dto.ProfileUpdateDTO;
import com.phenikaa.hospital_management.mapper.AppointmentMapper;
import com.phenikaa.hospital_management.mapper.MedicalRecordMapper;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.MedicalRecord;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.MedicalRecordRepository;
// import com.phenikaa.hospital_management.repository.PatientRepository;
import com.phenikaa.hospital_management.service.DoctorService;
import com.phenikaa.hospital_management.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PatientController {

    // @Autowired
    // private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    private SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/patient/dashboard")
    public String showPatientDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Patient patient = patientService.findByUsername(username);

        List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(patient.getId());
        
        model.addAttribute("appointments", appointmentMapper.toDTOList(appointments));
        model.addAttribute("medicalRecords", medicalRecordMapper.toDTOList(medicalRecords));
        
        model.addAttribute("patientName", patient.getFullName());
        
        return "patient-dashboard";
    }

    @GetMapping("/patient/doctors")
    public String listDoctors(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "id,asc") String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) ?
                                       Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<Doctor> doctorPage = doctorService.findAllDoctorsPaginated(pageable);
        model.addAttribute("doctorPage", doctorPage);
        
        return "doctor-list";
    }
    /**
     * Hiển thị form cập nhật hồ sơ Bệnh nhân
     */
    @GetMapping("/patient/profile")
    public String showProfileForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        Patient patient = patientService.findByUsername(username);

        // Chuyển Entity sang DTO để gửi ra form
        ProfileUpdateDTO profileDTO = new ProfileUpdateDTO();
        profileDTO.setFullName(patient.getFullName());
        profileDTO.setEmail(patient.getEmail());
        profileDTO.setPhoneNumber(patient.getPhoneNumber());
        profileDTO.setDateOfBirth(patient.getDateOfBirth());
        profileDTO.setCurrentUsername(username); // Giữ username hiện tại

        model.addAttribute("profileDTO", profileDTO);
        model.addAttribute("userRole", "patient"); // Để form biết action là gì
        return "profile-form";
    }

    /**
     * Xử lý cập nhật hồ sơ Bệnh nhân
     */
    @PostMapping("/patient/profile")
    public String updateProfile(@Valid @ModelAttribute("profileDTO") ProfileUpdateDTO profileDTO,
                                BindingResult bindingResult,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            model.addAttribute("userRole", "patient");
            return "profile-form";
        }

        try {
            patientService.updateProfile(username, profileDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật hồ sơ thành công!");
            return "redirect:/patient/dashboard";
        } catch (IllegalStateException e) {
            // email trùng từ Service
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            model.addAttribute("userRole", "patient");
            return "profile-form";
        }
    }

    /**
     * Xử lý khi bệnh nhân tự vô hiệu hóa tài khoản
     */
    @PostMapping("/patient/deactivate")
    public String deactivateAccount(Authentication authentication,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        String username = authentication.getName();

        try {
            // 1. Gọi service để set active = false
            patientService.deactivatePatient(username);

            // 2. Đăng xuất người dùng ra khỏi phiên làm việc hiện tại
            // 2. Đăng xuất người dùng ra khỏi phiên hiện tại
            this.logoutHandler.logout(request, response, authentication);

            // 3. Chuyển hướng về trang đăng nhập với thông báo
            return "redirect:/login?deactivated";

        } catch (Exception e) {
            // Nếu có lỗi, quay lại trang profile với thông báo lỗi
            return "redirect:/patient/profile?error=deactivate";
        }
    }
}