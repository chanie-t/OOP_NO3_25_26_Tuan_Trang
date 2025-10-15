package com.phenikaa.hospital_management;

import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.model.Role;
import com.phenikaa.hospital_management.model.RoleType;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import com.phenikaa.hospital_management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional // Thêm @Transactional để đảm bảo tất cả hoạt động trong một giao dịch
    public void run(String... args) throws Exception {
        // --- Tải hoặc Tạo các Vai trò ---
        Role doctorRole = findOrCreateRole(RoleType.ROLE_DOCTOR);
        Role patientRole = findOrCreateRole(RoleType.ROLE_PATIENT);

        // Chỉ thêm dữ liệu người dùng mẫu nếu chưa có bác sĩ nào
        if (doctorRepository.count() == 0) {
            System.out.println("Creating sample users...");

            // Tạo 1 Bác sĩ mẫu
            Doctor doctor = new Doctor();
            doctor.setUsername("dr.strange");
            doctor.setPassword("password123"); // Trong thực tế cần mã hóa mật khẩu
            doctor.setFullName("Stephen Strange");
            doctor.setEmail("strange@marvel.com");
            doctor.setSpecialization("Neurosurgery");
            doctor.setDepartment("Magic");
            doctor.setRoles(Set.of(doctorRole));
            doctorRepository.save(doctor);

            // Tạo 1 Bệnh nhân mẫu
            Patient patient = new Patient();
            patient.setUsername("tony.stark");
            patient.setPassword("password123");
            patient.setFullName("Tony Stark");
            patient.setEmail("tony@stark.com");
            patient.setDateOfBirth(LocalDate.of(1970, 5, 29));
            patient.setRoles(Set.of(patientRole));
            patientRepository.save(patient);

            System.out.println("Sample users created. Doctor ID: 1, Patient ID: 2");
        }
    }

    // Phương thức trợ giúp để tìm hoặc tạo Role
    private Role findOrCreateRole(RoleType roleType) {
        Optional<Role> roleOpt = roleRepository.findByName(roleType);
        if (roleOpt.isPresent()) {
            return roleOpt.get();
        } else {
            return roleRepository.save(new Role(null, roleType));
        }
    }
}