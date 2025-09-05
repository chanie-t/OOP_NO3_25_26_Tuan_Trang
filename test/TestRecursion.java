package test;

public class TestRecursion {
    public static void main(String[] args) {
        // Tạo bệnh nhân
        Node p1 = new Node("Patient", "Nguyen Van A", 101);
        Node p2 = new Node("Patient", "Tran Thi B", 102);
        Node p3 = new Node("Patient", "Le Van C", 103);

        // Tạo giường
        Node bed1 = new Node("Bed", "Bed 1"); bed1.addChild(p1);
        Node bed2 = new Node("Bed", "Bed 2"); bed2.addChild(p2);
        Node bed3 = new Node("Bed", "Bed 3"); bed3.addChild(p3);

        // Tạo phòng
        Node room1 = new Node("Room", "Room 101"); room1.addChild(bed1); room1.addChild(bed2);
        Node room2 = new Node("Room", "Room 102"); room2.addChild(bed3);

        // Tạo khoa
        Node dep1 = new Node("Department", "Khoa Nội"); dep1.addChild(room1); dep1.addChild(room2);

        // Tạo bệnh viện
        Node hospital = new Node("Hospital", "BV Trung Uong");
        hospital.addChild(dep1);
        
        int[] testIds = {101, 102, 103, 999};
        for (int id : testIds) {
            Node result = hospital.findPatientById(id);
            if (result != null) {
                System.out.println("Tìm thấy bệnh nhân: " + result.getName() + " (ID=" + result.getPatientId() + ")");
            } else {
                System.out.println("Không tìm thấy bệnh nhân có ID=" + id);
            }
        }
    }
}
