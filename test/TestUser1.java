import HospitalSystem.Model.User;

public class TestUser1 {
    public static void main(String[] args) {
        User u = new User();
        u.setID(1);
        u.setFirstName("Nguyen");
        u.setMiddleName("Van");
        u.setLastName("A");
        u.setEmail("a@gmail.com");
        u.setPhoneNumber("0123456789");
        u.setPassword("123456");

        System.out.println(u);
    }
}
