package com.phenikaa.hospital_management.repository;

import com.phenikaa.hospital_management.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Phương thức kiểm tra sự tồn tại của lịch hẹn cho một bác sĩ tại một thời điểm
    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);
}