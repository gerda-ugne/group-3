package hospital.staff;

import java.io.Serializable;
import java.util.*;

/**
 * Treatment type class defines default treatments
 * available at the hospital.
 *
 * Singleton approach is used.
 */
public class TreatmentType implements Serializable {

    /**
     * Set that contains default treatments
     */
    private static final List<TreatmentType> treatmentTypes = new ArrayList<TreatmentType>();

    /**
     * Label of the treatment
     */
    private final String label;

    /**
     * Role requirement for the treatment
     */
    private final List<Role> requiredRoles;

    /**
     * Private constructor for the TreatmentType class.
     * @param label label of the treatment
     * @param requiredRoles list of required roles for the treatment
     */
    private TreatmentType(String label, List<Role> requiredRoles) {
        this.label = label;
        this.requiredRoles = requiredRoles;
    }

    //adds basic treatment types to the list
    static {
        treatmentTypes.add(new TreatmentType("Routine Checkup", Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP"))));
        treatmentTypes.add(new TreatmentType("Emergency Appointment",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("PhysicianAssistant"),Role.valueOf("OccupationalTherapist"))));
        treatmentTypes.add(new TreatmentType("Mental Health Services",Arrays.asList(Role.valueOf("Therapist"))));
        treatmentTypes.add(new TreatmentType("Vaccinations",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Nurse"))));
        treatmentTypes.add(new TreatmentType("Eye care",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Optometrist"))));
        treatmentTypes.add(new TreatmentType("X-ray scan",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP"))));
        treatmentTypes.add(new TreatmentType("Radiology",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP"))));
        treatmentTypes.add(new TreatmentType("Sick note",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP"))));
        treatmentTypes.add(new TreatmentType("Dental Appointment",Arrays.asList(Role.valueOf("ClinicalAssistant"),Role.valueOf("Dentist"))));
        treatmentTypes.add(new TreatmentType("Surgery",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Nurse"), Role.valueOf("Surgeon"), Role.valueOf("ClinicalAssistant"), Role.valueOf("PhysicianAssistant"))));
        treatmentTypes.add(new TreatmentType("Minor Surgery",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Surgeon"), Role.valueOf("ClinicalAssistant"))));
        treatmentTypes.add(new TreatmentType("Sexual Health Services",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Nurse"), Role.valueOf("ClinicalAssistant"))));
    }

    /**
     * Getter method for the treatment types.
     * @return treatment types
     */
    public static List<TreatmentType> getTreatmentTypes(){

        List<TreatmentType> newList = new ArrayList<>(treatmentTypes);
        newList.sort(Comparator.comparing(o -> o.label));

        return treatmentTypes;
    }

    /**
     * Adds a new treatment type to the set.
     * @param label treatment label
     * @param requiredRoles treatment professional requirements
     */
    public static void addTreatmentType(String label, List<Role> requiredRoles) {
        treatmentTypes.add(new TreatmentType(label, requiredRoles));
    }

    /**
     * Adds a set of treatment types to the set.
     * Useful when making backups.
     * @param toAdd treatment list to be added
     */
    public static void addAllTreatmentTypes(List<TreatmentType> toAdd) {
      treatmentTypes.addAll(toAdd);
    }

    /**
     * Prints all the available treatment types
     */
    public static void displayTreatments() {
        int counter = 0;
        for (TreatmentType treatment : getTreatmentTypes()) {
            System.out.println(counter + ". " + treatment.label);
            counter++;

        }
    }

    /**
     * Searches for a treatment type with the same label.
     * @param label - treatment type
     * @return treatment if found, null if not
     */
    public static TreatmentType searchForTreatment(String label)
    {
        for (TreatmentType treatment: treatmentTypes
             ) {

            if(treatment.label.equals(label)) return treatment;
        }

        return  null;
    }

    /**
     * getter method for the treatment type's label
     * @return the treatment type's label
     */
    public String getLabel() {
        return label;
    }

    /**
     * getter method for the treatment type's required roles
     * @return the treatment type's required roles
     */
    public List<Role> getRequiredRoles() {
        return requiredRoles;
    }
}
