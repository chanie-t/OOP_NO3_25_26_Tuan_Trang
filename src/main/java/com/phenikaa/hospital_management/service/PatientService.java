package com.phenikaa.hospital_management.service;

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

    public Patient registerNewPatient(Patient patient) {
        // Mã hóa mật khẩu
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        // Gán vai trò
        patient.setRole("PATIENT");
        return patientRepository.save(patient);
    }
}