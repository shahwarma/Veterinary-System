package Booking;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import Pet.Pet;
import Staff.Staff;

public class Booking implements Serializable {
    private UUID ref;
    private Staff staff;
    private Pet pet;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    /**
     * 
     * @param ref: The reference for the booking (Auto generated using UUID)
     * @param staff: The reference of the staff assigned to the booking
     * @param pet: The reference of the pet assigned to the booking
     * @param startTime: The date and start time of the booking
     * @param endTime: The date and end time of the booking
     */
    
    public Booking(UUID ref, Staff staff, Pet pet, LocalDateTime startTime, LocalDateTime endTime) {
    	this.ref = UUID.randomUUID();
    	this.staff = staff;
    	this.pet = pet;
    	this.startTime = startTime;
    	this.endTime = endTime;    	
    	staff.addBooking(this);
    	
    }
    // Get method for the booking reference using UUID
    public UUID getUUID() {
    	return ref;
    }
    
    // Get method for staff reference
    public Staff getStaff() {
    	return staff;
    }
    
    // Get method for pet reference
    public Pet getPet() {
    	return pet;
    }
    
    // Get method to retrieve the start time of the booking
    public LocalDateTime getStartTime() {
    	return startTime;
    }
    
    // Get method to retrieve the end time of the booking
    public LocalDateTime getEndTime() {
    	return endTime;
    }
    // Method to represent all the booking together
    public String toString() {
    	return "Booking Reference: " + ref +  ", Staff: " + staff.getStaffName() + ". Pet: " + pet.getPetName() + ", Start Time: " + startTime + ", End Time: " + endTime;
    }
}
