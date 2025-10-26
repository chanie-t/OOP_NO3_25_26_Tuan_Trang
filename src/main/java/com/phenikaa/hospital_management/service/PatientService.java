package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.dto.ProfileUpdateDTO;
import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.mapper.PatientMapper;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.AppointmentStatus;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import java.util.List;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientMapper patientMapper;

    public Patient registerNewPatient(UserRegistrationDTO registrationDTO) {
        Patient patient = patientMapper.toEntity(registrationDTO);
        patient.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        patient.setRole("PATIENT");
        patient.setActive(true);
        
        return patientRepository.save(patient);
    }

    /**
     * Tìm bệnh nhân bằng username
     */
    public Patient findByUsername(String username) {
        return patientRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with username: " + username));
    }

    /**
     * Cập nhật thông tin hồ sơ bệnh nhân
     */
    @Transactional // Đảm bảo tất cả thay đổi được lưu hoặc rollback
    public Patient updateProfile(String username, ProfileUpdateDTO profileDTO) {
        Patient patient = findByUsername(username);

        // 1. check mail có bị trùng không
        // (chỉ check khi email bị đổi)
        if (!patient.getEmail().equals(profileDTO.getEmail())) {
            // check mail mới có bị trùng không
            boolean emailExists = patientRepository.findByEmail(profileDTO.getEmail()).isPresent() ||
                                  doctorRepository.findByEmail(profileDTO.getEmail()).isPresent();
            if (emailExists) {
                // Ném lỗi này sẽ được Controller bắt và hiển thị ra form
                throw new IllegalStateException("Email " + profileDTO.getEmail() + " đã tồn tại.");
            }
            patient.setEmail(profileDTO.getEmail());
        }

        patient.setFullName(profileDTO.getFullName());
        patient.setPhoneNumber(profileDTO.getPhoneNumber());
        patient.setDateOfBirth(profileDTO.getDateOfBirth());

        // 3. trả về patient được cập nhật
        return patientRepository.save(patient);
    }

    /**
     * Vô hiệu hóa tài khoản bệnh nhân
     * Tự động hủy các lịch hẹn chưa diễn ra khi bệnh nhân bị vô hiệu hóa
     */
    @Transactional
    public void deactivatePatient(String username) {
        Patient patient = findByUsername(username);
        patient.setActive(false); 
        patientRepository.save(patient);
        
        // Tự động hủy các lịch hẹn chưa diễn ra
        List<Appointment> scheduledAppointments = appointmentRepository.findByPatientId(patient.getId());
        
        for (Appointment app : scheduledAppointments) {
            if (app.getStatus() == AppointmentStatus.SCHEDULED) {
                app.setStatus(AppointmentStatus.CANCELLED);
                appointmentRepository.save(app);
            }
        }
    }
}