package com.phenikaa.hospital_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {
    private Long doctorId;
    private Long patientId;

    // Thêm annotation này vào
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentTime;

    private String reason;
}