import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Calculator calc = new Calculator();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            if (str.equals("#"))    // exit flag
                break;
            try {
                System.out.println(calc.expression(str));
            } catch (CalcError e) {
                System.out.println("error");
            }
        }
    }
}
