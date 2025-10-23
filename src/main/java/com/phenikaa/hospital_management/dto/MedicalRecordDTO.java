package com.phenikaa.hospital_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// chỉ chứa thông tin mà patient-dashboard.html cần
@Getter
@Setter
public class MedicalRecordDTO {
    private Long id;
    private String doctorName; // Chỉ cần tên
    private LocalDateTime recordDate;
    private String diagnosisSummary; // Chỉ cần tóm tắt
}