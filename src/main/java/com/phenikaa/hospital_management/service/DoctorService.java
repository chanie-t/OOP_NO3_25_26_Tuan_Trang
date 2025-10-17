package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Tạo phương thức để đăng ký hoặc thêm mới bác sĩ
    public Doctor registerNewDoctor(Doctor doctor) {
        // Mã hóa mật khẩu trước khi lưu
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        // Gán vai trò
        doctor.setRole("DOCTOR");
        return doctorRepository.save(doctor);
    }
}