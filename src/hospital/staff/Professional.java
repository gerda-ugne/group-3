package hospital.staff;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

public class Professional {

	private long id;

	private String firstName;

	private String lastName;

	private String office;

	private String role;

	private ElectronicDiary diary;

	private List<WorkingHours> workingHours;

	private List<WorkingHours> holidays;

	public long getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
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
	 * 
	 * @param from
	 * @param to
	 * @param duration
	 */
	public Set<Appointment> searchAvailability(long from, long to, int duration) {
		// TODO - implement Professional.searchAvailability
		return null;
	}

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param room
	 * @param treatmentType
	 */
	public Appointment addAppointment(long startTime, long endTime, String room, String treatmentType) {
		// TODO - implement Professional.addAppointment
		return null;
	}

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param room
	 * @param treatmentType
	 */
	public Appointment editAppointment(long startTime, long endTime, String room, String treatmentType) {
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