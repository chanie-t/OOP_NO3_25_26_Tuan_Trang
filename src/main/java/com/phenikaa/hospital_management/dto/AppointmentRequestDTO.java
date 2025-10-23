package com.phenikaa.hospital_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequestDTO {

    @NotNull
    private Long doctorId;

    @NotNull(message = "Thời gian hẹn không được để trống")
    @Future(message = "Thời gian hẹn phải ở trong tương lai")
    private LocalDateTime appointmentTime;

    @NotBlank(message = "Lý do khám không được để trống")
    private String reason;
}