package hospital.staff;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Staff {

	private Set<Professional> staff;

	public Staff() {

	}

	/**
	 * 
	 * @param newMember
	 */
	public void addMember(Professional newMember) {
		// TODO - implement Staff.addMember
		
	}

	/**
	 * 
	 * @param member
	 */
	public void removeMember(Professional member) {
		// TODO - implement Staff.removeMember
		
	}

	/**
	 * 
	 * @param professionals
	 * @param from
	 * @param to
	 * @param duration
	 */
	public Set<Appointment> searchAvailability(List<Long> professionals, Date from, Date to, int duration) {
		// TODO - implement Staff.searchAvailability
		return null;
	}

	/**
	 * 
	 * @param professionals
	 * @param startTime
	 * @param endTime
	 * @param room
	 * @param treatmentType
	 */
	public Appointment bookAppointment(List<Long> professionals, Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Staff.bookAppointment
		return null;
	}

	/**
	 * 
	 * @param professionalId
	 * @param appointmentId
	 */
	public Appointment editAppointment(long professionalId, long appointmentId, List<Long> professionals, Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Staff.editAppointment
		return null;
	}

	/**
	 * 
	 * @param professionalId
	 * @param appointmentId
	 */
	public Appointment deleteAppointment(long professionalId, long appointmentId) {
		// TODO - implement Staff.deleteAppointment
		return null;
	}

	/**
	 *
	 * @param appointment
	 */
	public Appointment deleteAppointment(Appointment appointment) {
		// TODO - implement Staff.deleteAppointment
		return null;
	}

	public Appointment searchAppointment(long id, long appointmentId) {
		// TODO - implement Staff.searchAppointment
		return null;
	}
}