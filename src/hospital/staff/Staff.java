package hospital.staff;

import hospital.undo_redo.UndoRedoExecutor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the staff of a hospital.
 * Handles any changes made to it as well as managing all the professional's personal administrations.
 */
public class Staff implements UndoRedoExecutor {

	/**
	 * The set of professionals the staff is consists of.
	 */
	private Set<Professional> staff;

	/**
	 * Default constructor of the class.
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
		// TODO - implement Staff.addMember
		
	}

	/**
	 * Removes a professional from the staff.
	 *
	 * @param member The professional to remove.
	 */
	public void removeMember(Professional member) {
		// TODO - implement Staff.removeMember
		
	}

	/**
	 * Searches for free and available appointment slots for ALL the given professionals in the given interval.
	 *
	 * @param professionals The list of professionals who have to share the new appointment.
	 * @param from The starting time of the interval to search in.
	 * @param to The ending time of the interval to search in.
	 * @param duration The duration of the planned appointment in minutes.
	 * @return A set of available time-slots as empty appointments, which are free for all of the involved professionals.
	 */
	public Set<Appointment> searchAvailability(List<Long> professionals, Date from, Date to, int duration) {
		// TODO - implement Staff.searchAvailability
		return null;
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