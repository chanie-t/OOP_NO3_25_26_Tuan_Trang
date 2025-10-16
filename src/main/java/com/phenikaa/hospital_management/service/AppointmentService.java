package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.model.Appointment;
import java.time.LocalDateTime;

public interface AppointmentService {

    /**
     * Chức năng đặt một lịch hẹn
     * @param doctorId ID bác sĩ
     * @param patientId ID bệnh nhân
     * @param appointmentTime Thời gian hẹn
     * @param reason Lý do khám
     * @return Đối tượng Appointment đã được tạo
     * @throws RuntimeException nếu bác sĩ/bệnh nhân không tồn tại hoặc lịch bị trùng
     */
    Appointment bookAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime, String reason);

}