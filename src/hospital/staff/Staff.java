package hospital.staff;

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
	public Set<Appointment> searchAvailability(List<Long> professionals, long from, long to, int duration) {
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
	public Appointment bookAppointment(List<Long> professionals, long startTime, long endTime, String room, String treatmentType) {
		// TODO - implement Staff.bookAppointment
		return null;
	}

	/**
	 * 
	 * @param professionalId
	 * @param appointmentId
	 */
	public Set<Appointment> editAppointment(long professionalId, long appointmentId) {
		// TODO - implement Staff.editAppointment
		return null;
	}

	/**
	 * 
	 * @param professionalId
	 * @param appointmentId
	 */
	public boolean deleteAppointment(long professionalId, long appointmentId) {
		// TODO - implement Staff.deleteAppointment
		return false;
	}

}