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
	public Set<Appointment> searchAvailability(List<Professional> professionals, Date from, Date to) {
		// TODO - implement Staff.searchAvailability

		//Amount of professionals needed for appointment
		int amountOfProfessionals = professionals.length();

		//The set of appointments that are available for all professionals
		Set <Appointment> finalAppointments = new Set<Appointment>();

		//Local variable for holding personal appointents of one professional at a time
		Set<Appointment> personalAppointments = new Set<Appointment>();

		for(int i=0, i<amountOfProfessionals, i++)
		{

		}


		personalAppointments = professionals[i].searchAvailability(from,to,duration);
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