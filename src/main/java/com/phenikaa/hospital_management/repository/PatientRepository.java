package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Các phương thức CRUD cơ bản đã có sẵn.
}