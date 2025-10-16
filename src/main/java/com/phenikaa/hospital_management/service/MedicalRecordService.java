package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.AppointmentStatus;
import com.phenikaa.hospital_management.model.MedicalRecord;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * PHƯƠNG THỨC HOẠT ĐỘNG CHÍNH CỦA ỨNG DỤNG (Yêu cầu 5)
     * Bác sĩ tạo bệnh án dựa trên một cuộc hẹn đã hoàn thành.
     */
    public MedicalRecord createMedicalRecord(Long appointmentId, String diagnosis, String prescription) {
        // 1. Tìm cuộc hẹn tương ứng
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId));

        // 2. Kiểm tra logic nghiệp vụ: Chỉ tạo được bệnh án cho lịch hẹn đã HOÀN THÀNH
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot create medical record for an appointment that is not completed.");
        }

        // 3. Tạo đối tượng MedicalRecord mới
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDiagnosis(diagnosis);
        medicalRecord.setPrescription(prescription);
        medicalRecord.setRecordDate(LocalDateTime.now());
        
        // 4. Liên kết bản ghi với Patient và Doctor từ cuộc hẹn
        medicalRecord.setPatient(appointment.getPatient());
        medicalRecord.setDoctor(appointment.getDoctor());

        // 5. Lưu vào CSDL
        return medicalRecordRepository.save(medicalRecord);
    }
}