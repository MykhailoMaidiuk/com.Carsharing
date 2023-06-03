package car;

/**
 * The Sedan class represents a sedan car.
 * It is a subclass of the Car class.
 */
public class Sedan extends Car{

	public Sedan(String name, int old, int price, Color color, String engineType, String licensePlateNumber,
			boolean rented) {
		super(name, old, price, color, engineType, licensePlateNumber, rented);
	}
	
	/**
     * Returns a string representation of the Sedan object.
     * @return the string representation of the sedan
     */
	@Override
	public String toString() {
		System.out.println();


	    String rentalStatus = this.isRented() ? "Rented" : "Available";
	    return "Sedan Details- " +
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
