package Staff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Booking.Booking;
import SurgerySystem.Surgery;

public class Staff implements Serializable {


    // Instance variables
    protected String ref;
    protected String name;
    protected Surgery primarySurgery;
    protected Set<Surgery> consultingSurgeries;
    List<Booking> bookings;

    /**
    * Constructor to initialise a Staff object
    * @param ref: The reference code for the staff
    * @param name: The name of the staff
    * @param primarySurgery: The primary surgery where the staff works
    */

    public Staff(String ref, String name, Surgery primarySurgery) {
        this.ref = ref;
        this.name = name;
        this.primarySurgery = primarySurgery;
        this.bookings = new ArrayList<>();
    }

    // Get methods for staff name and reference
    public String getStaffRef() {
        return ref;
    }

    public String getStaffName() {
        return name;
    }

    // Method to add booking to the bookings list
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    // Method to remove booking from booking list 
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

    // Method to represetn all staff information
    public String toString() {
        return "Staff Ref: " + ref + ", Name: " + name + ", Primary Surgery: " + primarySurgery.getName();
    }

}
