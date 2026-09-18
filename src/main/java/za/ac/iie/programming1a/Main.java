package za.ac.iie.programming1a;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("      REGISTRATION SYSTEM");
        System.out.println("================================");


        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your cellphone number: ");
        String cellphoneNumber = scanner.nextLine();


        Login user = new Login(
                username,
                password,
                cellphoneNumber,
                firstName,
                lastName
        );

        String registrationMessage = user.registerUser();

        System.out.println();
        System.out.println(registrationMessage);

        if (registrationMessage.equals("Registration successful.")) {

            System.out.println();
            System.out.println("================================");
            System.out.println("             LOGIN");
            System.out.println("================================");

            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            boolean loginStatus =
                    user.loginUser(enteredUsername, enteredPassword);

            System.out.println();
            System.out.println(user.returnLoginStatus(loginStatus));
        }

        scanner.close();
    }
}