package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Tìm tất cả lịch hẹn của một bệnh nhân cụ thể
    List<Appointment> findByPatientId(Long patientId);

    // Tìm tất cả lịch hẹn của một bác sĩ cụ thể
    List<Appointment> findByDoctorId(Long doctorId);

    // Tìm lịch hẹn của bác sĩ theo status
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);
}