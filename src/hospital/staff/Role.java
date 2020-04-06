package hospital.staff;


/**
 * Enum that defines available staff roles
 * in the hospital.
 *
 */
public enum Role{

    Nurse,
    Surgeon,
    ClinicalAssistant,
    GP,
    PhysicianAssistant,
    OccupationalTherapist,
    Therapist,
    Optometrist,
    Physiotherapist,
    Administrator,
    Dentist;

    /**
     * Checks if the role exists within enum.
     * @param role role to be checked
     * @return true/false whether the role exists.
     */
    public static boolean checkIfRoleExists(String role)
    {
        for (Role current : Role.values()) {

            if(current.toString().equals(role)) return true;
        }

        return false;
    }


    /**
     * Prints out all the enums.
     */
    public static void printRoles()
    {
        System.out.println("Available roles:\n");
        for (Role current : Role.values()) {

            System.out.println(current.toString());
        }
    }
}
