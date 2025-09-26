package Test;

import java.util.Scanner;

import Review.Doctor;
import Review.Hospital;
import Review. Patient;
import Review.Selector;


public class TestHospital {
    public static void test() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập số lượng (bệnh nhân + bác sĩ): ");
        int n = sc.nextInt();
        sc.nextLine(); // bỏ dòng trống

        Hospital h = new Hospital(n);

        for (int i = 0; i < n; i++) {
            System.out.print("Chọn (1 = Bệnh nhân, 2 = Bác sĩ): ");
            int type = sc.nextInt();
            sc.nextLine();

            if (type == 1) {
                System.out.print("Tên bệnh nhân: ");
                String name = sc.nextLine();
                System.out.print("Tuổi: ");
                int age = sc.nextInt();
                sc.nextLine();
                h.add(new Patient(name, age));
            } else if (type == 2) {
                System.out.print("Tên bác sĩ: ");
                String name = sc.nextLine();
                System.out.print("Chuyên khoa: ");
                String specialty = sc.nextLine();
                h.add(new Doctor(name, specialty));
            } else {
                System.out.println("Nhập 1 hoặc 2");
            }
        }

        System.out.println("\n=== Danh sách bệnh viện ===");
        Selector selector = h.getSelector();
        while (!selector.end()) {
            System.out.println(selector.current());
            selector.next();
        }

        sc.close();
    }
}
