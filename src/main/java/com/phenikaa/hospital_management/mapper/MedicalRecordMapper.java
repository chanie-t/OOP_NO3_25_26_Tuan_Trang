package com.phenikaa.hospital_management.mapper;

import com.phenikaa.hospital_management.dto.MedicalRecordDTO;
import com.phenikaa.hospital_management.model.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "doctor.fullName", target = "doctorName")
    @Mapping(source = "patient.fullName", target = "patientName")
    @Mapping(source = "diagnosis", target = "diagnosisSummary", qualifiedByName = "abbreviateDiagnosis")
    MedicalRecordDTO toDTO(MedicalRecord medicalRecord);

    List<MedicalRecordDTO> toDTOList(List<MedicalRecord> medicalRecords);

    // hàm custom này được MapStruct tự động sử dụng
    @Named("abbreviateDiagnosis")
    default String abbreviateDiagnosis(String diagnosis) {
        if (diagnosis == null || diagnosis.length() <= 100) {
            return diagnosis;
        }
        return diagnosis.substring(0, 100) + "...";
    }
}