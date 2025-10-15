// package com.phenikaa.hospital_management.controller;

// import com.phenikaa.hospital_management.model.Doctor;
// import com.phenikaa.hospital_management.service.DoctorService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/doctors")
// public class DoctorController {

//     @Autowired
//     private DoctorService doctorService;

//     /**
//      * API để lấy danh sách tất cả bác sĩ.
//      * URL: GET http://localhost:8080/api/doctors
//      */
//     @GetMapping
//     public List<Doctor> getAllDoctors() {
//         return doctorService.getAllDoctors();
//     }

//     /**
//      * API để lấy thông tin một bác sĩ theo ID.
//      * URL: GET http://localhost:8080/api/doctors/{id}
//      */
//     @GetMapping("/{id}")
//     public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
//         return doctorService.getDoctorById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     /**
//      * API để tạo một bác sĩ mới.
//      * URL: POST http://localhost:8080/api/doctors
//      */
//     @PostMapping
//     public Doctor createDoctor(@RequestBody Doctor doctor) {
//         return doctorService.createDoctor(doctor);
//     }

//     /**
//      * API để cập nhật thông tin một bác sĩ.
//      * URL: PUT http://localhost:8080/api/doctors/{id}
//      */
//     @PutMapping("/{id}")
//     public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
//         try {
//             Doctor updatedDoctor = doctorService.updateDoctor(id, doctorDetails);
//             return ResponseEntity.ok(updatedDoctor);
//         } catch (RuntimeException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     /**
//      * API để xóa một bác sĩ.
//      * URL: DELETE http://localhost:8080/api/doctors/{id}
//      */
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
//         doctorService.deleteDoctor(id);
//         return ResponseEntity.noContent().build();
//     }
// }