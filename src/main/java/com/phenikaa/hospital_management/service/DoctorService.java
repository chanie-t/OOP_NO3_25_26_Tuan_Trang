package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.dto.ProfileUpdateDTO;
import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Doctor registerNewDoctor(Doctor doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setRole("DOCTOR");
        return doctorRepository.save(doctor);
    }

    public Page<Doctor> findAllDoctorsPaginated(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    /**
     * Tìm bác sĩ bằng username
     */
    public Doctor findByUsername(String username) {
        return doctorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with username: " + username));
    }

    /**
     * Cập nhật thông tin hồ sơ bác sĩ
     */
    @Transactional
    public Doctor updateProfile(String username, ProfileUpdateDTO profileDTO) {
        Doctor doctor = findByUsername(username);

        // Kiểm tra Email
        if (!doctor.getEmail().equals(profileDTO.getEmail())) {
            boolean emailExists = patientRepository.findByEmail(profileDTO.getEmail()).isPresent() ||
                                  doctorRepository.findByEmail(profileDTO.getEmail()).isPresent();
            if (emailExists) {
                throw new IllegalStateException("Email " + profileDTO.getEmail() + " đã tồn tại.");
            }
            doctor.setEmail(profileDTO.getEmail());
        }

        doctor.setFullName(profileDTO.getFullName());
        doctor.setPhoneNumber(profileDTO.getPhoneNumber());
        doctor.setDateOfBirth(profileDTO.getDateOfBirth());
        
        return doctorRepository.save(doctor);
    }
}