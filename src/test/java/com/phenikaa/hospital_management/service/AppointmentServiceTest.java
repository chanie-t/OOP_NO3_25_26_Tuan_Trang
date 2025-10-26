package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.dto.AppointmentRequestDTO; // THÊM
import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.model.Appointment;
import com.phenikaa.hospital_management.model.AppointmentStatus;
import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.AppointmentRepository;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;

    private Patient mockPatient;
    private Doctor mockDoctor;
    private Appointment mockAppointment;
    private AppointmentRequestDTO mockRequestDTO; 

    @BeforeEach
    void setUp() {
        mockPatient = new Patient();
        mockPatient.setId(1L);
        mockPatient.setUsername("benhnhanA");

        mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        mockDoctor.setFullName("Bac Si B");

        mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        mockAppointment.setPatient(mockPatient);
        mockAppointment.setDoctor(mockDoctor);
        mockAppointment.setStatus(AppointmentStatus.SCHEDULED);
        
        mockRequestDTO = new AppointmentRequestDTO();
        mockRequestDTO.setDoctorId(1L);
        mockRequestDTO.setAppointmentTime(LocalDateTime.now().plusDays(1));
        mockRequestDTO.setReason("Dau dau");
    }

    @Test
    void testCreateAppointment_Success() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(mockDoctor));
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Appointment created = appointmentService.createAppointment(1L, mockRequestDTO);

        assertNotNull(created);
        assertEquals(AppointmentStatus.SCHEDULED, created.getStatus());
        assertEquals(mockPatient, created.getPatient());
        assertEquals("Dau dau", created.getReason()); // Kiểm tra lý do
        verify(appointmentRepository, times(1)).save(any(Appointment.class)); 
    }

    @Test
    void testCreateAppointment_PatientNotFound_ShouldThrowException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.createAppointment(1L, mockRequestDTO);
        });
        
        assertEquals("Patient not found with id: 1", exception.getMessage());
        verify(appointmentRepository, never()).save(any(Appointment.class)); 
    }

    @Test
    void testCancelAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Appointment cancelled = appointmentService.cancelAppointment(1L);

        assertNotNull(cancelled);
        assertEquals(AppointmentStatus.CANCELLED, cancelled.getStatus());
        verify(appointmentRepository, times(1)).save(mockAppointment);
    }

    @Test
    void testCancelAppointment_AlreadyCompleted_ShouldThrowException() {
        mockAppointment.setStatus(AppointmentStatus.COMPLETED);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            appointmentService.cancelAppointment(1L);
        });
        
        assertEquals("Cannot cancel an appointment that is already completed or cancelled.", exception.getMessage());
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}