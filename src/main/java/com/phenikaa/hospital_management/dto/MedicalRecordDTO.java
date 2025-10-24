package com.phenikaa.hospital_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// chỉ chứa thông tin mà patient-dashboard.html cần
@Getter
@Setter
public class MedicalRecordDTO {
    private Long id;
    private String doctorName;
    private String patientName;
    private LocalDateTime recordDate;
    private String diagnosisSummary;
}