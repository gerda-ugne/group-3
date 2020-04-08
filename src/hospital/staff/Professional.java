package hospital.staff;

import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a professional in the hospital staff.
 */
public class Professional extends User {


	/**
	 * 	The personal electronic diary of the professional with booked appointments.
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
	 * Constructor with no parameters for the Professional class
	 */
	public Professional() {
		super("<undefined>", "<undefined>", null, "<undefined>");
	}

	/**
	 * Constructor for the Professional class
	 * @param firstName name of the professional
	 * @param lastName last name of the professional
	 * @param role role of the professional
	 */
	public Professional(String firstName, String lastName, Role role) {
		super(firstName, lastName, role);
	}

	/**
	 * Constructor for the Professional class
	 * @param firstName name of the professional
	 * @param lastName last name of the professional
	 * @param role role of the professional
	 * @param office office of the professional
	 */
	public Professional(String firstName, String lastName, Role role, String office) {
		super(firstName, lastName, role, office);
		this.diary = new ElectronicDiary();
		this.tasks = new TaskList();
		this.workingHours = new HashMap<>(7);
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

		Set<Appointment> slotsToRemove = new HashSet<>();
		//Nested for loop: each slot is compared to the existing appointments.
		for (Appointment slot:
			 availableSlots) {

			for (Appointment bookedAppointment:
				 bookedAppointments) {

				//If the start and end times match it means the slot is not available
				// and therefore is removed from the available slots list.
				if(slot.getStartTime().isEqual(bookedAppointment.getStartTime())
				&& slot.getEndTime().isEqual(bookedAppointment.getEndTime()))
				{
					slotsToRemove.add(slot);
				}
			}

		}
		availableSlots.removeAll(slotsToRemove);

		return availableSlots;
	}

	/**
	 * Registers a new appointment in the professional's electronic diary.
	 * Checks if it's allowed and if there are now conflicts with the already booked appointments.
	 *
	 * @param appointment the new appointment to register in the professional's electronic diary.
	 * @return true if the appointment addition was successful, false if it wasn't
	 */
	public boolean addAppointment(Appointment appointment) {
		if(diary.searchIfTimeAvailable(appointment.getStartTime())) {
			return diary.addAppointment(appointment);
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
	 * Getter of the professional's electronic diary.
	 * @return the professional's electronic diary.
	 */
	public ElectronicDiary getDiary() {
		return this.diary;
	}

	/**
	 * Getter of an appointment from the professional's electronic diary.
	 * @param appointmentId appointment to be found's ID
	 * @return the needed appointment
	 */
	public Appointment getAppointment(long appointmentId) {
		return diary.getAppointment(appointmentId);
	}

}