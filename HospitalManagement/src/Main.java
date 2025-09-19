import java.util.ArrayList;
import java.util.Scanner;

class Patient {
    int id;
    String name;
    int age;
    Patient(int id, String name, int age) {
        this.id = id; this.name = name; this.age = age;
    }
    public String toString() {
        return id + " - " + name + " - " + age + " tuổi";
    }
}

class Doctor {
    int id;
    String name;
    String specialty;
    Doctor(int id, String name, String specialty) {
        this.id = id; this.name = name; this.specialty = specialty;
    }
    public String toString() {
        return id + " - " + name + " - " + specialty;
    }
}

class Department {
    int id;
    String name;
    Department(int id, String name) {
        this.id = id; this.name = name;
    }
    public String toString() {
        return id + " - " + name;
    }
}

class Appointment {
    int id;
    String patientName;
    String doctorName;
    String date;
    Appointment(int id, String patientName, String doctorName, String date) {
        this.id = id; this.patientName = patientName;
        this.doctorName = doctorName; this.date = date;
    }
    public String toString() {
        return id + " - " + patientName + " gặp " + doctorName + " vào " + date;
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Department> departments = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== QUẢN LÝ BỆNH VIỆN ===");
            System.out.println("1. Bệnh nhân");
            System.out.println("2. Bác sĩ");
            System.out.println("3. Khoa");
            System.out.println("4. Lịch hẹn");
            System.out.println("5. Thoát");
            System.out.print("Chọn: ");
            int c = sc.nextInt(); sc.nextLine();

            switch (c) {
                case 1: menuPatient(); break;
                case 2: menuDoctor(); break;
                case 3: menuDepartment(); break;
                case 4: menuAppointment(); break;
                case 5: return;
            }
        }
    }

    // ----------- HÀM HỖ TRỢ -----------
    static void requireBack() {
        System.out.println("Không có thông tin!");
        System.out.println("1. Quay lại");
        while (sc.nextInt() != 1) {
            System.out.println("Vui lòng nhập 1 để quay lại!");
        }
        sc.nextLine();
    }

    // ----------- BỆNH NHÂN -------------
    static void menuPatient() {
        while (true) {
            System.out.println("\n-- QUẢN LÝ BỆNH NHÂN --");
            System.out.println("1. Thêm");
            System.out.println("2. Xem");
            System.out.println("3. Sửa");
            System.out.println("4. Xóa");
            System.out.println("5. Quay lại");
            int c = sc.nextInt(); sc.nextLine();
            if (c == 5) break;

            switch (c) {
                case 1:
                    System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Tên: "); String name = sc.nextLine();
                    System.out.print("Tuổi: "); int age = sc.nextInt(); sc.nextLine();
                    patients.add(new Patient(id, name, age));
                    break;
                case 2:
                    if (patients.isEmpty()) requireBack();
                    else for (Patient p : patients) System.out.println(p);
                    break;
                case 3:
                    if (patients.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int uid = sc.nextInt(); sc.nextLine();
                        boolean found = false;
                        for (Patient p : patients) if (p.id == uid) {
                            System.out.print("Tên mới: "); p.name = sc.nextLine();
                            System.out.print("Tuổi mới: "); p.age = sc.nextInt(); sc.nextLine();
                            found = true;
                        }
                        if (!found) System.out.println("Không tìm thấy bệnh nhân!");
                    }
                    break;
                case 4:
                    if (patients.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int did = sc.nextInt(); sc.nextLine();
                        boolean removed = patients.removeIf(p -> p.id == did);
                        if (!removed) System.out.println("Không tìm thấy bệnh nhân!");
                    }
                    break;
            }
        }
    }

    // ----------- BÁC SĨ -------------
    static void menuDoctor() {
        while (true) {
            System.out.println("\n-- QUẢN LÝ BÁC SĨ --");
            System.out.println("1. Thêm");
            System.out.println("2. Xem");
            System.out.println("3. Sửa");
            System.out.println("4. Xóa");
            System.out.println("5. Quay lại");
            int c = sc.nextInt(); sc.nextLine();
            if (c == 5) break;

            switch (c) {
                case 1:
                    System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Tên: "); String name = sc.nextLine();
                    System.out.print("Chuyên khoa: "); String sp = sc.nextLine();
                    doctors.add(new Doctor(id, name, sp));
                    break;
                case 2:
                    if (doctors.isEmpty()) requireBack();
                    else for (Doctor d : doctors) System.out.println(d);
                    break;
                case 3:
                    if (doctors.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int uid = sc.nextInt(); sc.nextLine();
                        boolean found = false;
                        for (Doctor d : doctors) if (d.id == uid) {
                            System.out.print("Tên mới: "); d.name = sc.nextLine();
                            System.out.print("Chuyên khoa mới: "); d.specialty = sc.nextLine();
                            found = true;
                        }
                        if (!found) System.out.println("Không tìm thấy bác sĩ!");
                    }
                    break;
                case 4:
                    if (doctors.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int did = sc.nextInt(); sc.nextLine();
                        boolean removed = doctors.removeIf(d -> d.id == did);
                        if (!removed) System.out.println("Không tìm thấy bác sĩ!");
                    }
                    break;
            }
        }
    }

    // ----------- KHOA -------------
    static void menuDepartment() {
        while (true) {
            System.out.println("\n-- QUẢN LÝ KHOA --");
            System.out.println("1. Thêm");
            System.out.println("2. Xem");
            System.out.println("3. Sửa");
            System.out.println("4. Xóa");
            System.out.println("5. Quay lại");
            int c = sc.nextInt(); sc.nextLine();
            if (c == 5) break;

            switch (c) {
                case 1:
                    System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Tên khoa: "); String name = sc.nextLine();
                    departments.add(new Department(id, name));
                    break;
                case 2:
                    if (departments.isEmpty()) requireBack();
                    else for (Department d : departments) System.out.println(d);
                    break;
                case 3:
                    if (departments.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int uid = sc.nextInt(); sc.nextLine();
                        boolean found = false;
                        for (Department d : departments) if (d.id == uid) {
                            System.out.print("Tên mới: "); d.name = sc.nextLine();
                            found = true;
                        }
                        if (!found) System.out.println("Không tìm thấy khoa!");
                    }
                    break;
                case 4:
                    if (departments.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int did = sc.nextInt(); sc.nextLine();
                        boolean removed = departments.removeIf(d -> d.id == did);
                        if (!removed) System.out.println("Không tìm thấy khoa!");
                    }
                    break;
            }
        }
    }

    // ----------- LỊCH HẸN -------------
    static void menuAppointment() {
        while (true) {
            System.out.println("\n-- QUẢN LÝ LỊCH HẸN --");
            System.out.println("1. Thêm");
            System.out.println("2. Xem");
            System.out.println("3. Sửa");
            System.out.println("4. Xóa");
            System.out.println("5. Quay lại");
            int c = sc.nextInt(); sc.nextLine();
            if (c == 5) break;

            switch (c) {
                case 1:
                    System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Tên bệnh nhân: "); String pname = sc.nextLine();
                    System.out.print("Tên bác sĩ: "); String dname = sc.nextLine();
                    System.out.print("Ngày hẹn: "); String date = sc.nextLine();
                    appointments.add(new Appointment(id, pname, dname, date));
                    break;
                case 2:
                    if (appointments.isEmpty()) requireBack();
                    else for (Appointment a : appointments) System.out.println(a);
                    break;
                case 3:
                    if (appointments.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int uid = sc.nextInt(); sc.nextLine();
                        boolean found = false;
                        for (Appointment a : appointments) if (a.id == uid) {
                            System.out.print("Tên bệnh nhân mới: "); a.patientName = sc.nextLine();
                            System.out.print("Tên bác sĩ mới: "); a.doctorName = sc.nextLine();
                            System.out.print("Ngày hẹn mới: "); a.date = sc.nextLine();
                            found = true;
                        }
                        if (!found) System.out.println("Không tìm thấy lịch hẹn!");
                    }
                    break;
                case 4:
                    if (appointments.isEmpty()) requireBack();
                    else {
                        System.out.print("Nhập ID: "); int did = sc.nextInt(); sc.nextLine();
                        boolean removed = appointments.removeIf(a -> a.id == did);
                        if (!removed) System.out.println("Không tìm thấy lịch hẹn!");
                    }
                    break;
            }
        }
    }
}
