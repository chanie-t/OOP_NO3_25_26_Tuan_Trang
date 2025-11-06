# Đồ án OOP: Hệ Thống Quản Lý Bệnh Viện

Đây là đồ án môn học Lập trình Hướng đối tượng (OOP)

## 1. Thông tin Nhóm

| STT | Họ và Tên | MSSV | Email |
| :--- | :--- | :--- | :--- |
| 1 | Nguyễn Minh Tuấn | 22010478 | 22010478@st.phenikaa-uni.edu.vn |
| 2 | Nguyễn Thùy Trang | 23010487 | 23010487@st.phenikaa-uni.edu.vn |

---

## 2. Thông tin

* **Link Github Repository:** https://github.com/chanie-t/OOP_NO3_25_26_Tuan_Trang
* **Link Demo Youtube:** `https://www.youtube.com/watch?v=M3vxbVqIVQM`
* **Link Deploy (chạy toàn cầu):** `[link]`

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

(Mô tả 4 đối tượng chính `Patient`, `Doctor`, `Appointment`, `MedicalRecord` và thể hiện rõ tính kế thừa từ lớp cha `User`, cũng như các lớp DTO và Mapper)

<img width="1727" height="719" alt="class diagram" src="images/class diagram.png">

### 5.2 Sơ đồ thuật toán (Sequence Diagram)

#### 5.2.1 Chức năng lõi: Bệnh nhân đặt lịch hẹn

<img width="1255" height="451" alt="chức năng lõi" src="images/Chức năng lõi.png">

#### 5.2.2 CRUD Create: Bác sĩ tạo Bệnh án

<img width="1322" height="451" alt="CRUD bsi tạo bệnh án" src="images/CRUD bsi tạo bệnh án.png" />

#### 5.2.3 CRUD Read: Bệnh nhân xem chi tiết Bệnh án

<img width="717" height="422" alt="CRUD bệnh nhân xem bệnh án" src="images/CRUD bệnh nhân xem bệnh án.png" />

#### 5.2.4 CRUD Update: Cập nhật Hồ sơ cá nhân

<img width="1182" height="546" alt="CRUD hồ sơ cá nhân" src="images/CRUD hồ sơ cá nhân.png" />

#### 5.2.5 CRUD Delete: Bệnh nhân hủy lịch hẹn

<img width="920" height="493" alt="CRUD bệnh nhân huỷ lịch hẹn" src="images/CRUD bệnh nhân huỷ lịch hẹn.png" />
