package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.dto.AppointmentRequestDTO;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Đánh dấu đây là một Controller xử lý các yêu cầu API
@RequestMapping("/api/appointments") // Tất cả các URL trong controller này sẽ bắt đầu bằng /api/appointments
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService; // Tiêm Service vào Controller

    /**
     * Endpoint để đặt một lịch hẹn mới.
     * Lắng nghe yêu cầu POST tại URL: /api/appointments/book
     */
    // Trong file AppointmentController.java
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequestDTO request) { // Đổi sang ResponseEntity<?>
        try {
            Appointment newAppointment = appointmentService.bookAppointment(
                    request.getDoctorId(),
                    request.getPatientId(),
                    request.getAppointmentTime(),
                    request.getReason()
            );
            return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Trả về chính thông báo lỗi để Postman có thể hiển thị
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}