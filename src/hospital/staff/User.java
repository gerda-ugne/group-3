package hospital.staff;

import hospital.undo_redo.UndoRedoExecutor;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.Serializable;

/**
 * Abstract class that defines the user of the system.
 * This class is later extended into classes of Professional and Administrator.
 *
 */
public abstract class User implements Serializable, UndoRedoExecutor {
    /**
     * Static counter to generate unique IDs
     */
    private static long counter = 0;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The unique ID of User.
     */
    private final long id;

    /**
     * The office name/number of the user.
     */
    private String office;

    /**
     * The role of the user, e.g. nurse, dermatologist, administrator etc.
     */
    private String role;

    /**
     * Encrypted password of the user, which is used to log in.
     */
    protected String encryptedPassword;

    /**
     * Username used in the login system.
     */
    protected String username;


    /**
     * Constructor with no parameters for User class
     */
    public User() {
        this("<undefined>", "<undefined>", "<undefined>", "<undefined>");
    }

    /**
     * Constructor for User class
     * @param firstName name of User
     * @param lastName last name of User
     * @param role role of User
     */
    public User(String firstName, String lastName, String role) {
        this(firstName, lastName, role, "<undefined>");
    }

    /**
     * Constructor for User class
     * @param firstName name of User
     * @param lastName last name of User
     * @param role role of User
     * @param office office of User
     */
    public User(String firstName, String lastName, String role, String office) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.office = office;
        this.id = counter++;
        setPassword("default");
        username = (firstName + lastName).toLowerCase();
    }


    /**
     * Getter method for the username
     * @return username of User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for the username
     * @param username username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Getter of the first name of the user.
     * @return the first name of the user.
     */
    public String getFirstName() {
        return this.firstName;
    }


    /**
     * Sets the first name of the user.
     * @param firstName The first name to set to the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Getter of the last name of the user.
     * @return the last name of the user.
     */
    public String getLastName() {
        return this.lastName;
    }



    /**
     * Sets the last name of the user.
     * @param lastName the last name to set to the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    /**
     * Getter of User's office
     * @return User's office name/number
     */
    public String getOffice() {
        return this.office;
    }


    /**
     * Sets the name/number of User's office.
     * @param office The name/number to set User office to.
     */
    public void setOffice(String office) {
        this.office = office;
    }


    /**
     * Getter of User's role.
     * @return the role of User.
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Sets the role of User.
     * @param role The role to set to the user.
     */
    public void setRole(String role) {
        this.role = role;
    }


    /**
     * Sets a new password for User.
     * The set password is encrypted in the password field.
     *
     * @param password password to be set
     */
    public void setPassword(String password)
    {
        StrongPasswordEncryptor encryption = new StrongPasswordEncryptor();
        encryptedPassword = encryption.encryptPassword(password);

    }

    /**
     * Checks if the entered password is true
     *
     * @param input user's input
     * @return true/false whether the passwords match
     */
    public boolean checkPassword(String input)
    {
        StrongPasswordEncryptor encryption = new StrongPasswordEncryptor();
        return encryption.checkPassword(input, encryptedPassword);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + id + ")";
    }

    /**
     * Updates the username of User.
     * Method is used in case of first or last name change
     * when updating the details.
     *
     */
    public void updateUsername()
    {
        username = (firstName + lastName).toLowerCase();
    }


    /**
     * Getter method for the user's id
     * @return id of the user
     */
    public long getId() {
        return id;
    }

}
