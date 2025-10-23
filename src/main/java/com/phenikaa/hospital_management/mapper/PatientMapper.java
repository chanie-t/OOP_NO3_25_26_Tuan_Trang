package com.phenikaa.hospital_management.mapper;

import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import com.phenikaa.hospital_management.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") 
public interface PatientMapper {

    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "password", ignore = true) 
    @Mapping(target = "role", ignore = true) 
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "medicalRecords", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    Patient toEntity(UserRegistrationDTO dto);
}