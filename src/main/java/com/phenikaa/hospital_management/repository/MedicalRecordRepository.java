package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    // Tìm tất cả bệnh án của một bệnh nhân
    List<MedicalRecord> findByPatientId(Long patientId);

    // THÊM DÒNG NÀY: Tìm tất cả bệnh án của một bác sĩ
    List<MedicalRecord> findByDoctorId(Long doctorId);
}