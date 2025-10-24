# Đồ án OOP: Hệ Thống Quản Lý Bệnh Viện

Đây là đồ án môn học Lập trình Hướng đối tượng (OOP) - CSE703029, được thực hiện bởi Nhóm 04.

## 1. Thông tin Nhóm

| STT | Họ và Tên | MSSV | Email |
| :--- | :--- | :--- | :--- |
| 1 | Nguyễn Minh Tuấn | 22010478 | 22010478@st.phenikaa-uni.edu.vn |
| 2 | Nguyễn Thùy Trang | 23010487 | 23010487@st.phenikaa-uni.edu.vn |

---

## 2. Thông tin bắt buộc (Yêu cầu 9)

* **Link Github Repository:** `[VUI LÒNG DÁN LINK REPO CỦA BẠN VÀO ĐÂY]`
* **Link Demo Youtube:** `[VUI LÒNG DÁN LINK YOUTUBE DEMO CỦA BẠN VÀO ĐÂY]`
* **Link Deploy (chạy toàn cầu):** `[VUI LÒNG DÁN LINK DEPLOY CỦA BẠN VÀO ĐÂY (VÍ DỤ: RENDER, AWS, AZURE...)]`

---

## 3. Mô tả Dự án

Dự án xây dựng một ứng dụng Web Quản lý Bệnh viện bằng **Java Spring Boot** và **Cloud MySQL Database (Aiven)**, tuân thủ mô hình **MVC (Model-View-Controller)**.

Hệ thống cho phép hai vai trò người dùng (`PATIENT` và `DOCTOR`) tương tác với nhau thông qua một quy trình nghiệp vụ cốt lõi:

1.  **Bệnh nhân (Patient):** Có thể đăng ký tài khoản, đăng nhập, xem danh sách bác sĩ, đặt lịch hẹn với một bác sĩ cụ thể.
2.  **Bác sĩ (Doctor):** Có thể đăng nhập, xem các lịch hẹn mà bệnh nhân đã đặt với mình.
3.  **Hoàn thành khám:** Bác sĩ đánh dấu lịch hẹn là "Hoàn thành" sau khi khám xong.
4.  **Tạo Bệnh án (Medical Record):** Dựa trên lịch hẹn đã hoàn thành, bác sĩ có thể tạo, sửa, hoặc xóa bệnh án (bao gồm chẩn đoán và đơn thuốc) cho bệnh nhân đó.
5.  **Xem kết quả:** Bệnh nhân có thể xem lại lịch sử các cuộc hẹn và chi tiết các bệnh án của mình trên trang Dashboard.

---

## 4. Công nghệ sử dụng

* **Backend:** Java 17, Spring Boot 3.3.0
* **Frontend:** Thymeleaf, Bootstrap 5.3
* **Database:** MySQL (Kết nối tới Aiven Cloud)
* **Data & ORM:** Spring Data JPA, Hibernate
* **Bảo mật:** Spring Security (Xác thực, phân quyền, Remember-me)
* **Migration:** Flyway (Quản lý phiên bản CSDL)
* **Utilities:** MapStruct (DTO mapping), Lombok, Spring Boot Validation
* **Testing:** JUnit 5, Mockito

---

## 5. Sơ đồ Yêu cầu (UML)

### 5.1 UML Class Diagram

(Mô tả 4 đối tượng chính: Patient, Doctor, Appointment, và MedicalRecord)

<img width="757" height="738" alt="hospital class diagram" src="https://github.com/user-attachments/assets/233693ab-0f3f-44b1-a549-a57dfc52b3a6" />

### 5.2 Sơ đồ thuật toán (Sequence Diagram)

#### 5.2.1 CRUD CREATE (Tạo Bệnh án)

<img width="1803" height="529" alt="CRUD Create" src="https://github.com/user-attachments/assets/03414ce0-74f1-4d17-8026-d9a755b08cde" />

#### 5.2.2 CRUD READ (Xem danh sách Bệnh án)

<img width="1685" height="514" alt="xem danh sách bệnh nhân (CRUD Read)" src="https://github.com/user-attachments/assets/1b680a6f-ecad-40c4-a06a-7b68d79b4fbe" />

#### 5.2.3 CRUD UPDATE (Cập nhật Bệnh án)

<img width="2251" height="800" alt="cập nhật lịch hẹn (CRUD Update)" src="https://github.com/user-attachments/assets/40cc707e-2989-4a90-a3c5-8cf9996a1436" />

#### 5.2.4 CRUD DELETE (Xóa Bệnh án)

<img width="2180" height="698" alt="huỷ lịch hẹn (CRUD Delete)" src="https://github.com/user-attachments/assets/3d663488-edda-4b05-a010-901b891ee2ac" />

#### 5.2.5 Chức năng lõi (Bệnh nhân đặt lịch hẹn)

<img width="2163" height="816" alt="bệnh nhân đặt lịch hẹn" src="https://github.com/user-attachments/assets/c19bd427-2648-4abd-835f-b1ba6ff1b558" />


