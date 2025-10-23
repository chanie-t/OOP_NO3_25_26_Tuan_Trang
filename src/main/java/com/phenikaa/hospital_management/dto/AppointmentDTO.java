package com.phenikaa.hospital_management.dto;

import com.phenikaa.hospital_management.model.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// chỉ chứa thông tin mà patient-dashboard cần
@Getter
@Setter
public class AppointmentDTO {
    private Long id;
    private String doctorName; // chỉ cần tên, không cần cả đối tượng doctor
    private LocalDateTime appointmentTime;
    private String reason;
    private AppointmentStatus status;
}