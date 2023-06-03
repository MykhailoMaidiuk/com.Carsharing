package car;

public class CarFactory {
    public static Car createCar(String carType, String name, int old, int price, Color color, String engineType,
                                String licensePlateNumber, boolean rented) {
        switch (carType) {
            case "Sedan":
                return new Sedan(name, old, price, color, engineType, licensePlateNumber, rented);
            case "SUV":
                return new SUV(name, old, price, color, engineType, licensePlateNumber, rented);
            case "PickupTruck":
                return new PickupTruck(name, old, price, color, engineType, licensePlateNumber, rented);
            default:
                throw new IllegalArgumentException("Invalid car type: " + carType);
        }
    }
}
