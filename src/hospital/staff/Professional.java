package hospital.staff;

import java.time.DayOfWeek;
import java.util.*;

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
	 * The schedule when the professional is working in a week.
	 */
	private Map<DayOfWeek, WorkingHours> workingHours;

	/**
	 * Default constructor for the Professional class
	 *
	 * @param firstName The first name of the professional
	 * @param lastName The last  name of the professional
	 * @param role The role the professional takes
	 */
	public Professional(String firstName, String lastName, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.office = "<undefined>";
		this.role = role;
		this.diary = new ElectronicDiary();
		workingHours = new HashMap<>(7);
		this.id = counter++;
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
	 * Returns a set of empty, not yet saved appointments when the professional is available during the given period.
	 *
	 * @param from The time when the searched period starts.
	 * @param to The time when the searched period ends.
	 * @param duration The duration of the appointment to book later.
	 */
	public Set<Appointment> searchAvailability(Date from, Date to, int duration) {
		// TODO - implement Professional.searchAvailability
		return null;
	}

	/**
	 * Adds a new appointment to the professional's electronic diary.
	 * Checks if it's allowed and if there are now conflicts with the already booked appointments.
	 *
	 * @param startTime The time when the new appointment starts.
	 * @param endTime The time when the new appointment ends.
	 * @param room The name/number of the room where the appointment takes place in.
	 * @param treatmentType The type of the treatment
	 */
	public Appointment addAppointment(Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Professional.addAppointment
		Appointment tempAppointment = new Appointment(startTime, endTime, room, treatmentType);
		diary.addAppointment(tempAppointment);
		return null;
	}

	/**
	 * Modifies an appointment in the professional's electronic diary.
	 * Checks if it's allowed and if there are now conflicts with the already booked appointments.
	 *
	 * @param id The id of the appointment to modify.
	 * @param startTime The time when the new appointment starts.
	 * @param endTime The time when the new appointment ends.
	 * @param room The name/number of the room where the appointment takes place in.
	 * @param treatmentType The type of the treatment
	 */
	public Appointment editAppointment(long id, Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Professional.editAppointment
		return null;
	}

	/**
	 * Deletes an appointment from the professional's electronic diary
	 *
	 * @param appointmentId The id of the appointment to delete
	 */
	public boolean deleteAppointment(long appointmentId) {
		// TODO - implement Professional.deleteAppointment
		return false;
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