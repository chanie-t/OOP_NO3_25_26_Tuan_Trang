public class TestUser1 {
    public static void test() {
        User1 u = new User1(1, "Nguyen", "Minh", "Tuan", "abcd@example.com");
        System.out.println(u);

        u.setLastName("TuanB");
        System.out.println("Update: " + u);
    }
}
