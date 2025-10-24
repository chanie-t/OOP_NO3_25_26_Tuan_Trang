package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.dto.ProfileUpdateDTO;
import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.mapper.PatientMapper;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientMapper patientMapper; // Sử dụng MapStruct

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

        // 1. Kiểm tra Email có bị trùng không
        // (Chỉ kiểm tra nếu email đã thay đổi)
        if (!patient.getEmail().equals(profileDTO.getEmail())) {
            // Kiểm tra xem email mới có bị trùng không
            boolean emailExists = patientRepository.findByEmail(profileDTO.getEmail()).isPresent() ||
                                  doctorRepository.findByEmail(profileDTO.getEmail()).isPresent();
            if (emailExists) {
                // Ném lỗi này sẽ được Controller bắt và hiển thị ra form
                throw new IllegalStateException("Email " + profileDTO.getEmail() + " đã tồn tại.");
            }
            patient.setEmail(profileDTO.getEmail());
        }

        // 2. Cập nhật các trường khác
        patient.setFullName(profileDTO.getFullName());
        patient.setPhoneNumber(profileDTO.getPhoneNumber());
        patient.setDateOfBirth(profileDTO.getDateOfBirth());

        // 3. Lưu lại
        return patientRepository.save(patient);
    }
}