package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.AppointmentStatus;
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service // Đánh dấu đây là một Bean Service của Spring
public class AppointmentServiceImpl implements AppointmentService {

    // Tiêm (Inject) các Repository cần thiết vào Service
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Transactional // Đảm bảo tất cả các thao tác CSDL trong phương thức này là một giao dịch
    public Appointment bookAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime, String reason) {
        // --- Bắt đầu hiện thực hóa logic từ Sơ đồ Tuần tự ---

        // 1. Tìm Bác sĩ và Bệnh nhân
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ với ID: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + patientId));

        // 2. Kiểm tra xem lịch hẹn có bị trùng không
        boolean isSlotTaken = appointmentRepository.existsByDoctorIdAndAppointmentTime(doctorId, appointmentTime);
        if (isSlotTaken) {
            throw new RuntimeException("Lịch hẹn tại thời điểm này đã có người đặt. Vui lòng chọn thời gian khác.");
        }

        // 3. Tạo đối tượng Appointment mới
        Appointment newAppointment = new Appointment();
        newAppointment.setDoctor(doctor);
        newAppointment.setPatient(patient);
        newAppointment.setAppointmentTime(appointmentTime);
        newAppointment.setReason(reason);
        newAppointment.setStatus(AppointmentStatus.SCHEDULED); // Trạng thái ban đầu

        // 4. Lưu lịch hẹn vào CSDL
        return appointmentRepository.save(newAppointment);
    }
}