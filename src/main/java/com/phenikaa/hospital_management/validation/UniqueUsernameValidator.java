package com.phenikaa.hospital_management.validation;

import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; // <-- THÊM DÒNG NÀY

@Component // <-- THÊM ANNOTATION NÀY
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty()) {
            return true; // @NotBlank sẽ xử lý
        }
        // Thêm kiểm tra null để phòng trường hợp inject thất bại (dù @Component nên khắc phục)
        if (patientRepository == null || doctorRepository == null) {
             System.err.println("CẢNH BÁO: Repositories chưa được inject vào UniqueUsernameValidator!");
             // Có thể coi là không hợp lệ nếu không kiểm tra được
             return false; 
        }
        // Hợp lệ nếu không tồn tại ở cả 2 bảng
        return patientRepository.findByUsername(username).isEmpty()
                && doctorRepository.findByUsername(username).isEmpty();
    }
}