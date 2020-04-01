package hospital.staff;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a professional in the hospital staff.
 */
public class Professional {

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

	public Professional() {
		this("<undefined>", "<undefined>", "<undefined>", "<undefined>");
	}

	public Professional(String firstName, String lastName, String role) {
		this(firstName, lastName, role, "<undefined>");
	}

	public Professional(String firstName, String lastName, String role, String office) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.office = office;
		this.diary = new ElectronicDiary();
		this.tasks = new TaskList();
		this.workingHours = new HashMap<>(7);
		this.id = counter++;
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
		// Filters by from/to dates
		Predicate<Appointment> checkTimeRange = appointment -> {
			Date start = appointment.getStartTime();
			Date end = appointment.getEndTime();
			if (start.compareTo(from) <= 0 && start.compareTo(to) >= 0 &&
					end.compareTo(from) <= 0 && end.compareTo(to) >= 0
			) return true;
			else return false;
		};

		// Filters appointments by working hours of the professional
		Predicate <Appointment> checkWorkingHourRange = appointment -> {

			Date start = appointment.getStartTime();
			Date end = appointment.getEndTime();

			// Gets days of week for start/end dates
			// The values are of 0-6 for each week day
			int startDay =  start.getDay();
			int endDay = end.getDay();

			Map<DayOfWeek, WorkingHours> workingHoursMap = workingHours;

			//Iterates through the map
			for (Map.Entry<DayOfWeek, WorkingHours> entry : workingHoursMap.entrySet()) {

				//If weeks of day match of the appointment and the working hours set
				if(entry.getKey().equals(startDay) && entry.getKey().equals(endDay))
				{
					//Checks if appointment is within the working hour range
					if ((start.compareTo(entry.getValue().getStartHour())) >= 0 &&
							(start.compareTo(entry.getValue().getEndHour()) <= 0) &&
							(end.compareTo(entry.getValue().getStartHour()) >= 0) &&
							(end.compareTo(entry.getValue().getEndHour()) <= 0))
					return true;
				}
			}

			return false;

		};

		//Appointments are filtered to be in the provided time range
		List<Appointment> bookedAppointments = diary.sortByDate()
				.stream()
				.filter(checkTimeRange)
				.filter(checkWorkingHourRange)
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
		// TODO check for conflicts. Here or in the diary?
		return diary.addAppointment(appointment);
	}

	/**
	 * Deletes an appointment from the professional's electronic diary
	 *
	 * @param appointmentId The id of the appointment to delete
	 * @return the deleted appointment.
	 */
	public Appointment deleteAppointment(long appointmentId) {
		// TODO - implement Professional.deleteAppointment
		return null;
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