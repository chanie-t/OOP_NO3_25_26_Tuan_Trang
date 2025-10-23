package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Doctor registerNewDoctor(Doctor doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setRole("DOCTOR");
        return doctorRepository.save(doctor);
    }

    // Hàm này sẽ nhận các thông tin phân trang (trang số mấy, bao nhiêu mục, sắp xếp)
    public Page<Doctor> findAllDoctorsPaginated(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }
}