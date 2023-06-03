package app;

import java.util.InputMismatchException;
import java.util.Scanner;

import adminUI.AdminApp;
import userUI.UserApp;

// password for Admin = 123;

public class CarSharingApp {
	/**
	 * The main class for the Car Sharing App.
	 * Handles user registration, login, and navigation between user and admin panels.
	 */
    public static void main(String[] args) {
        try {
            LogIn login = new LogIn();
            AdminApp adminApp = new AdminApp();
            UserApp userApp = new UserApp();
            login.loadUsers();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("Welcome to Car Sharing App!");
                System.out.println("1. Register a new user");
                System.out.println("2. Login with existing credentials");
                System.out.println("3. Login as Admin");
                System.out.println("4. Exit");
                System.out.print("Enter your choice (1-4): ");

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();  // consume newline left-over

                    switch (choice) {
                        case 1:
                            System.out.println("Enter username: ");
                            String username = scanner.nextLine();
                            System.out.println("Enter password: ");
                            String password = scanner.nextLine();
                            String userType = "user"; // Set the user type accordingly
                            login.registerUser(username, password, userType);
                            break;
                        case 2:
                            System.out.println("Enter username: ");
                            username = scanner.nextLine();
                            System.out.println("Enter password: ");
                            password = scanner.nextLine();
                            boolean isUserLoginSuccess = login.loginUser(username, password);
                            if (isUserLoginSuccess) {
                                User currentUser = login.getCurrentUser();
                                System.out.println("Login successful! Welcome to the user panel, " + currentUser.getUsername() + ".");
                                if (currentUser.getUserType().equals("admin")) {
                                    adminApp.showAdminMenu();
                                } else {
                                    userApp.showUserMenu(currentUser);
                                }
                            } else {
                                System.out.println("Invalid credentials.");
                            }
                            break;
                        case 3:
                            System.out.println("Enter admin password: ");
                            password = scanner.nextLine();
                            boolean isAdminLoginSuccess = login.loginAsAdmin(password);
                            if (isAdminLoginSuccess) {
                                User currentUser = login.getCurrentUser();
                                System.out.println("Login as admin successful! Welcome to the admin panel, " + currentUser.getUsername() + ".");
                                adminApp.showAdminMenu();
                            } else {
                                System.out.println("Invalid admin password.");
                            }
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice, please choose a number between 1 and 4.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice, please enter a numeric value.");
                    scanner.nextLine();  
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
