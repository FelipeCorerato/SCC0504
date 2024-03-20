import java.util.Scanner;

public class FactorialCalculator {
    // Recursive method to calculate factorial
    public static int factorialRecursive(int n) {
        if (n == 0) {
            return 1;
        }

        return n * factorialRecursive(n - 1);
    }

    // Iterative method to calculate factorial
    public static int factorialIterative(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    // Iterative method to calculate the sum of all numbers to 1 till the integer argument
    public static int sumTillN(int n) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result += i;
        }

        return result;
    }

    public static void main(String[] args) {
        // Instantiates the scanner to read inputs

        try (Scanner scanner = new Scanner(System.in)) { // Uses ARM to close scanner as finally clause
            // Reads input number
            int inputNumber = Integer.parseInt(scanner.nextLine());

            // Validates if is positive
            if (inputNumber <= 0) {
                System.out.println("Invalid input! The input must be positive!");
            } else {
                // Prints the factorial and the sum results
                System.out.println("Factorial of " + inputNumber + " (Recursive): " + factorialRecursive(inputNumber));
                System.out.println("Factorial of " + inputNumber + " (Iterative): " + factorialIterative(inputNumber));
                System.out.println("Sum of all integers from 1 to " + inputNumber + " is: " + sumTillN(inputNumber));
            }
        } catch (NumberFormatException e) { // Catch exception if input is not number
            System.out.println("Invalid input! The input must be a positive integer!");
        }
    }
}
