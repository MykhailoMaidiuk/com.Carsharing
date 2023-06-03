package car;
/**
 * The SUV class represents an SUV car.
 * It is a subclass of the Car class.
 */
public class SUV extends Car{

	public SUV(String name, int old, int price, Color color, String engineType, String licensePlateNumber,
			boolean rented) {
		super(name, old, price, color, engineType, licensePlateNumber, rented);
	}
	   /**
     * Returns a string representation of the SUV object.
     * @return the string representation of the SUV
     */
	@Override
	public String toString() {
		System.out.println();


		String rentalStatus = this.isRented() ? "Rented" : "Available";
	    return "SUV Details- " +
	           this.getClass().getSimpleName() + ";\n" +
	           "Name: " + this.getName() + "; " +
	           "Model: " + this.getOld() + " year; " +
	           "Price: $" + this.getPrice() + " (rent per day), " + "\n" +
	           "Color: " + this.getColor() + " " + "\n" +
	           "Engine Type: " + this.getEngineType() + " " + "\n" +
	           "License Plate Number: " + this.getLicensePlateNumber() + "\n" + 
	           "Status: " + rentalStatus;
	}

}
