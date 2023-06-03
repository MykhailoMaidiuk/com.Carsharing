package userUI;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


import car.*;
import app.User;
/**
 * The UserUtils class provides utility methods for the UserApp to interact with cars and bookings.
 */
public class UserUtils implements IUser {
    private List<Car> cars;// dodat list aut do diagramu
    private final String carsDataFilePath = "Data" + File.separator + "CarsData.txt";

    private final Scanner scanner;
    /**
     * Initializes a new instance of the UserUtils class.
     */
    public UserUtils() {
        this.cars = loadCarsFromFile();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Loads the car data from the file into the cars list.
     *
     * @return The list of loaded cars.
     */
    private List<Car> loadCarsFromFile() {
        List<Car> loadedCars = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(carsDataFilePath));
            for (String line : lines) {
                if (!line.isEmpty()) { // Skip empty lines
                    String[] carDetails = line.split(",");
                    if (carDetails.length >= 8) { // Check if there are enough data
                        Car car = createCarFromDetails(carDetails);
                        loadedCars.add(car);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading from the file: " + e.getMessage());
        }
        return loadedCars;
    }
    /**
     * Creates a Car object from the car details array.
     *
     * @param carDetails The array containing the car details.
     * @return The created Car object.
     */

    private Car createCarFromDetails(String[] carDetails) {
        String carType = carDetails[0];
        String name = carDetails[1];
        int old = Integer.parseInt(carDetails[2]);
        int price = Integer.parseInt(carDetails[3]);
        Color color = Color.valueOf(carDetails[4]);
        String engineType = carDetails[5];
        String licensePlateNumber = carDetails[6];
        boolean rented = Boolean.parseBoolean(carDetails[7]);
        return CarFactory.createCar(carType, name, old, price, color, engineType, licensePlateNumber, rented);
    }
    /**
     * Allows the user to book a car.
     *
     * @param currentUser The current user who wants to book the car.
     */
    public void bookCar(User currentUser) {
        System.out.println("Enter the license plate number of the car you want to book:");
        String licensePlateNumber = scanner.nextLine();
        Car selectedCar = findCarByLicensePlateNumber(licensePlateNumber);
        if (selectedCar != null) {
            if (selectedCar.isRented()) {
                System.out.println("Sorry, the car is already rented.");
            } else {
                System.out.println("Enter the number of days you want to book the car:");
                int numOfDays = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                LocalDate startDate = LocalDate.now();
                LocalDate endDate = startDate.plusDays(numOfDays);

                Booking booking = new Booking(startDate, endDate, selectedCar.getPrice(), selectedCar, currentUser);
                selectedCar.setRented(true);
                selectedCar.setBooking(booking);

                System.out.println("Car successfully booked. The car will be rented from " + startDate + " to " + endDate + ".");
                saveCarsToFile(); // Save updated car data to file
            }
        } else {
            System.out.println("No car found with the provided license plate number.");
        }
    }
    
 // This method updates the car data file after each booking or cancellation.
    private void saveCarsToFile() {
        try {
            FileWriter writer = new FileWriter(carsDataFilePath, false); // false to overwrite existing file
            for (Car car : cars) {
                writer.write(car.carDetailsToString() + "\n"); // Assuming carDetailsToString() returns a CSV string of car data
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    /**
     * Displays the list of cars to the user, sorted based on the chosen sorting option.
     */
    public void viewCarList() {
        try {
            System.out.println("Choose sorting option:");
            System.out.println("1. By price");
            System.out.println("2. By name");
            System.out.println("3. By fuel type");
            int sortChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline left-over

            List<Car> sortedCars = new ArrayList<>(cars);

            switch (sortChoice) {
                case 1:
                    Collections.sort(sortedCars, Comparator.comparingInt(Car::getPrice));
                    break;
                case 2:
                    Collections.sort(sortedCars, Comparator.comparing(Car::getName));
                    break;
                case 3:
                    Collections.sort(sortedCars, Comparator.comparing(Car::getEngineType));
                    break;
                default:
                    System.out.println("Invalid choice. Displaying in original order.");
            }

            for (Car car : sortedCars) {
                car.displayCarInfo();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
        } catch (Exception e) {
            System.out.println("An error occurred while sorting and displaying cars: " + e.getMessage());
        }
    }


    /**
     * Displays the list of bookings made by users.
     */
    public void viewBookings(User currentUser) {
        for (Car car : cars) {
            if (car.isRented()) {
                Booking booking = car.getBooking();
                if (booking != null && booking.getUser().equals(currentUser)) {
                    System.out.println("Car: " + car.getName());
                    System.out.println("License Plate Number: " + car.getLicensePlateNumber());
                    LocalDate startDate = booking.getStartDate();
                    LocalDate endDate = booking.getEndDate();
                    long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);// return the number of days between startDate and endDate.
                    System.out.println("Duration: " + numOfDays + " days");
                    System.out.println("Total Cost: $" + booking.getTotalCost());
                    System.out.println("------------------------");
                }
            }
        }
    }

    /**
     * Allows the user to cancel their booking.
     *
     * @param currentUser The current user who wants to cancel the booking.
     */
    public void cancelBooking(User currentUser) {
        System.out.println("Enter the license plate number of the car to cancel the booking:");
        String licensePlateNumber = scanner.nextLine();
        Car selectedCar = findCarByLicensePlateNumber(licensePlateNumber);
        if (selectedCar != null) {
            if (selectedCar.isRented()) {
                Booking booking = selectedCar.getBooking();
                if (booking != null && booking.getUser().equals(currentUser)) {
                    selectedCar.setRented(false);
                    selectedCar.setBooking(null);
                    System.out.println("Booking canceled successfully.");
                } else {
                    System.out.println("You can only cancel your own booking.");
                }
            } else {
                System.out.println("The car is not currently rented.");
            }
        } else {
            System.out.println("No car found with the provided license plate number.");
        }
    }
    /**
     * Finds a car in the cars list by its license plate number.
     *
     * @param licensePlateNumber The license plate number of the car to find.
     * @return The Car object if found, null otherwise.
     */
    private Car findCarByLicensePlateNumber(String licensePlateNumber) {
        for (Car car : cars) {
            if (car.getLicensePlateNumber().equals(licensePlateNumber)) {
                return car;
            }
        }
        return null;
    }

}
