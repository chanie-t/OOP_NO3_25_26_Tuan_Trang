package test;

public class TestTime {
    public static void main(String[] args) {
        // Test lấy thời gian hiện tại
        String now = Time.getCurrentTime();
        System.out.println(" Thời gian hiện tại: " + now);

        // Test tính tổng phút
        int h = 2, m = 30;
        long totalMinutes = Time.sumMinutes(h, m);
        System.out.println("thời gian " + h + " giờ " + m + " phút = " + totalMinutes + " phút");
    }
}
