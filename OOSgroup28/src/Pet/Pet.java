package Pet;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import Booking.Booking;


public class Pet implements Serializable {

	
	// Instance variables
	private String ref;
    private String name;
    private String speciesName;
    private LocalDate regDate;
    private List<Booking> bookings;

   /**
    * Contstructor	
    * 
    * @param ref: The reference code for the pet
    * @param name: The name of the pet
    * @param speciesName: The type of species the pet is
    * @param regDate: The date that the pet was registered
    */
    
    public Pet(String ref, String name, String speciesName, LocalDate regDate) {
    	this.ref = ref;
    	this.name = name;
    	this.speciesName = speciesName;
    	this.regDate = regDate;
    }
    
    // Get methods for pet name, reference and species type    
    public String getPetRef() {
    	return ref;
    }
    
    public String getPetName() {
    	return name;
    }
    
    public String getSpeciesName() {
    	return speciesName;
    }
    
    // Get method that gets the date the pet was registered  
    public LocalDate getRegDate() {
    	return regDate;
    }

    // Method to add booking 
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    // Method to remove booking
    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    // Method to view the next booking
    public Booking nextBooking() {
    	if (!bookings.isEmpty()) {
    		return bookings.get(0);
    	}
		return null;
    }
    
    // Get method to retrieve all the bookings made
    public List<Booking> allBookings() {
		return bookings;
     
    }
    
    // Method to represent all pet information
    public String toString() {
    	return "Pet Ref: " + ref + ", Name: " + name + ", SpeciesName: " + speciesName + ", regDate: " + regDate;
    }
}
