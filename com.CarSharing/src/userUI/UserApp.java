package userUI;

import java.util.InputMismatchException;
import java.util.Scanner;
import app.*;

/**
 * The UserApp class represents the user interface for the user panel.
 * It allows users to interact with the Car Sharing App as a regular user.
 */
public class UserApp {
    private final UserUtils userUtils;
    private final Scanner scanner;

    /**
     * Constructor for creating a UserApp object.
     * Initializes the UserUtils and Scanner objects.
     */
    public UserApp() {
        this.userUtils = new UserUtils();
        this.scanner = new Scanner(System.in);
    }
    /**
     * Displays the user menu and handles user input.
     * @param currentUser the currently logged-in user
     */
    public void showUserMenu(User currentUser) {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            System.out.println();
            System.out.println("User Menu:");
            System.out.println("1. View car list");
            System.out.println("2. Book a car");
            System.out.println("3. View bookings");
            System.out.println("4. Cancel booking");
            System.out.println("5. Logout");
            System.out.print("Enter your choice (1-5): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (choice) {
                    case 1:
                    	 userUtils.viewCarList();
                         break;
                    case 2:
                    	 userUtils.bookCar(currentUser);
                         break;
                    case 3:
                        userUtils.viewBookings(currentUser);
                        break;
                    case 4:
                        userUtils.cancelBooking(currentUser);
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        isLoggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // consume the invalid input
            }
        }

   }
}
