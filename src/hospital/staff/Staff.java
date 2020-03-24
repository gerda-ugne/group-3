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
	 * Time taken to search the diaries is recorded and displayed to the user.
	 *
	 * @param professionals list of professionals needed for the appointment
	 * @param from searching data range from
	 * @param to searching data range to
	 */
	public List<Appointment> searchAvailability(List<Professional> professionals, Date from, Date to) {

		//Records current time to calculate time taken to search availability
		Date startSearchTime = new Date();

		//Local variable for holding personal appointments of one professional at a time
		List <Appointment> personalFreeSlots;
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

		//Converts set into a list type object
		List<Appointment> listOfAppointments = new ArrayList<Appointment>();
		listOfAppointments.addAll(allAppointments);

		Date endSearchTime = new Date();

		//Calculates the total time taken to search the free appointment slots
		long totalTimeTaken = endSearchTime.getTime() - startSearchTime.getTime();
		System.out.println("Search took " + totalTimeTaken/1000 + "seconds.");

		return listOfAppointments;
	}

	/**
	 * List of professionals is filtered by role
	 *
	 * @param professionals list of professionals to filter
	 * @param role role to filter by
	 * @return list of professionals only with the given role
	 */
	public List<Professional> sortByRole(List<Professional> professionals, String role)
	{
		List<Professional> professionalsOfRole = new ArrayList<Professional>();
		for (Professional professional:
			 professionals) {

			if(professional.getRole().equals(role)) professionalsOfRole.add(professional);
		}

		return professionalsOfRole;

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