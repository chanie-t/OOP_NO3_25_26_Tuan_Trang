package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // tìm bệnh nhân theo tên đăng nhập hoặc email
    Optional<Patient> findByUsername(String username);
    Optional<Patient> findByEmail(String email);
}