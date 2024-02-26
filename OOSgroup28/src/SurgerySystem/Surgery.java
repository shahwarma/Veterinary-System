package SurgerySystem;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Booking.Booking;
import Pet.Pet;
import Staff.Staff;

public class Surgery implements Serializable {

	// Instance variables
	private String name;
	private String postCode;
	private LocalTime openingTime;
	private LocalTime closingTime;
	private List<Booking> bookings;
	private List<Staff> staff;
	private List<Pet> pets;
	
	   /**
     * Constructor to initialise a Surgery object.
     *
     * @param name: The name of the surgery.
     * @param postCode: The post code of the surgery.
     * @param openingTime: The opening time of the surgery.
     * @param closingTime: The closing time of the surgery.
     */
	public Surgery(String name, String postCode, LocalTime openingTime, LocalTime closingTime) {
		this.name = name;
		this.postCode = postCode;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.bookings = new ArrayList<>();
		this.staff = new ArrayList<>();
		this.pets = new ArrayList<>();
	}
	
	
	// Get Methods
	public String getName() {
		return name;
	}

	public String getPostCode() {
		return postCode;
	}
	
	public LocalTime getOpeningTime() {
		return openingTime;
	}
	public LocalTime getClosingTime() {
		return closingTime;
	}
	
	
	// Methods to manage staff

    public void addStaff(Staff staff) {
        this.staff.add(staff);
        System.out.println("A new staff has been added" + staff);
    }

    public void removeStaff(Staff staff) {
        this.staff.remove(staff);
        System.out.println(staff + " has been removed from the system!");
    }

    public void searchStaff(String ref) {
		for(Staff staff : staff) {
			if (staff.getStaffRef().equals(ref)) {
				System.out.println(ref + " has been located");				
			}
		}    
    }

    // Methods to manage pets
    
    public void addPet(Pet pet) {
        this.pets.add(pet);
        System.out.println("Pet has been added.");
    }
    
    // Method to remove the pet
    public void removePet(Pet pet) {
        this.pets.remove(pet);
        System.out.println("Pet has been removed.");
    }

    public void searchPet(String ref) {
		for(Pet pet : pets) {
			if(pet.getPetRef().equals(ref)) {
				System.out.println("Pet with Reference: "+ ref + " Located.");
			}
		}
        
    }
    
    // Methods to manage bookings
    public void addBooking(Booking booking) {
    	bookings.add(booking);
    	System.out.println("Booking Successful!");
    }
    
    public void removeBooking(Booking booking) {
    	bookings.remove(booking);
    	System.out.println("Booking Removed Successfully");
    	
    }
    
    public void findBooking(String UUID) {
    	for(Booking booking : bookings) {
    		if(booking.getUUID().equals(UUID)) {
    			System.out.println("Booking Ref: "+ UUID + " Located.");
    		}
    	}
    }
    
    // Get methods for lists
	public List<Staff> getStaffList() {
		return staff;
	}
	public List<Pet> getPets() {
		return pets;
	}
	public List<Booking> getBookings() {
		return bookings;
	}
}
