package com.phenikaa.hospital_management.validation;

import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty()) {
            return true;
        }
        // Kiểm tra nếu các repository được inject đúng cách
        if (patientRepository == null || doctorRepository == null) {
             System.err.println("CẢNH BÁO: Repositories chưa được inject vào UniqueUsernameValidator!");
             return false; 
        }
        // Kiểm tra username trong cả hai repository
        return patientRepository.findByUsername(username).isEmpty()
                && doctorRepository.findByUsername(username).isEmpty();
    }
}