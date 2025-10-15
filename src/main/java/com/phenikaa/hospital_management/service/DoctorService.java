// package com.phenikaa.hospital_management.service;

// import com.phenikaa.hospital_management.model.Doctor;
// import com.phenikaa.hospital_management.repository.DoctorRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class DoctorService {

//     @Autowired
//     private DoctorRepository doctorRepository;

//     /**
//      * Lấy danh sách tất cả bác sĩ.
//      */
//     public List<Doctor> getAllDoctors() {
//         return doctorRepository.findAll();
//     }

//     /**
//      * Tìm bác sĩ theo ID.
//      */
//     public Optional<Doctor> getDoctorById(Long id) {
//         return doctorRepository.findById(id);
//     }

//     /**
//      * Thêm một bác sĩ mới.
//      */
//     public Doctor createDoctor(Doctor doctor) {
//         // Có thể thêm logic kiểm tra thông tin bác sĩ trước khi lưu
//         return doctorRepository.save(doctor);
//     }

//     /**
//      * Cập nhật thông tin bác sĩ.
//      */
//     public Doctor updateDoctor(Long id, Doctor doctorDetails) {
//         // Tìm bác sĩ hiện tại
//         Doctor doctor = doctorRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ với ID: " + id));

//         // Cập nhật thông tin
//         doctor.setFullName(doctorDetails.getFullName());
//         doctor.setDepartment(doctorDetails.getDepartment());
//         doctor.setPhoneNumber(doctorDetails.getPhoneNumber());

//         // Lưu lại vào CSDL
//         return doctorRepository.save(doctor);
//     }

//     /**
//      * Xóa một bác sĩ.
//      */
//     public void deleteDoctor(Long id) {
//         doctorRepository.deleteById(id);
//     }
// }