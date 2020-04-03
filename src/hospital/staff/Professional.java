package hospital.staff;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jasypt.util.password.StrongPasswordEncryptor;



/**
 * Represents a professional in the hospital staff.
 */
public class Professional{

	/**
	 * Static counter to generate unique IDs
	 */
	private static long counter = 0;

	/**
	 * The unique ID of the professional.
	 */
	private final long id;

	// TODO add title

	/**
	 * The first name of the professional.
	 */
	private String firstName;

	/**
	 * The last name of the professional.
	 */
	private String lastName;

	/**
	 * The office name/number of the professional.
	 */
	private String office;

	/**
	 * The role of the professional, e.g. nurse, dermatologist, etc.
	 */
	private String role;

	/**
	 * The personal electronic diary of the professional with booked appointments.
	 */
	private ElectronicDiary diary;

	private TaskList tasks;

	/**
	 * The schedule when the professional is working in a week.
	 */
	private Map<DayOfWeek, WorkingHours> workingHours;

	/**
	 * Encrypted password of the professional, which is used to log in.
	 */
	private String encryptedPassword;

	/**
	 * Username used in the login system.
	 * Contains first name letter and last name
	 */
	private String username;

	/**
	 * Constructor with no parameters for the Professional class
	 */
	public Professional() {
		this("<undefined>", "<undefined>", "<undefined>", "<undefined>");
	}

	/**
	 * Constructor for the Professional class
	 * @param firstName name of the professional
	 * @param lastName last name of the professional
	 * @param role role of the professional
	 */
	public Professional(String firstName, String lastName, String role) {
		this(firstName, lastName, role, "<undefined>");
	}

	/**
	 * Constructor for the Professional class
	 * @param firstName name of the professional
	 * @param lastName last name of the professional
	 * @param role role of the professional
	 * @param office office of the professional
	 */
	public Professional(String firstName, String lastName, String role, String office) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.office = office;
		this.diary = new ElectronicDiary();
		this.tasks = new TaskList();
		this.workingHours = new HashMap<>(7);
		this.id = counter++;
		setPassword("default");
		username = this.firstName.substring(0) + lastName;
	}

	/**
	 * Getter method for the username
	 * @return username of the professional
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
	 * Getter of the ID of the professional.
	 *
	 * @return the unique ID of the professional.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Getter of the first name of the professional.
	 *
	 * @return the first name of the professional.
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name of the professional.
	 *
	 * @param firstName The first name to set to the professional.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter of the last name of the professional.
	 *
	 * @return the last name of the professional.
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name of the professional.
	 *
	 * @param lastName the last name to set to the professional.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Finds all available slots for a possible appointment
	 * and returns a List of available appointment slots with start
	 * ane end times specified.

	 * @param from data range to search from
	 * @param to data range to search to
	 * @return List of free slots for appointments that has data of
	 * start and end time
	 */
	public List<Appointment> searchAvailability(Date from, Date to) {

		//Start time is converted into seconds
		long startTime = from.getTime();

		//End time of an appointment is calculated
		long endTime = from.getTime() + Appointment.TREATMENT_DURATION;

		//New list for empty available slots is created
		List<Appointment> availableSlots = new ArrayList<Appointment>();

		//While there is more time to add empty appointments
		while (endTime < to.getTime()) {

			//Add an empty appointment with defined start and end time
			availableSlots.add(new Appointment(new Date(startTime), new Date(endTime)));

			//Adjust the time for the next instance
			startTime = endTime;
			endTime = startTime + Appointment.TREATMENT_DURATION;
		}

		// Check if an appointment is in the given time-range
		Predicate<Appointment> checkTimeRange = appointment -> {
			Date start = appointment.getStartTime();
			Date end = appointment.getEndTime();
			if (start.compareTo(from) <= 0 && start.compareTo(to) >= 0 &&
					end.compareTo(from) >= 0 && end.compareTo(to) <= 0) return true;
			else return false;
		};

		//Appointments are filtered to be in the provided time range
		List<Appointment> bookedAppointments = diary.sortByDate()
				.stream()
				.filter(checkTimeRange)
				.sorted()
				.collect(Collectors.toList());

		//Nested for loop: each slot is compared to the existing appointments.
		for (Appointment slot:
			 availableSlots) {

			for (Appointment bookedAppointment:
				 bookedAppointments) {

				//If the start and end times match it means the slot is not available
				// and therefore is removed from the available slots list.
				if((slot.getStartTime().equals(bookedAppointment.getStartTime()))
				&& slot.getEndTime().equals(bookedAppointment.getEndTime()))
				{
					availableSlots.remove(slot);
					break;
				}
			}

		}

		return availableSlots;
	}


	/**
	 * Registers a new appointment in the professional's electronic diary.
	 * Checks if it's allowed and if there are now conflicts with the already booked appointments.
	 *
	 * @param appointment the new appointment to register in the professional's electronic diary.
	 */
	public boolean addAppointment(Appointment appointment) {
		if(diary.searchIfTimeAvailable(appointment.getStartTime())) return diary.addAppointment(this,appointment);
		return false;
	}

	/**
	 * Deletes an appointment from the professional's electronic diary
	 *
	 * @param appointmentId The id of the appointment to delete
	 * @return the deleted appointment.
	 */
	public Appointment deleteAppointment(long appointmentId) {
		Appointment deletedAppointment=diary.getAppointment(appointmentId);
		if(deletedAppointment!=null) diary.deleteAppointment(appointmentId);
		return deletedAppointment;
	}

	/**
	 * A new task is added to the personal task list.
	 * @param taskName - name of the task
	 * @param description - description of the task
	 * @param dueBy - date when the task is due by
	 * @return true or false whether the addition was successful
	 */
	public boolean addTask(String taskName, String description, Date dueBy)
	{
		Task toAdd = new Task(taskName, description, dueBy);
		return tasks.addTask(toAdd);
	}

	/**
	 * Task is deleted off the personal task list.
	 * @param taskName task name to delete
	 * @return the deleted task
	 */
	public Task deleteTask(String taskName)
	{
		Task toDelete = null;

		//Finds a task with the same task name in the list
		//Copies the content of the found task onto the
		//toDelete node
		for (Task task : tasks.getTaskList()) {
			if (task.getTaskName().equals(taskName)) {
				toDelete = task;
			}
		}
		if (tasks.deleteTask(toDelete)) return toDelete;
		else return null;
	}

	/**
	 * Sets a new password for the professional.
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

	/**
	 * Getter of the professional's office
	 *
	 * @return the professional's office name/number
	 */
	public String getOffice() {
		return this.office;
	}

	/**
	 * Sets the name/number of the professional's office.
	 *
	 * @param office The name/number to set the professional office to.
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * Getter of the professional's role.
	 *
	 * @return the role of the professional.
	 */
	public String getRole() {
		return this.role;
	}

	/**
	 * Sets the role of the professional.
	 *
	 * @param role The role to set to the professional.
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Getter of the professional's electronic diary.
	 *
	 * @return the professional's electronic diary.
	 */
	public ElectronicDiary getDiary() {
		return this.diary;
	}


}