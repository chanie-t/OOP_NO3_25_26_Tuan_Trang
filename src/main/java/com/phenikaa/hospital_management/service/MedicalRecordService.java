package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.AppointmentStatus;
import com.phenikaa.hospital_management.model.MedicalRecord;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    // Bác sĩ tạo bệnh án dựa trên một cuộc hẹn đã hoàn thành
    @Transactional
    public MedicalRecord createMedicalRecord(Long appointmentId, String diagnosis, String prescription) {
        // 1. tìm cuộc hẹn tương ứng
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId));

        // 2. chỉ tạo được bệnh án cho lịch hẹn đã hoàn thành
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Không thể tạo bệnh án cho lịch hẹn chưa hoàn thành.");
        }

        // 3. ktra bệnh án đã tồn tại cho cuộc hẹn chưa
        if (appointment.getMedicalRecord() != null) {
            throw new IllegalStateException("Bệnh án cho lịch hẹn này đã tồn tại. Bạn chỉ có thể sửa.");
        }

        // 4. tạo MedicalRecord
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDiagnosis(diagnosis);
        medicalRecord.setPrescription(prescription);
        medicalRecord.setRecordDate(LocalDateTime.now());
        
        // 5. liên kết với patient và doctor từ cuộc hẹn
        medicalRecord.setPatient(appointment.getPatient());
        medicalRecord.setDoctor(appointment.getDoctor());
        
        // 6. LIÊN KẾT BỆNH ÁN VỚI LỊCH HẸN (QUAN TRỌNG)
        medicalRecord.setAppointment(appointment);

        // 7. lưu vào csdl và trả về
        return medicalRecordRepository.save(medicalRecord);
    }

    /**
     * Bác sĩ cập nhật bệnh án
     * @param recordId ID của bệnh án
     * @param diagnosis Chẩn đoán mới
     * @param prescription Đơn thuốc mới
     * @return Bệnh án đã được cập nhật
     */
    public MedicalRecord updateMedicalRecord(Long recordId, String diagnosis, String prescription) {
        // 1. tìm bệnh án
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + recordId));

        // 2. cập nhật thông tin
        medicalRecord.setDiagnosis(diagnosis);
        medicalRecord.setPrescription(prescription);
        medicalRecord.setRecordDate(LocalDateTime.now()); // cập nhật ngày sửa

        // 3. Lưu lại
        return medicalRecordRepository.save(medicalRecord);
    }

    /**
     * bsi xóa 1 bệnh án
     * @param recordId ID bệnh án
     */
    @Transactional
    public void deleteMedicalRecord(Long recordId) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + recordId));

        // khi xóa bệnh án, ngắt liên kết appointment
        Appointment appointment = medicalRecord.getAppointment();
        if (appointment != null) {
            appointment.setMedicalRecord(null);
            appointmentRepository.save(appointment); // Cập nhật lại appointment
        }
        
        medicalRecordRepository.delete(medicalRecord);
    }
}