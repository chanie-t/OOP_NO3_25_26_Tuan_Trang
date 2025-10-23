package com.phenikaa.hospital_management.config;

import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (doctorRepository.count() == 0) {
            log.info("Database trống, bắt đầu khởi tạo dữ liệu Bác sĩ mẫu...");

            // Bác sĩ 1 (DOCTOR)
            Doctor doctor1 = new Doctor();
            doctor1.setUsername("bs.tuan"); // Quay lại username cũ
            doctor1.setFullName("Bác sĩ Nguyễn Minh Tuấn");
            doctor1.setEmail("tuan.bs@hospital.com");
            doctor1.setPassword(passwordEncoder.encode("password"));
            doctor1.setRole("DOCTOR"); // Vai trò là DOCTOR
            doctor1.setDepartment("Khoa Nội");
            doctor1.setSpecialization("Tim mạch");
            doctorRepository.save(doctor1);

            // Bác sĩ 2 (DOCTOR)
            Doctor doctor2 = new Doctor();
            doctor2.setUsername("bs.trang");
            doctor2.setFullName("Bác sĩ Nguyễn Thùy Trang");
            doctor2.setEmail("trang.bs@hospital.com");
            doctor2.setPassword(passwordEncoder.encode("password"));
            doctor2.setRole("DOCTOR"); // Vai trò là DOCTOR
            doctor2.setDepartment("Khoa Ngoại");
            doctor2.setSpecialization("Phẫu thuật tổng quát");
            doctorRepository.save(doctor2);

            log.info("Đã tạo 2 Bác sĩ mẫu: bs.tuan và bs.trang (mật khẩu: password)");
        } else {
            log.info("Database đã có dữ liệu, bỏ qua bước khởi tạo.");
        }
    }
}