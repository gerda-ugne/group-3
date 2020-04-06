package hospital.staff;

import java.util.*;

/**
 * Treatment type class defines default treatments
 * available at the hospital.
 *
 * Singleton approach is used.
 */
public class TreatmentType {

    /**
     * Set that contains default treatments
     */
    private static final Set<TreatmentType> treatmentTypes = new HashSet<>();

    /**
     * Label of the treatment
     */
    private String label;

    /**
     * Role requirement for the treatment
     */
    private List<Role> requiredRoles;

    /**
     * Private constructor for the TreatmentType class.
     * @param label label of the treatment
     * @param requiredRoles list of required roles for the treatment
     */
    private TreatmentType(String label, List<Role> requiredRoles) {
        this.label = label;
        this.requiredRoles = requiredRoles;
    }


    static {
        treatmentTypes.add(new TreatmentType("<undefined>", null));
        treatmentTypes.add(new TreatmentType("Routine Checkup", Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP"))));
        treatmentTypes.add(new TreatmentType("Emergency Appointment",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("PhysicianAssistant"),Role.valueOf("OccupationalTherapist"))));
        treatmentTypes.add(new TreatmentType("Mental Health Services", Collections.singletonList(Role.valueOf("Therapist"))));
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
    public static Set<TreatmentType> getTreatmentTypes(){
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
     * @param toAdd treatment set to be added
     */
    public static void addAllTreatmentTypes(Set<TreatmentType> toAdd) {
      treatmentTypes.addAll(toAdd);
    }

    /**
     * Prints all the available treatment types
     */
    public static void displayTreatments()
    {
        int counter = 1;
        for (TreatmentType treatment: treatmentTypes
        ) {
            System.out.println(counter + ". " + treatment.label);
            counter ++;

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
}