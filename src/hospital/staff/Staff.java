package hospital.staff;

import java.util.*;

/**
 * Staff class contains a HashSet of Professionals.
 * The HashSet implements the Set interface.
 *
 * Members can be added and removed from the staff.
 *
 */
public class Staff {

	private Set<Professional> staff;

	/**
	 * Default constructor for Staff class
	 */
	public Staff() {

		staff = new HashSet<Professional>();
	}

	/**
	 * Adds a new professional to the staff
	 * @param newMember new member to add
	 */
	public void addMember(Professional newMember) {

		staff.add(newMember);

	}

	/**
	 * Removes a professional from the staff
	 * @param member member to remove
	 */
	public void removeMember(Professional member) {

		try {
			staff.remove(member);
		} catch (NullPointerException e) {
			System.out.println("This professional does not exist.");
		}

	}

	/**
	 * Finds all common available slots in a list of provided professionals.
	 *
	 * @param professionals list of professionals needed for the appointment
	 * @param from searching data range from
	 * @param to searching data range to
	 */
	public Set<Appointment> searchAvailability(List<Professional> professionals, Date from, Date to) {

		//Local variable for holding personal appointments of one professional at a time
		List <Appointment> personalFreeSlots = new ArrayList<Appointment>();
		Set <Appointment> allAppointments = new HashSet<Appointment>();

		//Professional availability is retrieved and recorded into a set
		for (Professional professional:
			 professionals) {

			personalFreeSlots = professional.searchAvailability(from, to);
			allAppointments.addAll(personalFreeSlots);
		}

		//The intersection of free common slots is calculated
		for (Professional professional:
			 professionals) {

			allAppointments.retainAll(professional.searchAvailability(from,to));

		}

		return allAppointments;
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