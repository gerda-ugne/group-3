package hospital.staff;

public class Staff {

	private Set<Professional> staff;

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
		
	}

	/**
	 * 
	 * @param professionalId
	 * @param appointmentId
	 */
	public Set<Appointment> editAppointment(long professionalId, long appointmentId) {
		// TODO - implement Staff.editAppointment
		
	}

	/**
	 * 
	 * @param professionalId
	 * @param appointmentId
	 */
	public boolean deleteAppointment(long professionalId, long appointmentId) {
		// TODO - implement Staff.deleteAppointment
		
	}

	public void undo() {
		// TODO - implement Staff.undo
		
	}

	public void redo() {
		// TODO - implement Staff.redo
		
	}

}