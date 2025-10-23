package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import com.phenikaa.hospital_management.mapper.PatientMapper;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientMapper patientMapper; // Sử dụng MapStruct

    public Patient registerNewPatient(UserRegistrationDTO registrationDTO) {
        // Chuyển DTO sang Entity bằng MapStruct
        Patient patient = patientMapper.toEntity(registrationDTO);

        // Xử lý các trường logic
        patient.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        patient.setRole("PATIENT");
        patient.setActive(true);
        
        return patientRepository.save(patient);
    }
}