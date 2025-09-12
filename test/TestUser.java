package test;

import src.User;
import src.Identity;

public class TestUser {
    public static void test() {
        Identity id1 = new Identity("Nguyen Minh Tuan", "17-08-2004", "001");
        User user1 = new User(id1, "Admin");

        System.out.println(user1.getInfo());
    }
}
