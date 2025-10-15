package com.phenikaa.hospital_management.repository;
import com.phenikaa.hospital_management.model.Role;
import com.phenikaa.hospital_management.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Thêm phương thức này vào
    Optional<Role> findByName(RoleType name);
}