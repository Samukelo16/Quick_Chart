package com.mycompany.quick_chart;

import java.util.Scanner;

public class Login {

    private String storedUsername;
    private String storedPassword;
    private String storedPhone;
    private String firstName;
    private String lastName;

    public boolean registerAndLogin(Scanner input) {
        System.out.print("Enter first name: ");
        firstName = input.nextLine();

        System.out.print("Enter last name: ");
        lastName = input.nextLine();

        registerUser(input);
        boolean loginStatus = loginUser(input);

        if (loginStatus) {
            System.out.println("Login successful.");
            System.out.println("Welcome " + firstName + " " + lastName + ".");
            return true;
        }

        System.out.println("Login failed.");
        System.out.println("Too many failed attempts. Account is locked.");
        return false;
    }

    private void registerUser(Scanner input) {
        while (true) {
            System.out.print("Enter username: ");
            String username = input.nextLine();

            if (checkUsername(username)) {
                System.out.println("Username successfully captured.");
                storedUsername = username;
                break;
            }

            System.out.println("Username incorrectly formatted.");
            System.out.println("Username must contain an underscore and be no more than 5 characters.");
        }

        while (true) {
            System.out.print("Enter password: ");
            String password = input.nextLine();

            if (checkPassword(password)) {
                System.out.println("Password meets complexity requirements.");
                storedPassword = password;
                break;
            }

            System.out.println("Password does not meet complexity requirements.");
            System.out.println("Password must be at least 8 characters and include a capital letter, number, and special character.");
        }

        while (true) {
            System.out.print("Enter phone number (+27...): ");
            String phone = input.nextLine();

            if (checkPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully captured.");
                storedPhone = phone;
                break;
            }

            System.out.println("Invalid phone number. Use South African international format, for example +27831234567.");
        }
    }

    private boolean loginUser(Scanner input) {
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter username: ");
            String username = input.nextLine();

            System.out.print("Enter password: ");
            String password = input.nextLine();

            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                return true;
            }

            attempts++;
            System.out.println("Login failed.");
        }

        return false;
    }

    public String getStoredPhone() {
        return storedPhone;
    }

    public static boolean checkUsername(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    public static boolean checkPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+27\\d{9}$");
    }
}
