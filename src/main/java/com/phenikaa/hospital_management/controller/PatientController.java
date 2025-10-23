package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.mapper.AppointmentMapper;
import com.phenikaa.hospital_management.mapper.MedicalRecordMapper;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.MedicalRecord;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
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

    // Inject các Repository cần thiết
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    // Inject Service và Mapper
    @Autowired
    private DoctorService doctorService; // Dùng cho phân trang bác sĩ
    @Autowired
    private AppointmentMapper appointmentMapper; // Dùng để chuyển đổi Appointment sang DTO
    @Autowired
    private MedicalRecordMapper medicalRecordMapper; // Dùng để chuyển đổi MedicalRecord sang DTO

    /**
     * Hiển thị trang dashboard của bệnh nhân.
     * Tải danh sách lịch hẹn và bệnh án (dưới dạng DTO) để hiển thị.
     */
    @GetMapping("/patient/dashboard")
    public String showPatientDashboard(Model model, Authentication authentication) {
        // Lấy thông tin bệnh nhân đang đăng nhập
        String username = authentication.getName();
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Patient not found: " + username));

        // 1. Lấy danh sách Entity từ CSDL
        List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(patient.getId());

        // 2. Chuyển đổi (Map) sang DTO bằng MapStruct
        // 3. Gửi danh sách DTO ra view (an toàn và hiệu quả hơn)
        model.addAttribute("appointments", appointmentMapper.toDTOList(appointments));
        model.addAttribute("medicalRecords", medicalRecordMapper.toDTOList(medicalRecords));
        
        return "patient-dashboard"; // Trả về view patient-dashboard.html
    }

    /**
     * Hiển thị danh sách bác sĩ có phân trang và sắp xếp.
     */
    @GetMapping("/patient/doctors")
    public String listDoctors(Model model,
                              // Tham số phân trang từ URL, có giá trị mặc định
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              // Tham số sắp xếp, mặc định theo id tăng dần (asc)
                              @RequestParam(defaultValue = "id,asc") String sort) {

        // Xử lý tham số sort (tách trường và chiều sắp xếp)
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) ?
                                       Sort.Direction.DESC : Sort.Direction.ASC;

        // Tạo đối tượng Pageable để truy vấn
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        
        // Gọi service để lấy trang dữ liệu bác sĩ
        Page<Doctor> doctorPage = doctorService.findAllDoctorsPaginated(pageable);
        
        // Gửi đối tượng Page ra view
        model.addAttribute("doctorPage", doctorPage);
        
        return "doctor-list";
    }
}