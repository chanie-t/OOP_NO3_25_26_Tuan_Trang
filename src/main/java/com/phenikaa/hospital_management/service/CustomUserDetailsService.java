// File: src/main/java/com/phenikaa/hospital_management/service/CustomUserDetailsService.java
package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm trong bảng Patient trước
        Optional<Patient> patientOpt = patientRepository.findByUsername(username);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            // Nếu tìm thấy, tạo đối tượng UserDetails với vai trò ROLE_PATIENT
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_PATIENT");
            return new User(patient.getUsername(), patient.getPassword(), Collections.singleton(authority));
        }

        // 2. Nếu không có Patient, tìm trong bảng Doctor
        Optional<Doctor> doctorOpt = doctorRepository.findByUsername(username);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            // Nếu tìm thấy, tạo đối tượng UserDetails với vai trò ROLE_DOCTOR
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_DOCTOR");
            return new User(doctor.getUsername(), doctor.getPassword(), Collections.singleton(authority));
        }

        // 3. Nếu không tìm thấy ở cả 2 bảng, ném ra lỗi
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}