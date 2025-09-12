package test;

import src.Identity;
import src.User;

public class TestListOfUser {
    public static void test() {
        ListOfUser list = new ListOfUser();

        // CREATE
        list.addUser(new User(new Identity("Nguyen Minh Tuan", "17-08-2004", "001"), "Admin"));
        list.addUser(new User(new Identity("Trang", "02-02-2001", "002"), "User"));

        // READ ALL
        System.out.println("All Users:");
        for (User u : list.getAllUsers()) {
            System.out.println(u.getInfo());
        }

        // UPDATE
        System.out.println("\nUpdate User 002:");
        list.updateUser("002", "Trang Updated", "02-02-2001", "Moderator");
        System.out.println(list.getUserById("002").getInfo());

        // DELETE
        System.out.println("\nDelete User 002:");
        list.deleteUser("002");
        for (User u : list.getAllUsers()) {
            System.out.println(u.getInfo());
        }
    }
}
