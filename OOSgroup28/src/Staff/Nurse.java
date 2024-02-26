package Staff;

import SurgerySystem.Surgery;

public class Nurse extends Staff {
     
    /**
    * Constructor to initialise a Staff object
    * @param ref: The reference code for the staff
    * @param name: The name of the staff
    * @param primarySurgery: The primary surgery where the staff works
    */
	
public Nurse(String ref, String name, Surgery primarySurgery) {
    super(ref, name, primarySurgery);
}

}