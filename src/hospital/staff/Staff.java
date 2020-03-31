package hospital.staff;

import hospital.undo_redo.UndoRedoExecutor;
import hospital.timeLogger.TimeLogger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Staff class contains a HashSet of Professionals.
 * The HashSet implements the Set interface.
 *
 * Members can be added and removed from the staff.
 *
 */
public class Staff implements UndoRedoExecutor {

	/**
	 * The set of professionals the staff is consists of.
	 */
	private Set<Professional> staff;

	/**
	 * Default constructor for Staff class
	 */
	public Staff() {
		staff = new HashSet<>();
	}

	/**
	 * Adds a new member to the staff.
	 *
	 * @param newMember The professional to add as a new member.
	 */
	public void addMember(Professional newMember) {

		staff.add(newMember);

	}

	/**
	 * Removes a professional from the staff
	 * @param member member to remove
	 * @return false/true whether the member was removed
	 */
	public boolean removeMember(Professional member) {

		return staff.remove(member);

	}

	/**
	 * Finds all common available slots in a list of provided professionals.
	 * Time taken to search the diaries is recorded and displayed to the user.
	 *
	 * @param professionals The list of professionals who have to share the new appointment.
	 * @param from The starting time of the interval to search in.
	 * @param to The ending time of the interval to search in.
	 * @return A set of available time-slots as empty appointments, which are free for all of the involved professionals.
	 */
	public List<Appointment> searchAvailability(List<Professional> professionals, Date from, Date to) {

		// TODO move time logging to a different class (and package), e.g. TimeLogger
		//Records current time to calculate time taken to search availability
		long startSearchTime = System.nanoTime();

		//Local variable for holding personal appointments of one professional at a time
		List<List<Appointment>> personalFreeSlots = new ArrayList<>();
		Set <Appointment> allAppointments = new HashSet<Appointment>();

		//Professional availability is retrieved and recorded into a set
		for (Professional professional:
			 professionals) {

			List<Appointment> tempList = professional.searchAvailability(from, to);
			personalFreeSlots.add(tempList);
			allAppointments.addAll(tempList);
		}

		//The intersection of free common slots is calculated

		for(int o=0; o<personalFreeSlots.size(); o++)
		{
			allAppointments.retainAll(personalFreeSlots.get(o));
		}

		//Converts set into a list type object
		List<Appointment> listOfAppointments = new ArrayList<Appointment>(allAppointments);
		//Sorts the list by start date
		listOfAppointments.sort(Comparator.comparing(Appointment::getStartTime));

		TimeLogger log = new TimeLogger(startSearchTime);

		return listOfAppointments;
	}

	/**
	 * List of professionals is filtered by role.
	 *
	 * @param professionals list of professionals to filter
	 * @param role role to filter by
	 * @return list of professionals only with the given role
	 */
	public List<Professional> sortByRole(List<Professional> professionals, String role)
	{
		List<Professional> professionalsOfRole = new ArrayList<Professional>(professionals)
				.stream()
				.filter(professional -> {
					if(professional.getRole().equals(role)) return true;
					else return false;
				})
				.collect(Collectors.toList());

		return professionalsOfRole;

	}

	/**
	 * Books an appointment in one or more electronic diaries of the involved professionals.
	 * It also checks if the given time-slot is free and available for all of the involved professionals.
	 *
	 * @param professionals A list of the ids of the professionals who are involved in the new appointment.
	 * @param startTime The time when the new appointment starts.
	 * @param endTime The time when the new appointment ends.
	 * @param room The name/number of the room where the appointment will take place.
	 * @param treatmentType The type of treatment the new appointment has.
	 * @return The newly created appointment or null if the booking was unsuccessful.
	 */
	public Appointment bookAppointment(List<Long> professionals, Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Staff.bookAppointment
		return null;
	}

	/**
	 * Edit one of the appointment of one of the staff member.
	 * If more than one professionals are involved in the treatment,
	 * it checks if the modifications do not conflict with any of the professionals' electronic diary.
	 *
	 * @param professionalId The ID of the professional who has the appointment.
	 * @param appointmentId The ID of the appointment to modify.
	 * @param professionals A list of the ids of the professionals who are involved in the appointment, including the owner themself.
	 * @param startTime The time when the appointment starts.
	 * @param endTime The time when the appointment ends.
	 * @param room The name/number of the room where the appointment will take place.
	 * @param treatmentType The type of treatment the appointment has.
	 * @return	The modified appointment.
	 * 			It is possible, that the modification was unsuccessful, and the returned appointment is the unmodified one.
	 * 			The return can be null, if the appointment could not have been found.
	 */
	public Appointment editAppointment(long professionalId, long appointmentId, List<Long> professionals, Date startTime, Date endTime, String room, String treatmentType) {
		// TODO - implement Staff.editAppointment
		return null;
	}

	/**
	 * Deletes an appointment from one of professional's electronic diary.
	 * It also deletes it from all the involved professionals' diaries.
	 *
	 * @param professionalId The ID of the professional who has the appointment.
	 * @param appointmentId The ID of the appointment to delete.
	 * @return The deleted appointment or null, if the deletion was unsuccessful.
	 */
	public Appointment deleteAppointment(long professionalId, long appointmentId) {
		// TODO - implement Staff.deleteAppointment
		return null;
	}

	/**
	 * Search for an appointment in one of the professional's electronic diary.
	 *
	 * @param professionId The ID of the professional who has the appointment.
	 * @param appointmentId The ID of the appointment to search for.
	 * @return The found appointment or null if it could not have been found.
	 */
	public Appointment searchAppointment(long professionId, long appointmentId) {
		// TODO - implement Staff.searchAppointment
		return null;
	}
}