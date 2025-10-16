package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.AppointmentStatus;
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    // Tiêm các repository cần thiết vào service
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Lấy danh sách lịch hẹn của một bệnh nhân
     */
    public List<Appointment> findAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    /**
     * Tạo một lịch hẹn mới
     */
    public Appointment createAppointment(Long patientId, Long doctorId, LocalDateTime appointmentTime, String reason) {
        // Tìm Patient và Doctor, nếu không có sẽ ném ra lỗi
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setReason(reason);
        appointment.setStatus(AppointmentStatus.SCHEDULED); // Trạng thái ban đầu

        return appointmentRepository.save(appointment);
    }

    /**
     * Hủy một lịch hẹn
     */
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId));

        // Kiểm tra logic: Không thể hủy lịch đã hoàn thành hoặc đã bị hủy
        if (appointment.getStatus() == AppointmentStatus.COMPLETED || appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel an appointment that is already completed or cancelled.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }
}