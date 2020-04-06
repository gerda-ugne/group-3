package hospital.staff;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import hospital.undo_redo.UndoRedoExecutor;

import hospital.undo_redo.UndoRedoExecutor;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Represents a professional in the hospital staff.
 */
public class Professional implements Serializable, UndoRedoExecutor {

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

	/**
	 * The personal task list for the professional
	 */
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
		username = (firstName + lastName).toLowerCase();
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
	 * Setter method for Electronic diary
	 * @param diary diary to be set
	 */
	public void setDiary(ElectronicDiary diary) {
		this.diary = diary;
	}

	/**
	 * Getter method for the TaskList
	 * @return the task list
	 */
	public TaskList getTasks() {
		return tasks;
	}

	/**
	 * Setter method for the TaskList
	 * @param tasks task list to be set
	 */
	public void setTasks(TaskList tasks) {
		this.tasks = tasks;
	}

	/**
	 * Getter method for the Working Hours
	 * @return working hours
	 */
	public Map<DayOfWeek, WorkingHours> getWorkingHours() {
		return workingHours;
	}

	/**
	 * Setter method for the working hours
	 * @param workingHours to be set
	 */
	public void setWorkingHours(Map<DayOfWeek, WorkingHours> workingHours) {
		this.workingHours = workingHours;
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
	public List<Appointment> searchAvailability(LocalDateTime from, LocalDateTime to) {

		//Start time is converted into seconds
		LocalDateTime startTime = from;

		//End time of an appointment is calculated
		LocalDateTime endTime = from.plus(Appointment.TREATMENT_DURATION);

		//New list for empty available slots is created
		List<Appointment> availableSlots = new ArrayList<>();

		//While there is more time to add empty appointments
		while (!endTime.isAfter(to)) {

			//Add an empty appointment with defined start and end time
			availableSlots.add(new Appointment(startTime, endTime));

			//Adjust the time for the next instance
			startTime = endTime;
			endTime = startTime.plus(Appointment.TREATMENT_DURATION);
		}

		// Check if an appointment is in the given time-range
		// Filters by from/to dates
		Predicate<Appointment> checkTimeRange = appointment -> {
			LocalDateTime start = appointment.getStartTime();
			LocalDateTime end = appointment.getEndTime();

			if (!start.isBefore(from) && !start.isAfter(to) &&
					!end.isBefore(from) && !end.isAfter(to)
			) return true;
			else return false;
		};

		// Filters appointments by working hours of the professional
		Predicate <Appointment> checkWorkingHourRange = appointment -> {

			//Get the hours of the day when the appointment starts and ends
			LocalTime start = appointment.getStartTime().toLocalTime();
			LocalTime end = appointment.getEndTime().toLocalTime();

			// Gets days of week for start/end dates
			// The values are of 1-7 for each week day
			DayOfWeek startDay =  appointment.getStartTime().getDayOfWeek();
			DayOfWeek endDay = appointment.getEndTime().getDayOfWeek();

			//Iterates through the map
			for (Map.Entry<DayOfWeek, WorkingHours> entry : workingHours.entrySet()) {

				//If the day of the week match of the appointment and the working hours set,
				// i.e the appointment is happening on Monday and the iterator reaches
				// Monday working hour definition
				if(entry.getKey().equals(startDay) && entry.getKey().equals(endDay))
				{
					//Checks if appointment is within the working hour range
					//Starting and ending hours have to be:
					//equal or after the working shift start hour AND
					//before or equal to the working shit start hour
					if (!start.isBefore(entry.getValue().getStartHour()) &&
							!start.isAfter(entry.getValue().getEndHour()) &&
							!end.isBefore(entry.getValue().getStartHour()) &&
							!end.isAfter(entry.getValue().getEndHour()))
					return true;
				}
			}

			return false;

		};

		//Appointments are filtered to be in the provided time range
		List<Appointment> bookedAppointments = diary.sortByDate()
				.stream()
				.filter(checkTimeRange.and(checkWorkingHourRange))
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
		if(diary.searchIfTimeAvailable(appointment.getStartTime())) {
			return diary.addAppointment(this, appointment);
		}
		return false;
	}

	/**
	 * Deletes an appointment from the professional's electronic diary
	 *
	 * @param appointmentId The id of the appointment to delete
	 * @return the deleted appointment.
	 */
	public Appointment deleteAppointment(long appointmentId) {
		Appointment deletedAppointment = diary.getAppointment(appointmentId);
		if(deletedAppointment != null) diary.deleteAppointment(appointmentId);
		return deletedAppointment;
	}

	/**
	 * A new task is added to the personal task list.
	 * @param taskName - name of the task
	 * @param description - description of the task
	 * @param dueBy - date when the task is due by
	 * @return true or false whether the addition was successful
	 */
	public boolean addTask(String taskName, String description, LocalDate dueBy)
	{
		Task toAdd = new Task(taskName, description, dueBy);
		return tasks.addTask(toAdd);
	}

	/**
	 * Task is deleted off the personal task list.
	 * @param taskName task name to delete
	 * @return true/false whether the task was deleted
	 */
	public boolean deleteTask(String taskName)
	{
		Task toDelete = tasks.findTask(taskName);
		if(toDelete == null) return false;
		else
		{
			tasks.deleteTask(toDelete);
			return true;
		}


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

	public Appointment getAppointment(long appointmentId) {
		return null;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + " (" + id + ")";
	}

	/**
	 * Updates the username of the professional.
	 * Method is used in case of first or last name change
	 * when updating the details.
	 *
	 */
	public void updateUsername()
	{
		username = (firstName + lastName).toLowerCase();
	}
}