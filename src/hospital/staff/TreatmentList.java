package hospital.staff;


import java.util.*;

/**
 * Treatment class defines a map
 * of available treatments in the hospital, which is
 * linked to the needed roles.
 *
 * Treatments can be added and removed.
 */
public class TreatmentList {

    /**
     * Map of treatment types.
     * String defines treatment type,
     * List of Roles defines required professionals.
     */
    Map<String, List<Role>> treatments;

    /**
     * Constructor of the Treatment class.
     * Defines default treatments available at the hospital with the requirement of roles.
     */
    public TreatmentList()
    {
        treatments = new HashMap<>();
        treatments.put("Routine Checkup", Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP")));
        treatments.put("Emergency Appointment",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("PhysicianAssistant"),Role.valueOf("OccupationalTherapist")));
        treatments.put("Mental Health Services",Arrays.asList(Role.valueOf("Therapist")));
        treatments.put("Vaccinations",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Nurse")));
        treatments.put("Eye care",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Optometrist")));
        treatments.put("X-ray scan",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP")));
        treatments.put("Radiology",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP")));
        treatments.put("Sick note",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("GP")));
        treatments.put("Dental Appointment",Arrays.asList(Role.valueOf("ClinicalAssistant"),Role.valueOf("Dentist")));
        treatments.put("Surgery",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Nurse"), Role.valueOf("Surgeon"), Role.valueOf("ClinicalAssistant"), Role.valueOf("PhysicianAssistant")));
        treatments.put("Minor Surgery",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Surgeon"), Role.valueOf("ClinicalAssistant")));
        treatments.put("Sexual Health Services",Arrays.asList(Role.valueOf("Nurse"),Role.valueOf("Nurse"), Role.valueOf("ClinicalAssistant")));

    }

    /**
     * Adds a treatment type to the map.
     *
     * @param treatment treatment type to add
     * @param roles roles needed for the treatment
     */
    public void addTreatment(String treatment, List<Role> roles)
    {
        treatments.put(treatment,roles);
    }

    /**
     * Removes a specified treatment type from the map.
     *
     * @param treatment treatment to remove
     * @return true/false whether deletion was successful
     */
    public void deleteTreatment(String treatment)
    {
       treatments.remove(treatment);
    }

}
