package adminUI;

import java.io.*;
import java.util.*;

import java.util.stream.Collectors;

import app.*;
import car.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class AdminUtils implements IAdmin {
    private List<Car> cars;
    private Scanner scanner;
    private final File carsDataFile = new File("Data" + File.separator + "CarsData.txt");
    /**
     * Constructor for AdminUtils class.
     * Initializes the list of cars and the scanner for user input.
     * Also, loads the car data from the file into the list of cars.
     */
    public AdminUtils() {
        this.cars = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        loadCarsFromFile();
    }

    /**
     * This private method loads car data from the file and populates the list of cars.
     * It handles the potential IOException that could occur while reading from the file.
     */
    private void loadCarsFromFile() {
        try (LineIterator it = FileUtils.lineIterator(carsDataFile, "UTF-8")) { // Here, it is initializing a LineIterator with a file and character encoding
            while (it.hasNext()) {
                String line = it.nextLine();
                if (!line.isEmpty()) { 
                    String[] carDetails = line.split(",");
                    if (carDetails.length >= 8) { 
                        Car car = createCarFromDetails(carDetails);
                        cars.add(car);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading from the file: " + e.getMessage());
        }
    } 	


    /**
     * This private method constructs a Car object from a given array of Strings representing car details.
     * It uses a CarFactory to create the correct subtype of Car.
     * 
     * @param carDetails Array of Strings containing car details
     * @return Car object created from given details
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
     * Adds a new car to the list of cars.
     * Prompts the admin for car details and creates a new car object.
     * Saves the new car to the data file.
     */
    public void addCar() {
        String type = null;
        String name = null;
        Integer old = null;
        Integer price = null;
        Color color = null;
        String engineType = null;
        String licensePlateNumber = null;
        Boolean rented = null;
        
        while (type == null) {
            try {
                System.out.println("Choose car type:");
                System.out.println("1. Sedan");
                System.out.println("2. SUV");
                System.out.println("3. PickupTruck");
                int typeChoice = scanner.nextInt();
                scanner.nextLine();  // consume newline left-over
                switch (typeChoice) {
                    case 1:
                        type = "Sedan";
                        break;
                    case 2:
                        type = "SUV";
                        break;
                    case 3:
                        type = "PickupTruck";
                        break;
                    default:
                        System.out.println("Invalid car type.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please input a number for car type.");
                scanner.nextLine(); // consume the incorrect input
            }
        }

        System.out.println("Name:");
        name = scanner.nextLine();

        while (old == null) {
            try {
                System.out.println("Old:");
                old = scanner.nextInt();
                scanner.nextLine();  // consume newline left-over
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please input a number for the age.");
                scanner.nextLine(); // consume the incorrect input
            }
        }

        while (price == null) {
            try {
                System.out.println("Price:");
                price = scanner.nextInt();
                scanner.nextLine();  // consume newline left-over
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please input a number for the price.");
                scanner.nextLine(); // consume the incorrect input
            }
        }

        while (color == null) {
            try {
                System.out.println("Color (RED, BLUE, BLACK, WHITE, SILVER):");
                color = Color.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid color. Please try again with a valid color (RED, BLUE, BLACK, WHITE, SILVER).");
            }
        }

        while (engineType == null) {
            try {
                System.out.println("Engine Type (Petrol, Electricity, Diesel):");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Petrol") || input.equalsIgnoreCase("Electricity") || input.equalsIgnoreCase("Diesel")) {
                    engineType = input;
                } else {
                    System.out.println("Invalid engine type. Please input 'Petrol', 'Electricity', or 'Diesel'.");
                }
            } catch (Exception e) {
                System.err.println("Invalid input. Please try again with a valid engine type (Petrol, Electricity, Diesel).");
            }
        }


        while (licensePlateNumber == null) {
            try {
                System.out.println("License Plate Number:");
                String input = scanner.nextLine();
                // Check if a car with the given license plate number already exists
                if (findCarByLicensePlateNumber(input) != null) {
                    System.out.println("A car with this license plate number already exists. Please enter a different number.");
                } else {
                    licensePlateNumber = input;
                }
            } catch (Exception e) {
                System.err.println("Invalid input. Please try again.");
            }
        }


        while (rented == null) {
            try {
                System.out.println("Is rented (true/false):");
                rented = scanner.nextBoolean();
                scanner.nextLine();  // consume newline left-over
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please input true or false for the rental status.");
                scanner.nextLine(); // consume the incorrect input
            }
        }

        Car car;
        switch (type) {
            case "Sedan":
                car = new Sedan(name, old, price, color, engineType, licensePlateNumber, rented);
                break;
            case "SUV":
                car = new SUV(name, old, price, color, engineType, licensePlateNumber, rented);
                break;
            case "PickupTruck":
                car = new PickupTruck(name, old, price, color, engineType, licensePlateNumber, rented);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        cars.add(car);
        car.saveToFile();
    }

    /**
     * Finds a car in the cars list by its license plate number.
     *
     * @param licensePlateNumber The license plate number of the car to find.
     * @return The Car object if found, null otherwise.
     */
    private Car findCarByLicensePlateNumber(String licensePlateNumber) {
        for (Car car : cars) {
            if (car.getLicensePlateNumber().equals(licensePlateNumber)) { // Check if the license plate number of the current car matches the provided license plate number
                return car;
            }
        }
        return null;
    }

    /**
     * This public method removes a car from the list of cars.
     * It prompts the admin for the license plate number of the car to remove and saves the changes to the data file.
     * If no car is found with the given license plate number, it informs the admin.
     */
    public void removeCar() {
        System.out.println("Enter the license plate number of the car to remove:");
        String licensePlateNumber = scanner.nextLine();
        Car carToRemove = null;
        for (Car car : cars) {
            if (car.getLicensePlateNumber().equals(licensePlateNumber)) {
                carToRemove = car;
                break;
            }
        }
        if (carToRemove != null) {
            cars.remove(carToRemove);
            saveToFile(); // Save changes to file
        } else {
            System.out.println("No car found with license plate number " + licensePlateNumber);
        }
    }
    /**
     * Displays the list of cars, sorted by the specified sorting option.
     * 1. By name
     * 2. By price
     * Displays the cars in their original order if an invalid option is chosen.
     */
    public void viewCarList() {
        System.out.println("Choose sorting option:");
        System.out.println("1. By name");
        System.out.println("2. By price");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();  // consume newline left-over

        List<Car> sortedCars;

        switch (sortChoice) {
            case 1:
                sortedCars = cars.stream()//This creates a stream of the car objects in the cars list. 
                        .sorted(Comparator.comparing(Car::getName))//his is an intermediate operation that sorts the car objects in the stream based on their names.
                        .collect(Collectors.toList());//This is a terminal operation that collects the sorted elements of the stream into a new list. 
                break;
            case 2:
                sortedCars = cars.stream()
                        .sorted(Comparator.comparing(Car::getPrice))
                        .collect(Collectors.toList());
                break;
            default:
                System.out.println("Invalid choice. Displaying in original order.");
                sortedCars = cars;
        }

        for (Car car : sortedCars) {
            System.out.println(car.toString());
        }
    }

    /**
     * Displays the list of rented cars along with the username of the renter.
     * If booking details are unavailable, it will be indicated.
     */
    public void viewRentedCars() {
        List<Car> rentedCars = cars.stream()
                .filter(Car::isRented)
                .collect(Collectors.toList());

        if (rentedCars.isEmpty()) {
            System.out.println("No cars are currently rented.");
        } else {
            for (Car car : rentedCars) {
                System.out.println("Car: " + car.getName());
                System.out.println("License Plate Number: " + car.getLicensePlateNumber());
                Booking booking = car.getBooking();
                if (booking != null) {
                    User user = booking.getUser();
                    System.out.println("Rented by: " + user.getUsername());
                } 
                
                System.out.println("------------------------");
            }
        }
    }

    /**
     * This private method saves the list of cars to the data file.
     * It handles the potential IOException that could occur while writing to the file.
     */
    private void saveToFile() {
        try {
        	// Convert each car object to a string representation and collect them into a list of strings
            List<String> lines = cars.stream().map(Car::toString).collect(Collectors.toList());
            FileUtils.writeLines(carsDataFile, lines); // Write the list of strings to the file using FileUtils.writeLines()
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
