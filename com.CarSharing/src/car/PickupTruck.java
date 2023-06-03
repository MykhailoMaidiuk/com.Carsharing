package car;
/**
 * The PickupTruck class represents a pickup truck car.
 * It is a subclass of the Car class.
 */
public class PickupTruck extends Car{
	
	public PickupTruck(String name, int old, int price, Color color, String engineType, String licensePlateNumber,
			boolean rented) {
		super(name, old, price, color, engineType, licensePlateNumber, rented);
		// TODO Auto-generated constructor stub
	}
	
	 /**
     * Returns a string representation of the PickupTruck object.
     * @return the string representation of the pickup truck
     */
	@Override
	public String toString() {
		System.out.println();

		String rentalStatus = this.isRented() ? "Rented" : "Available";
	    return "PickupTruck Details- " +
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
	
	


	
	
	

