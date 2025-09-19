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

<img width="441" height="814" alt="1 drawio" src="https://github.com/user-attachments/assets/864aa93d-c95b-4162-ad9e-60e9db26dcbb" />

## 1.2 🧠Sơ đồ thuật toán
## 1.2.1 <strong>CRUD Doctor</strong>
<img width="1310" height="524" alt="image" src="https://github.com/user-attachments/assets/9c8266c0-dd66-478b-a70d-8eba69599ff6" />


## 1.2.2 <strong>CRUD Patient</strong>

<img width="1323" height="524" alt="image" src="https://github.com/user-attachments/assets/b9f49fb3-8fe3-4a10-8356-0711f548ad02" />


## 1.2.3 <strong>CRUD Department</strong>

<img width="1449" height="524" alt="image" src="https://github.com/user-attachments/assets/92699e60-f455-4963-afc0-529ce6270f0d" />


## 1.2.4 <strong>CRUD Appointment</strong>

<img width="1480" height="524" alt="image" src="https://github.com/user-attachments/assets/9c244a8e-87b8-475b-90ee-3fd03e4a6aef" />





