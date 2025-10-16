package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Tương tự, có thể thêm phương thức tìm bác sĩ bằng username
    Optional<Doctor> findByUsername(String username);
}