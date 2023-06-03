package adminUI;

import java.util.Scanner;

/**
 * This class represents the admin panel in the application. It allows the admin
 * to add, remove, and change car info, as well as view the car list and rented
 * cars.
 */
public class AdminApp {
	private AdminUtils adminUtils;
	private Scanner scanner;

	/**
	 * Constructor for the AdminApp class. Initializes the AdminUtils object and the
	 * Scanner object.
	 */
	public AdminApp() {
		this.adminUtils = new AdminUtils();
		this.scanner = new Scanner(System.in);
	}

	/**
	 * Displays the admin menu and handles the admin's choices.
	 */
	public void showAdminMenu() {
		while (true) {
			System.out.println("\nWhat would you like to do?");
			System.out.println("1. Add a car");
			System.out.println("2. Remove a car");
			System.out.println("3. View car list");
			System.out.println("4. View rented cars");
			System.out.println("5. Return to main menu");
			try {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					adminUtils.addCar();
					break;
				case 2:
					adminUtils.removeCar();
					break;
				case 3:
					adminUtils.viewCarList();
					break;
				case 4:
					adminUtils.viewRentedCars();
					break;
				case 5:
					return;
				default:
					System.out.println("Invalid choice.");
				}
			} catch (Exception e) {
				System.err.println("An error occurred: " + e.getMessage());
				scanner.nextLine(); // Consume the remaining input
			}
		}
	}
}
