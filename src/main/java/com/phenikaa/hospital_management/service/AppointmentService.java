package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.dto.AppointmentRequestDTO;
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
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Appointment> findAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment createAppointment(Long patientId, AppointmentRequestDTO requestDTO) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + requestDTO.getDoctorId()));

        // Kiểm tra double-booking cho bác sĩ
        Optional<Appointment> existing = appointmentRepository.findByDoctorIdAndAppointmentTime(doctor.getId(), requestDTO.getAppointmentTime());
        if (existing.isPresent() && existing.get().getStatus() != AppointmentStatus.CANCELLED) {
            // Nếu đã có lịch (và lịch đó không bị hủy) thì không cho đặt
            throw new IllegalStateException("Bác sĩ đã có lịch hẹn vào thời điểm này. Vui lòng chọn thời gian khác.");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(requestDTO.getAppointmentTime());
        appointment.setReason(requestDTO.getReason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);
    }

    /**
     * Bác sĩ đánh dấu lịch hẹn là đã hoàn thành
     */
    public Appointment completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Cannot complete an appointment that is not scheduled.");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        return appointmentRepository.save(appointment);
    }

    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED || appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel an appointment that is already completed or cancelled.");
        }
        
        final long MINIMUM_HOURS_BEFORE_CANCELLATION = 2;
        LocalDateTime earliestCancelTime = appointment.getAppointmentTime().minusHours(MINIMUM_HOURS_BEFORE_CANCELLATION);

        if (LocalDateTime.now().isAfter(earliestCancelTime)) {
            throw new IllegalStateException("Không thể hủy lịch hẹn trong vòng " + MINIMUM_HOURS_BEFORE_CANCELLATION + " giờ trước giờ khám.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }
}