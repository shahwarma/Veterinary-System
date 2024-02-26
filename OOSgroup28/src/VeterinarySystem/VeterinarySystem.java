package VeterinarySystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import Booking.Booking;
import Pet.Pet;
import Staff.Nurse;
import Staff.Staff;
import Staff.Surgeon;
import SurgerySystem.Surgery;


public class VeterinarySystem implements Serializable{
	private static Scanner input = new Scanner(System.in);
	
	
	private List<Surgery> surgeries;
	private List<Booking> bookings;
	private List<Staff> staff;
	private List<Pet> pets;
    
	public VeterinarySystem() {
        surgeries = new ArrayList<>();
        staff = new ArrayList<>(); 
        pets = new ArrayList<>();
    }
	// Method to add the surgery
    public void addSurgery(Surgery surgery) {
        surgeries.add(surgery);
    }
    
    // Method to remove the surgery
    public void removeSurgery(Surgery surgery) {
        surgeries.remove(surgery);
    }
    
    // Method to search surgery
    public List<Surgery> searchSurgery(String name) {
        List<Surgery> result = new ArrayList<>();
        for (Surgery surgery : surgeries) {
            if (surgery.getName().equals(name)) {
                result.add(surgery);
            }
        }
        return result;
    }
    private static final String FILE_NAME = ".ser";
    
    // Method to serialize
    public void serialize() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this);
            System.out.println("Serialization successful. Data saved to " + FILE_NAME );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to deserialize
    public static VeterinarySystem deserialize() {
        VeterinarySystem vetSystem = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            vetSystem = (VeterinarySystem) ois.readObject();
            System.out.println("Deserialization successful. Data loaded from " + FILE_NAME + " file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vetSystem;
    }
    public void displayMenu() {
    	System.out.println("---- Veterinary System ----");
    	System.out.println("---- Main Menu ----");
        System.out.println("1. Bookings Menu");
        System.out.println("2. Staff Menu");
        System.out.println("3. Pets Menu");
        System.out.println("4. Display Surgeries");
        System.out.println("5. Exit");
        System.out.println("Enter your choice: ");
    }

    public void handleUserInput(int choice) {
        

        switch (choice) {
            case 1:
                manageBookings();
                break;
            case 2:
            	manageStaff();
                break;
            case 3:
            	managePets();
                break;
            case 4:
            	displaySurgeries();
                break;
            case 5:
                saveAndExit();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    public void saveAndExit() {
        // Save data before exiting
        serialize();
        System.out.println("Exiting program. Goodbye!!");
        System.exit(0);
    }
	private void manageBookings() {
		while (true) {
            System.out.println("\n---- Bookings Menu ----");
            System.out.println("1. Add Booking");
            System.out.println("2. Remove Booking");
            System.out.println("3. Display All Bookings");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int bookingChoice;
            try {
                bookingChoice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); 
                continue;
            }

            switch (bookingChoice) {
                case 1:
                    addBooking();
                    break;
                case 2:
                    removeBooking();
                    break;
                case 3:
                    displayAllBookings();
                    break;
                case 4:
                    return; // Goes back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
		
	}
	
	public void addBooking() {
	    System.out.println("---- New Booking ----");

	    // Method to display all the surgeries in system and get user input for selection
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        // Display staff members and pets in the selected surgery
	        displayStaffAndPets(selectedSurgery);

	        // Method to select staff and pets and get user input for selection
	        Staff selectedStaff = selectStaff(selectedSurgery);
	        Pet selectedPet = selectPet(selectedSurgery);

	        if (selectedStaff != null && selectedPet != null) {
	            // Takes user input for booking date and time
	            LocalDateTime startTime = getDateTimeInput("Enter Start Date and Time (yyyy-MM-dd HH:mm): ");
	            LocalDateTime endTime = getDateTimeInput("Enter End Date and Time (yyyy-MM-dd HH:mm): ");

	            // Create new Booking object
	            Booking newBooking = new Booking(UUID.randomUUID(), selectedStaff, selectedPet, startTime, endTime);

	            // Add new booking to the associated surgery
	            selectedSurgery.addBooking(newBooking);
	        }
	    }
	}
    
	private void displayStaffAndPets(Surgery surgery) {
	    System.out.println("Staff Members in " + surgery.getName() + ":");
	    List<Staff> staffList = surgery.getStaffList();
	    for (int i = 0; i < staffList.size(); i++) {
	        System.out.println((i + 1) + ". " + staffList.get(i).getStaffName());
	    }

	    System.out.println("Pets in " + surgery.getName() + ":");
	    List<Pet> petList = surgery.getPets();
	    for (int i = 0; i < petList.size(); i++) {
	        System.out.println((i + 1) + ". Pet Ref: " + petList.get(i).getPetRef() +
	                ", Name: " + petList.get(i).getPetName() +
	                ", SpeciesName: " + petList.get(i).getSpeciesName() +
	                ", regDate: " + petList.get(i).getRegDate());
	    }
	}
	private LocalDateTime getDateTimeInput(String string) {
	    LocalDateTime dateTime = null;
	    boolean isValidInput = false;

	    while (!isValidInput) {
	        try {
	            System.out.println("Enter Date and Time (yyyy-MM-dd HH:mm): ");
	            String dateTimeString = input.nextLine();
	            dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	            isValidInput = true;
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid date and time format. Please use the format 'yyyy-MM-dd HH:mm'.");
	        }
	    }

	    return dateTime;
	}

	private Pet selectPet(Surgery surgery) {
        System.out.println("Available Pets:");
        List<Pet> petList = surgery.getPets();

        for (int i = 0; i < petList.size(); i++) {
            System.out.println((i + 1) + ". Pet Ref: " + petList.get(i).getPetRef() +
                    ", Name: " + petList.get(i).getPetName() +
                    ", SpeciesName: " + petList.get(i).getSpeciesName() +
                    ", regDate: " + petList.get(i).getRegDate());
        }

        int petChoice;
        try {
            System.out.println("Enter the number of the pet: ");
            petChoice = input.nextInt();
            input.nextLine(); 
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            input.nextLine(); 
            return null;
        }

        if (petChoice >= 1 && petChoice <= petList.size()) {
            return petList.get(petChoice - 1);
        } else {
            System.out.println("Invalid choice. Please try again.");
            return null;
        }
    
	}
	private Staff selectStaff(Surgery surgery) {
        System.out.println("Available Staff:");
        List<Staff> staffList = surgery.getStaffList();

        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ". " + staffList.get(i).getStaffName());
        }

        int staffChoice;
        try {
            System.out.println("Enter the number of the staff: ");
            staffChoice = input.nextInt();
            input.nextLine(); 
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            input.nextLine(); 
            return null;
        }

        if (staffChoice >= 1 && staffChoice <= staffList.size()) {
            return staffList.get(staffChoice - 1);
        } else {
            System.out.println("Invalid choice. Please try again.");
            return null;
        }
    }

	public void removeBooking() {
	    // Method to display surgeries and select the surgery wanted
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        // Get bookings list in the selected surgery
	        List<Booking> bookingList = selectedSurgery.getBookings();

	        if (!bookingList.isEmpty()) {
	            // Display bookings in the selected surgery
	            System.out.println("Bookings in " + selectedSurgery.getName() + ":");
	            for (int i = 0; i < bookingList.size(); i++) {
	                System.out.println((i + 1) + ". " + bookingList.get(i).getUUID() + " - Pet: " + bookingList.get(i).getPet().getPetName() + ", Staff: " + bookingList.get(i).getStaff().getStaffName());
	            }

	            // Take user input for booking index to be removed
	            System.out.println("Enter the number of the Booking to be removed: ");
	            int bookingChoice = input.nextInt();

	            // Check if the user choice is valid
	            if (bookingChoice >= 1 && bookingChoice <= bookingList.size()) {
	                // Remove selected booking from associated surgery
	                Booking bookingToRemove = bookingList.get(bookingChoice - 1);
	                selectedSurgery.removeBooking(bookingToRemove);

	                
	            } else {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        } else {
	            System.out.println("There are no bookings in " + selectedSurgery.getName() + ".");
	        }
	    }
	}
	
	
	public void displayAllBookings() {
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        List<Booking> bookingList = selectedSurgery.getBookings();
	       
	        System.out.println("All Bookings in " + selectedSurgery.getName() + ":");
	        for (Booking booking : bookingList) {
	            System.out.println(booking);
	        }
	    }
	}
	
	private void manageStaff() {
	       while (true) {
	            System.out.println("\n---- Staff Menu ----");
	            System.out.println("1. Add Staff");
	            System.out.println("2. Remove Staff");
	            System.out.println("3. Search Staff");
	            System.out.println("4. Display All Staff");
	            System.out.println("5. Check Availablity");
	            System.out.println("6. Back to Main Menu");
	            System.out.print("Enter your choice: ");

	            int staffChoice;
	            try {
	                staffChoice = input.nextInt();
	            } catch (InputMismatchException e) {
	                System.out.println("Invalid input. Please enter a number.");
	                input.nextLine(); 
	                continue;
	            }

	            switch (staffChoice) {
	                case 1:
	                    addStaff();
	                    break;
	                case 2:
	                    removeStaff();
	                    break;
	                case 3:
	                    searchStaff();
	                    break;
	                case 4:
	                	displayAllStaff();
	                	break;
	                case 5:
	                	checkAvailability();
	                	break;
	                case 6:
	                    return; // Back to main menu
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }    
		
	}
	
	public void addStaff() {
	    // Generates a random 3-digit staff reference
	    String staffRef = generateStaffRef();

	    System.out.println("Enter Staff Name: ");
	    String staffName = input.next();

	    System.out.println("Are they a Nurse or Doctor? Enter 'N' for Nurse, 'D' for Doctor: ");
	    String staffType = input.next().toUpperCase();

	    if ("N".equals(staffType) || "D".equals(staffType)) {
	        // Method to display surgeries and select the primary surgery
	        Surgery primarySurgery = selectPrimarySurgery();

	        // Adjust the staff's name based on their type (E.g. If Doctor adds Dr. in front and if Nurse adds Nurse in front of name)
	        if ("D".equals(staffType)) {
	            staffName = "Dr. " + staffName;
	        } else if ("N".equals(staffType)) {
	            staffName = "Nurse " + staffName;
	        }
	        // Create a new Staff object
	        Staff newStaff = new Staff(staffRef, staffName, primarySurgery);

	        // Add new staff to the associated surgery
	        primarySurgery.addStaff(newStaff);

	        System.out.println("Staff added successfully!");
	    } else {
	        System.out.println("Invalid staff type. Please enter 'N' for Nurse or 'D' for Doctor.");
	    }
	}
	
	private String generateStaffRef() {
        Random random = new Random();
        int staffRefNumber = random.nextInt(900) + 100; // Generates random 3 digit number
        return String.valueOf(staffRefNumber);
    }
	
	private Surgery selectPrimarySurgery() {
	    System.out.println("Available Surgeries:");
	    
	    // Display list of surgeries
	    for (int i = 0; i < surgeries.size(); i++) {
	        System.out.println((i + 1) + ". " + surgeries.get(i).getName());
	    }

	    // Take user input for selecting a surgery
	    int surgeryChoice;
	    try {
	        System.out.println("Enter the number of the primary surgery: ");
	        surgeryChoice = input.nextInt();
	        input.nextLine();  
	    } catch (InputMismatchException e) {
	        System.out.println("Invalid input. Please enter a number.");
	        input.nextLine(); 
	        return null;
	    }

	    // Check if the user choice is within the valid range
	    if (surgeryChoice >= 1 && surgeryChoice <= surgeries.size()) {
	        // Return the selected surgery
	        return surgeries.get(surgeryChoice - 1);
	    } else {
	        System.out.println("Invalid choice. Please try again.");
	        return null;
	    }
	}

	public void removeStaff() {
	    // Method to display surgeries and select the surgery wanted
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        // Get list of staff in the selected surgery
	        List<Staff> staffList = selectedSurgery.getStaffList();

	        if (!staffList.isEmpty()) {
	            // Display staff in selected surgery
	            System.out.println("Staff in " + selectedSurgery.getName() + ":");
	            for (int i = 0; i < staffList.size(); i++) {
	                System.out.println((i + 1) + ". " + staffList.get(i).getStaffName() + " (Ref: " + staffList.get(i).getStaffRef() + ")");
	            }

	            // Take user input for staff reference to be removed
	            System.out.println("Enter the number of the Staff to be removed: ");
	            int staffChoice = input.nextInt();

	            // Check if the user choice is within the valid range
	            if (staffChoice >= 1 && staffChoice <= staffList.size()) {
	                // Remove the selected staff from surgery
	                Staff staffToRemove = staffList.get(staffChoice - 1);
	                selectedSurgery.removeStaff(staffToRemove);

	                System.out.println("Staff removed successfully!");
	            } else {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        } else {
	            System.out.println("There are no staff in " + selectedSurgery.getName() + ".");
	        }
	    }
	}
	
	private Surgery selectSurgery() {
        System.out.println("Available Surgeries:");

        // Display a list of surgeries
        for (int i = 0; i < surgeries.size(); i++) {
            System.out.println((i + 1) + ". " + surgeries.get(i).getName());
        }

        // Take user input for selecting a surgery
        int surgeryChoice;
        try {
            System.out.println("Enter the number of the surgery: ");
            surgeryChoice = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            input.nextLine(); 
            return null;
        }

        // Check if the user choice is within the valid range
        if (surgeryChoice >= 1 && surgeryChoice <= surgeries.size()) {
            // Return the selected surgery
            return surgeries.get(surgeryChoice - 1);
        } else {
            System.out.println("Invalid choice. Please try again.");
            return null;
        }
    }

	public void searchStaff() {
	    // Take user input for staff reference to search
	    System.out.println("Enter the Staff Reference to search: ");
	    String staffRefToSearch = input.nextLine();

	    // Method to display surgeries and select the surgery
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        // Get the list of staff in the selected surgery
	        List<Staff> staffList = selectedSurgery.getStaffList();

	        // Find the staff with the specified reference
	        boolean staffFound = false;
	        for (Staff staff : staffList) {
	            if (staff.getStaffRef().equals(staffRefToSearch)) {
	                System.out.println("Staff found:\n" + staff);
	                staffFound = true;
	                break;
	            }
	        }

	        if (!staffFound) {
	            System.out.println("Staff with reference " + staffRefToSearch + " not found.");
	        }
	    }
	}
	
	public void displayAllStaff() {
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        List<Staff> staffList = selectedSurgery.getStaffList();

	        System.out.println("All Staff in " + selectedSurgery.getName() + ":");
	        for (Staff staff : staffList) {
	            System.out.println(staff);
	        }
	    }
	}
	
	public void checkAvailability() {
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        System.out.println("Enter the Start Date and Time to check availability (yyyy-MM-dd HH:mm): ");
	        String startDateTimeString = input.nextLine();
	        LocalDateTime startTime = LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

	        System.out.println("Enter the End Date and Time to check availability (yyyy-MM-dd HH:mm): ");
	        String endDateTimeString = input.nextLine();
	        LocalDateTime endTime = LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

	        // Get the list of bookings in the selected surgery
	        List<Booking> bookingList = selectedSurgery.getBookings();

	        // Check availability for the specified time slot
	        boolean isAvailable = true;
	        for (Booking booking : bookingList) {
	            LocalDateTime bookingStartTime = booking.getStartTime();
	            LocalDateTime bookingEndTime = booking.getEndTime();

	            // Check for overlap
	            if ((startTime.isAfter(bookingStartTime) && startTime.isBefore(bookingEndTime)) ||
	                (endTime.isAfter(bookingStartTime) && endTime.isBefore(bookingEndTime)) ||
	                (startTime.isBefore(bookingStartTime) && endTime.isAfter(bookingEndTime))) {
	                isAvailable = false;
	                break;
	            }
	        }

	        if (isAvailable) {
	            System.out.println("The specified time slot is available.");
	        } else {
	            System.out.println("The specified time slot is not available. Please choose another time.");
	        }
	    }
	}
	
	private void managePets() {
        while (true) {
            System.out.println("\n---- Pets Menu ----");
            System.out.println("1. Add Pet");
            System.out.println("2. Remove Pet");
            System.out.println("3. Search Pets");
            System.out.println("4. View All Pets");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int petChoice;
            try {
                petChoice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); 
                continue;
            }

            switch (petChoice) {
                case 1:
                    addPet();
                    break;
                case 2:
                    removePet();
                    break;
                case 3:
                    searchPet();
                    break;
                case 4:
                    viewAllPets();
                    break;
                case 5:
                    return; // Main Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }    
		
    }

	public void addPet() {
		
		System.out.println("Enter Pet Name: ");
		String petName = input.next();

		System.out.println("Enter Pet Species Name: ");
		String speciesName = input.next();

		// Method to display surgeries and select the surgery
		Surgery selectedSurgery = selectSurgery();

		if (selectedSurgery != null) {
		    // Generate a 5-digit pet reference number automatically
		    String petRef = generatePetRef();

		    // Create a new Pet object
		    Pet newPet = new Pet(petRef, petName, speciesName, LocalDate.now());

		    // Add the new pet to the associated surgery
		    selectedSurgery.addPet(newPet);

		    System.out.println("Pet added successfully!");
		}
	}
	private String generatePetRef() {
        // Generate a random 5-digit pet reference number
        Random random = new Random();
        int petRefNumber = 10000 + random.nextInt(90000);
        return String.valueOf(petRefNumber);
    }
	
	public void removePet() {
	    // Method to display surgeries and select the surgery
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        // Get the list of pets in the selected surgery
	        List<Pet> petList = selectedSurgery.getPets();

	        if (!petList.isEmpty()) {
	            // Display pets in the selected surgery
	            System.out.println("Pets in " + selectedSurgery.getName() + ":");
	            for (int i = 0; i < petList.size(); i++) {
	                System.out.println((i + 1) + ". " + petList.get(i).getPetName() + " (Ref: " + petList.get(i).getPetRef() + ")");
	            }

	            // Take user input for pet reference to be removed
	            System.out.println("Enter the number of the Pet to be removed: ");
	            int petChoice = input.nextInt();

	            // Check if the user choice is within the valid range
	            if (petChoice >= 1 && petChoice <= petList.size()) {
	                // Remove the selected pet from the associated surgery
	                Pet petToRemove = petList.get(petChoice - 1);
	                selectedSurgery.removePet(petToRemove);

	                System.out.println("Pet removed successfully!");
	            } else {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        } else {
	            System.out.println("There are no pets in " + selectedSurgery.getName() + ".");
	        }
	    }
	}
	public void searchPet() {
	    // Take user input for pet reference to search
	    System.out.println("Enter the Pet Reference to search: ");
	    String petRefToSearch = input.nextLine();

	    // Method to display surgeries and select the surgery
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        // Get the list of pets in the selected surgery
	        List<Pet> petList = selectedSurgery.getPets();

	        // Find the pet with the specified reference
	        boolean petFound = false;
	        for (Pet pet : petList) {
	            if (pet.getPetRef().equals(petRefToSearch)) {
	                System.out.println("Pet found:\n" + pet);
	                petFound = true;
	                break;
	            }
	        }

	        if (!petFound) {
	            System.out.println("Pet with reference " + petRefToSearch + " not found.");
	        }
	    }
	}
	
	public void viewAllPets() {
	    Surgery selectedSurgery = selectSurgery();

	    if (selectedSurgery != null) {
	        List<Pet> petList = selectedSurgery.getPets();

	        System.out.println("All Pets in " + selectedSurgery.getName() + ":");
	        for (Pet pet : petList) {
	            System.out.println(pet);
	        }
	    }
	}
	
	public void displaySurgeries() {
	    System.out.println("List of Surgeries:");
	    for (int i = 0; i < surgeries.size(); i++) {
	        System.out.println((i + 1) + ". " + surgeries.get(i).getName());
	    }

	    // Take user input for selecting a surgery
	    int surgeryChoice;
	    try {
	        System.out.println("Enter the number of the surgery to view details: ");
	        surgeryChoice = input.nextInt();
	        input.nextLine();  // Consume the newline character
	    } catch (InputMismatchException e) {
	        System.out.println("Invalid input. Please enter a number.");
	        input.nextLine();  // Consume the invalid input
	        return;
	    }

	    // Check if the user choice is within the valid range
	    if (surgeryChoice >= 1 && surgeryChoice <= surgeries.size()) {
	        Surgery selectedSurgery = surgeries.get(surgeryChoice - 1);
	        displaySurgeryDetails(selectedSurgery);
	    } else {
	        System.out.println("Invalid choice. Please try again.");
	    }
	}

	private void displaySurgeryDetails(Surgery surgery) {
	    System.out.println("Surgery Details:");
	    System.out.println("Name: " + surgery.getName());
	    System.out.println("Post Code: " + surgery.getPostCode());
	    System.out.println("Opening Time: " + surgery.getOpeningTime());
	    System.out.println("Closing Time: " + surgery.getClosingTime());
	    System.out.println("\nStaff Members:");
	    for (Staff staff : surgery.getStaffList()) {
	        System.out.println("- " + staff.getStaffName());
	    }
	    System.out.println("\nPets:");
	    for (Pet pet : surgery.getPets()) {
	        System.out.println("- " + pet.getPetName() + " (Ref: " + pet.getPetRef() + ")");
	    }
	    System.out.println("\nBookings:");
	    for (Booking booking : surgery.getBookings()) {
	        System.out.println("- " + booking);
	    }
	}
	public static void main(String[] args) {
        VeterinarySystem vetSystem = new VeterinarySystem();
        
        // Try to deserialize existing data
        vetSystem = VeterinarySystem.deserialize();

        if (vetSystem == null) {
            // If deserialization fails create new instance
            vetSystem = new VeterinarySystem();

            // Creating sample surgeries
            Surgery surgery1 = new Surgery("Tilted Towers Surgery", "CH662PB", LocalTime.of(9, 0), LocalTime.of(17, 0));
            Surgery surgery2 = new Surgery("Retail Row Surgery", "CH52UA", LocalTime.of(10, 0), LocalTime.of(18, 0));

            vetSystem.addSurgery(surgery1);
            vetSystem.addSurgery(surgery2);

            // Creating sample staff
            Surgeon surgeon1 = new Surgeon("001", "Dr. Ronaldo", surgery1);
            Nurse nurse1 = new Nurse("002", "Nurse Agbayani", surgery1);

            Surgeon surgeon2 = new Surgeon("003", "Dr. Messi", surgery2);
            Nurse nurse2 = new Nurse("004", "Nurse Ramirez", surgery2);

            surgery1.addStaff(surgeon1);
            surgery1.addStaff(nurse1);

            surgery2.addStaff(surgeon2);
            surgery2.addStaff(nurse2);

            // Creating sample pets
            Pet pet1 = new Pet("00001", "Tom", "Cat", LocalDate.of(2024, 1, 1));
            Pet pet2 = new Pet("00002", "Luna", "Dog", LocalDate.of(2023, 2, 15));
            Pet pet3 = new Pet("00003", "Nemo", "Fish", LocalDate.of(2023, 3, 30));

            surgery1.addPet(pet1);
            surgery1.addPet(pet2);

            surgery2.addPet(pet3);

            // Creating sample bookings
            LocalDateTime startDateTime1 = LocalDateTime.of(2024, 2, 12, 10, 0);
            LocalDateTime endDateTime1 = LocalDateTime.of(2024, 2, 12, 12, 0);

            Booking booking1 = new Booking(UUID.randomUUID(), surgeon1, pet1, startDateTime1, endDateTime1);
            surgery1.addBooking(booking1);

            LocalDateTime startDateTime2 = LocalDateTime.of(2024, 3, 15, 14, 0);
            LocalDateTime endDateTime2 = LocalDateTime.of(2024, 3, 15, 16, 0);

            Booking booking2 = new Booking(UUID.randomUUID(), nurse1, pet2, startDateTime2, endDateTime2);
            surgery1.addBooking(booking2);

            LocalDateTime startDateTime3 = LocalDateTime.of(2024, 4, 5, 11, 0);
            LocalDateTime endDateTime3 = LocalDateTime.of(2024, 4, 5, 13, 0);

            Booking booking3 = new Booking(UUID.randomUUID(), surgeon2, pet3, startDateTime3, endDateTime3);
            surgery2.addBooking(booking3);

            // Serialize the initial data so it saves
            vetSystem.serialize();
        }
        

        while (true) {
            vetSystem.displayMenu();

            // Get user input
            int choice;
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // Consume the invalid input
                continue;
            }

            // Handle user input
            vetSystem.handleUserInput(choice);
        }
        
    }
}
