package View;

import java.util.Scanner;

import Model.Database;

public class Login {

    public Login(Scanner s, Database database) {
        System.out.println("Welcome to Hospital Management System");
        System.out.println("Enter email:");
        String email = s.next();
        System.out.println("Enter password:");
        String password = s.next();

        Controller.Login login = new Controller.Login(email, password, database);
        if (login.isLoggedIn()) {
            login.getUser().showList(s, database);
        } else {
            System.out.println("Wrong email or password");
        }
    }

}
