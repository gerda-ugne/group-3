package hospital.staff;

/**
 * The Administrator class represents the administrator of the hospital.
 * The Administrator has different control options.
 *
 */
public class Administrator extends User{

    /**
     * Constructor of the Administrator class
     */
    public Administrator()
    {
        super("<undefined>", "<undefined>", "Administrator", "<undefined>");
        setUsername("admin");
    }

    /**
     * Constructor of the administrator class
     * @param firstName first name of the admin
     * @param lastName last name of the admin
     * @param office office of the admin
     */
    public Administrator(String firstName, String lastName, String office) {
        super(firstName, lastName, "Administrator", office);
        setUsername("admin");
    }


}
