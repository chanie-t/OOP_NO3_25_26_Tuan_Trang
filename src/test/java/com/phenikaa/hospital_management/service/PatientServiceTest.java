package com.phenikaa.hospital_management.service;

import com.phenikaa.hospital_management.dto.ProfileUpdateDTO;
import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import com.phenikaa.hospital_management.exception.ResourceNotFoundException;
import com.phenikaa.hospital_management.mapper.PatientMapper;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private Patient mockPatient;
    private UserRegistrationDTO registrationDTO;
    private ProfileUpdateDTO profileDTO;

    @BeforeEach
    void setUp() {
        // --- Mock đối tượng Patient ---
        mockPatient = new Patient();
        mockPatient.setId(1L);
        mockPatient.setUsername("testuser");
        mockPatient.setEmail("test@example.com");
        mockPatient.setFullName("Test User");
        mockPatient.setActive(true);

        // --- Mock DTO đăng ký ---
        registrationDTO = new UserRegistrationDTO();
        registrationDTO.setUsername("newuser");
        registrationDTO.setFullName("New User");
        registrationDTO.setEmail("new@example.com");
        registrationDTO.setPassword("password123");

        // --- Mock DTO cập nhật ---
        profileDTO = new ProfileUpdateDTO();
        profileDTO.setFullName("Test User Updated");
        profileDTO.setEmail("updated@example.com");
        profileDTO.setPhoneNumber("0987654321");
        profileDTO.setDateOfBirth(LocalDate.of(2000, 1, 1));
    }

    @Test
    void testRegisterNewPatient_Success() {
        Patient mappedPatient = new Patient();
        mappedPatient.setUsername(registrationDTO.getUsername());

        when(patientMapper.toEntity(any(UserRegistrationDTO.class))).thenReturn(mappedPatient);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient savedPatient = patientService.registerNewPatient(registrationDTO);

        assertNotNull(savedPatient);
        assertEquals("newuser", savedPatient.getUsername());
        assertEquals("hashedPassword", savedPatient.getPassword());
        assertEquals("PATIENT", savedPatient.getRole());
        assertTrue(savedPatient.isActive());
        
        verify(patientMapper, times(1)).toEntity(registrationDTO);
        verify(passwordEncoder, times(1)).encode("password123");
        verify(patientRepository, times(1)).save(mappedPatient);
    }

    @Test
    void testFindByUsername_Success() {
        // Arrange
        when(patientRepository.findByUsername("testuser")).thenReturn(Optional.of(mockPatient));

        // Act
        Patient foundPatient = patientService.findByUsername("testuser");

        // Assert
        assertNotNull(foundPatient);
        assertEquals("testuser", foundPatient.getUsername());
    }

    @Test
    void testFindByUsername_NotFound_ShouldThrowException() {
        // Arrange
        when(patientRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            patientService.findByUsername("nonexistent");
        });

        assertEquals("Patient not found with username: nonexistent", exception.getMessage());
    }

    @Test
    void testUpdateProfile_Success_EmailChanged() {
        // Arrange
        when(patientRepository.findByUsername("testuser")).thenReturn(Optional.of(mockPatient));
        // giả lập email mới chưa tồn tại
        when(patientRepository.findByEmail("updated@example.com")).thenReturn(Optional.empty());
        when(doctorRepository.findByEmail("updated@example.com")).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Patient updatedPatient = patientService.updateProfile("testuser", profileDTO);

        // Assert
        assertNotNull(updatedPatient);
        assertEquals("Test User Updated", updatedPatient.getFullName());
        assertEquals("updated@example.com", updatedPatient.getEmail());
        assertEquals("0987654321", updatedPatient.getPhoneNumber());
        assertEquals(LocalDate.of(2000, 1, 1), updatedPatient.getDateOfBirth());
        
        // Verify
        verify(patientRepository, times(1)).save(mockPatient);
    }

    @Test
    void testUpdateProfile_EmailAlreadyExists_ShouldThrowException() {
        // Arrange
        when(patientRepository.findByUsername("testuser")).thenReturn(Optional.of(mockPatient));
        // giả lập email mới đã bị bác sĩ sử dụng
        when(patientRepository.findByEmail("updated@example.com")).thenReturn(Optional.empty());
        when(doctorRepository.findByEmail("updated@example.com")).thenReturn(Optional.of(new com.phenikaa.hospital_management.model.Doctor()));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            patientService.updateProfile("testuser", profileDTO);
        });

        assertEquals("Email updated@example.com đã tồn tại.", exception.getMessage());
        
        // Verify
        verify(patientRepository, never()).save(any(Patient.class)); // Đảm bảo không lưu
    }
    
    @Test
    void testUpdateProfile_Success_EmailNotChanged() {
        // Arrange
        // đặt email DTO giống email gốc
        profileDTO.setEmail("test@example.com"); 
        
        when(patientRepository.findByUsername("testuser")).thenReturn(Optional.of(mockPatient));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Patient updatedPatient = patientService.updateProfile("testuser", profileDTO);

        // Assert
        assertEquals("Test User Updated", updatedPatient.getFullName());
        assertEquals("test@example.com", updatedPatient.getEmail()); // Email không đổi

        // Verify
        // Xác minh rằng hàm tìm email (patientRepo.findByEmail, doctorRepo.findByEmail) 0 được gọi
        verify(patientRepository, never()).findByEmail(anyString());
        verify(doctorRepository, never()).findByEmail(anyString());
        verify(patientRepository, times(1)).save(mockPatient);
    }

    @Test
    void testDeactivatePatient_Success() {
        // Arrange
        when(patientRepository.findByUsername("testuser")).thenReturn(Optional.of(mockPatient));
        
        // Act
        patientService.deactivatePatient("testuser");

        // Assert
        assertFalse(mockPatient.isActive()); // Kiểm tra đối tượng mock đã bị thay đổi
        
        // Verify
        verify(patientRepository, times(1)).save(mockPatient); // Đảm bảo đã gọi save với trạng thái mới
    }
}