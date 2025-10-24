package com.phenikaa.hospital_management.validation;

import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; // <-- THÊM DÒNG NÀY

@Component // <-- THÊM ANNOTATION NÀY
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true;
        }
         // Thêm kiểm tra null
        if (patientRepository == null || doctorRepository == null) {
             System.err.println("CẢNH BÁO: Repositories chưa được inject vào UniqueEmailValidator!");
             return false;
        }
        // Kiểm tra email trong hai repository
        return patientRepository.findByEmail(email).isEmpty()
                && doctorRepository.findByEmail(email).isEmpty();
    }
}