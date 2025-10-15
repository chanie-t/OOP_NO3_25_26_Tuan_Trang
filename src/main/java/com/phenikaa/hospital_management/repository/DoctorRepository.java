package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Các phương thức CRUD cơ bản (findAll, findById, save, delete) đã có sẵn.
    // Có thể thêm các phương thức tìm kiếm riêng nếu cần, ví dụ:
    // List<Doctor> findBySpecialization(String specialization);
}