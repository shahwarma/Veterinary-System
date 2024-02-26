package Staff;

import java.util.ArrayList;
import java.util.List;

import SurgerySystem.Surgery;

public class Surgeon extends Staff {


    private List<Surgery> assignedSurgeries;
     
    /**
    * @param ref: The reference code for the staff
    * @param name: The name of the staff
    * @param primarySurgery: The primary surgery where the staff works
    */

public Surgeon(String ref, String name, Surgery primarySurgery) {
    super(ref, name, primarySurgery);
    this.assignedSurgeries = new ArrayList<>();
    this.assignedSurgeries.add(primarySurgery);
}


}