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
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    private String reason;

    // Mối quan hệ Nhiều-tới-Một: Nhiều lịch hẹn có thể thuộc về một Bác sĩ
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: chỉ tải khi cần thiết
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Mối quan hệ Nhiều-tới-Một: Nhiều lịch hẹn có thể thuộc về một Bệnh nhân
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}