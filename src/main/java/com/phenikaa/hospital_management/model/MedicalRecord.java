package com.phenikaa.hospital_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT") // Dùng TEXT cho các chuỗi dài
    private String diagnosis; // Chẩn đoán

    @Column(columnDefinition = "TEXT")
    private String prescription; // Đơn thuốc

    private LocalDateTime recordDate;

    // Mối quan hệ Một-tới-Một: Mỗi bệnh án tương ứng với một Lịch hẹn.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // Mối quan hệ Nhiều-tới-Một: Nhiều bệnh án có thể do một Bác sĩ tạo.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Mối quan hệ Nhiều-tới-Một: Nhiều bệnh án có thể thuộc về một Bệnh nhân.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}