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
		lastName = "unfdefined";
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
	 * wip
	 * @param from
	 * @param to
	 */
	public List<Appointment> searchAvailability(Date from, Date to) {
		// TODO - implement Professional.searchAvailability

		// by Miklos
		long startTime = from.getTime();
		long endTime = from.getTime() + Appointment.TREATMENT_DURATION;
		List<Appointment> availableSlots = new ArrayList<>();
		while (endTime < to.getTime()) {
			availableSlots.add(new Appointment(new Date(startTime), new Date(endTime)));
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

		List<Appointment> bookedAppointments = diary.sortByDate()
				.stream()
				.filter(checkTimeRange)
				.sorted()
				.collect(Collectors.toList());
		//end by Miklos

		//Get all the currently booked appointments sorted by date
		List<Appointment> bookedAppointments = diary.sortByDate();

		//List to store available appointment times
		Set<Appointment> availableTimes = new Set<Appointment>();
		//List of empty slots to add when found
		Set<Appointment> toAdd = new Set<Appointment>();
		
		//Set a pointer to search from the "FROM" date
		Date currentDay = from;

		Iterator iter = bookedAppointments.iterator();
		Appointment marker = new Appointment();

		//For each new appointment from the booked appointment list
		//Until start is found
		while (iter.hasNext()) {

			if(bookedAppointments[iter].getStartTime =< from){

				marker = bookedAppointments[iter];
				break;
			}
		}

		iter = bookedAppointments.iterator();
		Appointment previous = null;
		boolean found = false;

		while (iter.hasNext()) {

			if(bookedAppointments[iter].getStartTime =< from)
			{
				found = true;
				break;
			}

			previous = bookedAppointments[iter];
		}

		if(!found) marker = previous;
		//TO-DO: shorten the list to cut off the irrelevant appointments
		// and compare appointmets by DAY in the loops

		if()
		{
			availableTimes.addAll(toAdd);
		}


		//calendar.add(calendar.DATE, 1);
		LocalDateTime.from(currentDay.toInstant()).plusDays(1);



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