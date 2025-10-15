// package com.phenikaa.hospital_management.controller;

// import com.phenikaa.hospital_management.model.Patient;
// import com.phenikaa.hospital_management.service.PatientService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/patients") // Tiền tố chung cho tất cả các API trong lớp này
// public class PatientController {

//     @Autowired
//     private PatientService patientService;

//     /**
//      * API để lấy danh sách tất cả bệnh nhân.
//      * URL: GET http://localhost:8080/api/patients
//      */
//     @GetMapping
//     public List<Patient> getAllPatients() {
//         return patientService.getAllPatients();
//     }

//     /**
//      * API để tạo một bệnh nhân mới.
//      * URL: POST http://localhost:8080/api/patients
//      * Body của request sẽ chứa thông tin bệnh nhân dưới dạng JSON.
//      */
//     @PostMapping
//     public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
//         Patient savedPatient = patientService.createPatient(patient);
//         return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
//     }
// }