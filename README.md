OOP-N03_25_26 
# Hệ Thống Quản Lý Bệnh Viện
## Thành viên :

Nguyễn Minh Tuấn - 22010478@st.phenikaa-uni.edu.vn

Nguyễn Thùy Trang - 23010487@st.phenikaa-uni.edu.vn

Nội dung:
## Project: Hệ thống quản lý Bệnh viện.

Yêu cầu chính:

- Giao diện <b>Java Spring Boot</b>.
- Có chức năng quản lý bệnh nhân, phòng điều trị.
- Và các chức năng khác.

Cụ thể:

## Quản lý bệnh nhân

- Thêm bệnh nhân mới

- Sửa thông tin bệnh nhân

- Xóa bệnh nhân

- Xem danh sách bệnh nhân

- Lọc bệnh nhân theo độ tuổi (ví dụ: > 60 tuổi → bệnh nhân già, 18–30 → thanh niên, …)

## Quản lý phòng

- Thêm phòng mới

- Sửa thông tin phòng

- Xóa phòng

- Liệt kê tất cả các phòng + số bệnh nhân trong đó

## Gán bệnh nhân vào phòng

- Chọn bệnh nhân và đưa vào phòng cụ thể

- Nếu phòng đã đầy (patients.size() == capacity) → báo lỗi

- Dữ liệu được lưu trữ xuống file nhị phân

+ Cần tạo các lớp liên quan đến “bệnh nhân”, “phòng điều trị” để đọc, ghi xuống 1 hay nhiều file.

- Khi làm việc với dữ liệu trong bộ nhớ, dữ liệu cần được lưu trữ dưới dạng các Collection tùy chọn như ArrayList, LinkedList, Map, ....

- Sinh viên có thể thêm các chức năng vào chương trình để ứng dụng phong phú hơn bằng cách thêm các nghiệp vụ cho bài toán (tùy chọn).

## Sơ đồ khối yêu cầu
## 1.1 UML Class Diagram

<img width="757" height="738" alt="hospital class diagram" src="https://github.com/user-attachments/assets/233693ab-0f3f-44b1-a549-a57dfc52b3a6" />

## 1.2 Sơ đồ thuật toán
## 1.2.1 <strong>CRUD CREATE</strong>

<img width="1803" height="529" alt="CRUD Create" src="https://github.com/user-attachments/assets/03414ce0-74f1-4d17-8026-d9a755b08cde" />

## 1.2.2 <strong>CRUD READ</strong>

<img width="1685" height="514" alt="xem danh sách bệnh nhân (CRUD Read)" src="https://github.com/user-attachments/assets/1b680a6f-ecad-40c4-a06a-7b68d79b4fbe" />

## 1.2.3 <strong>CRUD UPDATE</strong>

<img width="2251" height="800" alt="cập nhật lịch hẹn (CRUD Update)" src="https://github.com/user-attachments/assets/40cc707e-2989-4a90-a3c5-8cf9996a1436" />

## 1.2.4 <strong>CRUD DELETE</strong>

<img width="2180" height="698" alt="huỷ lịch hẹn (CRUD Delete)" src="https://github.com/user-attachments/assets/3d663488-edda-4b05-a010-901b891ee2ac" />

## 1.2.5 <strong>Chức năng lõi</strong>

<img width="2163" height="816" alt="bệnh nhân đặt lịch hẹn" src="https://github.com/user-attachments/assets/c19bd427-2648-4abd-835f-b1ba6ff1b558" />



