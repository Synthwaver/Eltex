import java.util.Scanner;
import java.util.InputMismatchException;

public class Calculator {
    public static void main(String[] args) {
        System.out.print("Enter expression like '1 + 2'\nAvailable operations: +, -, *, /\n");

        float a, b;
        String operation;
        Scanner scanner = new Scanner(System.in);
        try {
            a = scanner.nextFloat();
            operation = scanner.next();
            b = scanner.nextFloat();
        }
        catch (InputMismatchException e) {
            System.err.printf("Wrong format\n");
            return;
        }
        finally {
            scanner.close();
        }

        float result;
        switch (operation) {
            case "+" : result = a + b; break;
            case "-" : result = a - b; break;
            case "*" : result = a * b; break;
            case "/" : result = a / b; break;
            default:
                System.err.printf("Invalid operation '%s'\n", operation);
                return;
        }
        System.out.printf("%f %s %f = %f\n", a, operation, b, result);
    }
}