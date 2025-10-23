// package com.phenikaa.hospital_management.service;

// import com.phenikaa.hospital_management.model.Patient; // Giả định model của bạn là Patient
// import com.phenikaa.hospital_management.repository.PatientRepository; // Giả định repository của bạn là PatientRepository
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class PatientServiceImplTest {

//     @Mock
//     private PatientRepository patientRepository;

//     @InjectMocks
//     private PatientServiceImpl patientService; // Giả định service impl của bạn là PatientServiceImpl

//     @Test
//     void testGetAllPatients_ShouldReturnPatientList() {
//         // Arrange (Sắp đặt)
//         Patient patient1 = new Patient();
//         patient1.setId(1L);
//         patient1.setName("Nguyễn Văn A"); // Giả định thuộc tính là 'name'

//         Patient patient2 = new Patient();
//         patient2.setId(2L);
//         patient2.setName("Trần Thị B");

//         List<Patient> mockPatientList = Arrays.asList(patient1, patient2);
        
//         // "Dạy" cho mock repository phải làm gì
//         when(patientRepository.findAll()).thenReturn(mockPatientList);

//         // Act (Hành động)
//         List<Patient> result = patientService.getAllPatients(); // Giả định tên phương thức là getAllPatients

//         // Assert (Khẳng định)
//         assertNotNull(result);
//         assertEquals(2, result.size());
//         assertEquals("Nguyễn Văn A", result.get(0).getName());

//         // Verify (Xác minh)
//         verify(patientRepository, times(1)).findAll();
//     }
    
//     @Test
//     void testGetPatientById_WhenPatientExists_ShouldReturnPatient() {
//         // Arrange
//         Patient mockPatient = new Patient();
//         mockPatient.setId(1L);
//         mockPatient.setName("Nguyễn Văn A");
        
//         when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));

//         // Act
//         Optional<Patient> result = patientService.getPatientById(1L); // Giả định tên phương thức là getPatientById

//         // Assert
//         assertTrue(result.isPresent());
//         assertEquals("Nguyễn Văn A", result.get().getName());
        
//         // Verify
//         verify(patientRepository, times(1)).findById(1L);
//     }

//     @Test
//     void testGetPatientById_WhenPatientDoesNotExist_ShouldReturnEmptyOptional() {
//         // Arrange
//         when(patientRepository.findById(99L)).thenReturn(Optional.empty());

//         // Act
//         Optional<Patient> result = patientService.getPatientById(99L);

//         // Assert
//         assertFalse(result.isPresent());

//         // Verify
//         verify(patientRepository, times(1)).findById(99L);
//     }
// }