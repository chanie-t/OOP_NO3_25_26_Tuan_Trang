package com.phenikaa.hospital_management.mapper;

import com.phenikaa.hospital_management.dto.AppointmentDTO;
import com.phenikaa.hospital_management.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    
    // dạy MapStruct lấy source và map vào target
    @Mapping(source = "doctor.fullName", target = "doctorName")
    AppointmentDTO toDTO(Appointment appointment);
    
    // tự động map một list
    List<AppointmentDTO> toDTOList(List<Appointment> appointments);
}