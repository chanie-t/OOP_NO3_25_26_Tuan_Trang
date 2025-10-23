-- Tạo bảng patients
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- Các trường từ User.java
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255),
    date_of_birth DATE,
    role VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
);

-- Tạo bảng doctors
CREATE TABLE doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- Các trường từ User
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255),
    date_of_birth DATE,
    role VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    -- Các trường riêng của Doctor
    department VARCHAR(255),
    specialization VARCHAR(255)
);

-- Tạo bảng appointments
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_time DATETIME,
    status VARCHAR(255),
    reason TEXT,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- Tạo bảng medical_records
CREATE TABLE medical_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diagnosis TEXT,
    prescription TEXT,
    record_date DATETIME,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);