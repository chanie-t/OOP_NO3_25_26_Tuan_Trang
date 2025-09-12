package test;
import src.Time;

public class TestTime {
    public static void test() {
        Time t = new Time();
        System.out.println("Date: " + t.getCurrentDate());
        System.out.println("Time: " + t.getCurrentTime());
    }
}
