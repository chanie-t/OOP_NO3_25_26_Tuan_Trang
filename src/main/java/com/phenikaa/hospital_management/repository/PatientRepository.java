package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Spring Data JPA sẽ tự động tạo câu query để tìm Patient theo username
    // SELECT * FROM patients WHERE username = ?
    Optional<Patient> findByUsername(String username);
}