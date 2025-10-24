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
        // 1. Tìm trong bảng Patient
        Optional<Patient> patientOpt = patientRepository.findByUsername(username);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            // Đọc Role từ CSDL (ví dụ: "PATIENT")
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + patient.getRole());
            
            // Trả về đối tượng UserDetails với kiểm tra 'isActive'
            return new User(
                patient.getUsername(), 
                patient.getPassword(), 
                patient.isActive(), // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                Collections.singleton(authority)
            );
        }

        // 2. Tìm trong bảng Doctor
        Optional<Doctor> doctorOpt = doctorRepository.findByUsername(username);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            // Đọc Role từ CSDL (ví dụ: "DOCTOR")
            
            // SỬA LỖI: Xóa bớt 1 dấu "" ở "ROLE_""
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + doctor.getRole()); 
            
            // Trả về đối tượng UserDetails với kiểm tra 'isActive'
            return new User(
                doctor.getUsername(), 
                doctor.getPassword(), 
                doctor.isActive(), // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                Collections.singleton(authority)
            );
        }

        // 3. Không tìm thấy
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}