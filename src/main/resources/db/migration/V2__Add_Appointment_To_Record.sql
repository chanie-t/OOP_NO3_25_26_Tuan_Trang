/* Thêm cột appointment_id vào bảng medical_records */
ALTER TABLE medical_records
ADD COLUMN appointment_id BIGINT;

/* (FOREIGN KEY) 
  và UNIQUE để đảm bảo mỗi lịch hẹn có 1 bệnh án (One-to-One)
*/
ALTER TABLE medical_records
ADD CONSTRAINT uk_appointment_id UNIQUE (appointment_id),
ADD CONSTRAINT fk_medical_record_to_appointment
    FOREIGN KEY (appointment_id) REFERENCES appointments(id);