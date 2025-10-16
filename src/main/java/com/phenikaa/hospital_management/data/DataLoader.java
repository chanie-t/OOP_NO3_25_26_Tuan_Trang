package com.phenikaa.hospital_management.data;

import com.phenikaa.hospital_management.model.Doctor;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.model.Role;
import com.phenikaa.hospital_management.model.User;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import com.phenikaa.hospital_management.repository.RoleRepository;
import com.phenikaa.hospital_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
        Role doctorRole = roleRepository.save(new Role("ROLE_DOCTOR"));
        Role patientRole = roleRepository.save(new Role("ROLE_PATIENT"));

        // Tạo Admin User
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        // Sửa ở đây:
        adminUser.setRoles(Collections.singleton(adminRole));
        userRepository.save(adminUser);

        // Tạo Doctor User và đối tượng Doctor tương ứng
        User doctorUser = new User();
        doctorUser.setUsername("doctor.strange");
        doctorUser.setPassword(passwordEncoder.encode("doctor123"));
        // Sửa ở đây:
        doctorUser.setRoles(Collections.singleton(doctorRole));
        userRepository.save(doctorUser);

        Doctor doctor = new Doctor();
        doctor.setFullName("Stephen Strange");
        doctor.setSpecialty("Ngoại thần kinh");
        doctor.setUser(doctorUser);
        doctorRepository.save(doctor);

        // Tạo Patient User và đối tượng Patient tương ứng
        User patientUser = new User();
        patientUser.setUsername("patient.doe");
        patientUser.setPassword(passwordEncoder.encode("patient123"));
        // Sửa ở đây:
        patientUser.setRoles(Collections.singleton(patientRole));
        userRepository.save(patientUser);

        Patient patient = new Patient();
        patient.setFullName("Minh Tuan");
        patient.setAddress("17 Hà Đông, Hà Nội");
        patient.setPhoneNumber("0987654321");
        patient.setUser(patientUser);
        patientRepository.save(patient);

        System.out.println("==========================================");
        System.out.println("              ĐÃ TẠO DỮ LIỆU              ");
        System.out.println("==========================================");
    }
}