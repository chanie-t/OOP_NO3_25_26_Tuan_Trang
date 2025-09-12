import test.TestUser;
import test.TestListOfUser;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Test User ===");
        TestUser.test();

        System.out.println("\n=== Test List Of Use ===");
        TestListOfUser.test();

        //TestTime.test();
        //TestIdentity.test();
        //TestRecursion.test();
    }
    
}
