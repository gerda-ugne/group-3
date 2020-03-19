package hospital.staff;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Professional {

	private static long counter = 0;

	private long id;

	private String firstName;

	private String lastName;

	private String office;

	private String role;

	private ElectronicDiary diary;

	private Map<DayOfWeek, WorkingHours> workingHours;

	/**
	 * Default constructor for the professional classs
	 */
	public Professional() {

		id = counter++;
		firstName = "undefined";
		lastName = "undefined";
		office = "undefined";
		role = "undefined";
		diary = new ElectronicDiary();

	}

	/**
	 * Default constructor for the Professional class
	 * @param firstName - first name of the professional
	 * @param lastName - last  name of the professional
	 * @param role - role that professional takes
	 */
	public Professional(String firstName, String lastName, String role, String office) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.office = office;
		this.role = role;
		this.diary = new ElectronicDiary();
		workingHours = new HashMap<>(7);
		this.id = counter++;
	}

	public long getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Finds available slots for a possible appointment
	 * and returns a List of available appointment dates.
	 *
	 * The returned list only contains Appointments with only
	 * data of start and end time.
	 *
	 * @param from data range to search from
	 * @param to data range to search to
	 */
	public List<Appointment>  searchAvailability(Date from, Date to) {

		// by Miklos

		//Start time is converted into seconds
		long startTime = from.getTime();

		//End time of an appointment is calculated
		long endTime = from.getTime() + Appointment.TREATMENT_DURATION;

		//New list for empty available slots is created
		List<Appointment> availableSlots = new ArrayList<>();

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
		//end by Miklos

		//Clear other data of booked appointments to make the list comparable to available slots
		for (Appointment i: bookedAppointments
			 ) {

			Date start = i.getStartTime();
			Date end = i.getEndTime();

			i = new Appointment(start,end);
		}
		
		//Leaves the available slots to be empty
		try {
			availableSlots.removeAll(bookedAppointments);
			return availableSlots;

		} catch (NullPointerException e) {
			System.out.println("Null element found, searching for availability could not be completed.");
		}


		return null;
	}

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param room
	 * @param treatmentType
	 */
	public Appointment addAppointment(Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Professional.addAppointment

		Appointment tempAppointment = new Appointment(startTime, endTime, room, treatmentType);
		diary.addAppointment(tempAppointment);
		return null;
	}

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param room
	 * @param treatmentType
	 */
	public Appointment editAppointment(Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Professional.editAppointment
		return null;
	}

	/**
	 * 
	 * @param appointmentId
	 */
	public boolean deleteAppointment(long appointmentId) {
		// TODO - implement Professional.deleteAppointment
		return false;
	}

	public String getOffice() {
		return this.office;
	}

	/**
	 * 
	 * @param office
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	public String getRole() {
		return this.role;
	}

	/**
	 * 
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public ElectronicDiary getDiary() {
		return this.diary;
	}

}