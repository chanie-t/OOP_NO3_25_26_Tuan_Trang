public class BreakAndContinue {

    public static void WhileTest(int n) {
        int i = 0;
        while (i < n) {
            if (i == 5) {
                System.out.println("Break tại i = " + i);
                break;
            }
            System.out.println("While i = " + i);
            i++;
        }
    }

    public static void DoWhileTest(int n) {
        int i = 0;
        do {
            i++;
            if (i % 2 == 0) {
                continue;
            }
            System.out.println("DoWhile i = " + i);
        } while (i < n);
    }
}
