// package com.phenikaa.hospital_management.service;

// import com.phenikaa.hospital_management.model.Patient;
// import com.phenikaa.hospital_management.repository.PatientRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class PatientService {

//     // Inject PatientRepository để sử dụng các phương thức của nó
//     @Autowired
//     private PatientRepository patientRepository;

//     /**
//      * Lấy danh sách tất cả bệnh nhân
//      * @return List<Patient>
//      */
//     public List<Patient> getAllPatients() {
//         return patientRepository.findAll();
//     }

//     /**
//      * Thêm một bệnh nhân mới vào csdl
//      * @param patient Đối tượng Patient chứa thông tin cần lưu
//      * @return Patient đã được lưu (bao gồm cả ID)
//      */
//     public Patient createPatient(Patient patient) {
//         // (Trong tương lai, bạn có thể thêm các logic kiểm tra dữ liệu ở đây)
//         // Ví dụ: kiểm tra xem thông tin bệnh nhân có hợp lệ không trước khi lưu.
//         return patientRepository.save(patient);
//     }

//     // (Bạn sẽ thêm các phương thức khác như updatePatient, deletePatient, getPatientById ở đây)
// }