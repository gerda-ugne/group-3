package hospital.staff;

import java.time.DayOfWeek;
import java.util.*;

public class Professional {

	private static long counter = 0;

	private long id;

	private String firstName;

	private String lastName;

	private String office;

	private String role;

	private ElectronicDiary diary;

	private Map<DayOfWeek, WorkingHours> workingHours;

	public Professional(String firstName, String lastName, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.office = "<undefined>";
		this.role = role;
		this.diary = null;
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
	 * TODO
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	/**
	 * TODO
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * TODO
	 * @param from
	 * @param to
	 * @param duration
	 */
	public Set<Appointment> searchAvailability(Date from, Date to, int duration) {
		// TODO - implement Professional.searchAvailability
		return null;
	}

	/**
	 * TODO
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
	 * TODO
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
	 * TODO
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