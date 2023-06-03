package car;


import java.io.*;

public abstract class Car {
    private String name;
    private int old;
    private int price;
    private Color color;
    private String engineType;
    private String licensePlateNumber;
    private boolean rented;
    private Booking booking;
    
    /**
     * Constructs a Car object with the specified details.
     *
     * @param name              the name of the car
     * @param old               the age of the car
     * @param price             the price of the car
     * @param color             the color of the car
     * @param engineType        the engine type of the car
     * @param licensePlateNumber the license plate number of the car
     * @param rented            the rental status of the car
     */
    public Car(String name, int old, int price, Color color, String engineType, String licensePlateNumber, boolean rented) {
        this.name = name;
        this.old = old;
        this.price = price;
        this.color = color;
        this.engineType = engineType;
        this.licensePlateNumber = licensePlateNumber;
        this.rented = rented;
        this.booking = null;
    }
//  Add getters and setters for the above attributes
    public String getName() {
        return name;
    }

    public int getOld() {
        return old;
    }

    public int getPrice() {
        return price;
    }

    public Color getColor() {
        return color;
    }

    public String getEngineType() {
        return engineType;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Returns a string representation of the car details.
     *
     * @return The car details in CSV format.
     */
    public String carDetailsToString() {
        return this.getClass().getSimpleName() + ","
                + this.getName() + ","
                + this.getOld() + ","
                + this.getPrice() + ","
                + this.getColor().name() + ","
                + this.getEngineType() + ","
                + this.getLicensePlateNumber() + ","
                + this.isRented();
    }

  
    /**
     * Displays the information about the car, including its details, rental status, and booking information.
     */
    

    public void displayCarInfo() {
        String carDetails = String.format(
            "\n%s Details:\n" +
            "Car Name: %s\n" +
            "Car Age: %d\n" +
            "Car Price: %.2f\n" +
            "Car Color: %s\n" +
            "Engine Type: %s\n" +
            "License Plate Number: %s\n" +
            "Is rented?: %s\n",
            this.getClass().getSimpleName(),
            this.name,
            this.old,
            this.price,
            this.color,
            this.engineType,
            this.licensePlateNumber,
            (this.rented ? "Yes" : "No")
        );
        
        System.out.println(carDetails);

        if (this.rented && this.getBooking() != null) {
            String rentalDetails = String.format(
                "Duration: %d days\n" +
                "Total Cost: $%.2f\n",
                this.getBooking().getDuration(),
                this.getBooking().getTotalCost()
            );

            System.out.println(rentalDetails);
        }
    }

    /**
     * Saves the car details to a file.
     */
    public void saveToFile() {
        StringBuilder carDetails = new StringBuilder();
        carDetails.append(this.getClass().getSimpleName()).append(",")
                  .append(this.getName()).append(",")
                  .append(this.getOld()).append(",")
                  .append(this.getPrice()).append(",")
                  .append(this.getColor().name()).append(",")
                  .append(this.getEngineType()).append(",")
                  .append(this.getLicensePlateNumber()).append(",")
                  .append(this.isRented());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data" + File.separator + "CarsData.txt", true))) {
            writer.write(carDetails.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

}
