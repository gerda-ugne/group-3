package hospital.staff;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents an electronic diary of a professional. Contains appointments.
 */
public class ElectronicDiary implements Serializable {

	/**
	 * The list of appointments the professional has
	 */
	private final List<Appointment> appointments;

	/**
	 * Constructor for the Electronic diary class.
	 * Creates an empty diary with no appointments
	 */
	public ElectronicDiary() {
		appointments = new ArrayList<>();
	}

	/**
	 * Getter of the appointments in the diary
	 *
	 * @return the list of appointments in the diary
	 */
	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	/**
	 * Adds a new appointment to the diary.
	 *
	 * @param newAppointment The appointment to add into the diary
	 * @return true if addition was successful, false if it wasn't
	 */
	public boolean addAppointment(Appointment newAppointment) {
		//gets appointment start time
		LocalDateTime from = newAppointment.getStartTime();

		//checks if the given professional has the free slot needed
		if(searchIfTimeAvailable(from))
		{
			//if he does, adds the appointment to his diary and returns true
			appointments.add(newAppointment);
			return true;
		}
		return false;
	}

	/**
	 * Deletes an appointment from the diary.
	 *
	 * @param appointmentId the ID of the appointment to delete.
	 * @return true if deletion was successful, false if it wasn't
	 */
	public boolean deleteAppointment(long appointmentId) {
		//gets the appointment by ID
		Appointment appointmentToDelete = getAppointment(appointmentId);
		//if appointment isn't null (appointment was found) remove it from diary
		if (appointmentToDelete != null) {
			return appointments.remove(appointmentToDelete);
		}
		return false;
	}

	/**
	 * Return an appointment from the diary.
	 *
	 * @param appointmentId The ID of the appointment to retrieve.
	 * @return the found appointment, or null if no such appointment exists
	 */
	public Appointment getAppointment(long appointmentId) {
		for(Appointment appointment : appointments) {
			if(appointment.getId() == (appointmentId)) {
				return appointment;
			}
		}
		return null;
	}

	/**
	 * Sorts the appointments by date to ease the
	 * search functionality in other methods.
	 * @return sorted appointment list by date
	 */
	public List<Appointment> sortByDate()
	{
		List<Appointment> copy = new ArrayList<>(List.copyOf(appointments));
		Collections.sort(copy);
		return copy;
	}

	/**
	 * Checks whether the specified time is free in the Professional's diary

	 * @param from data range to search from
	 * @return true if time is free
	 */
	public boolean searchIfTimeAvailable(LocalDateTime from) {

		//End time of an appointment is calculated
		LocalDateTime endTime = from.plus(Appointment.TREATMENT_DURATION);

		for(Appointment appointment: appointments)
		{
			//Gets each appointment's start and end times
			LocalDateTime appointmentStartTime = appointment.getStartTime();
			LocalDateTime appointmentEndTime = appointment.getStartTime().plus(Appointment.TREATMENT_DURATION);

			//checks if times overlap
			// End time is in the existing appointment
			if(!endTime.isBefore(appointmentStartTime) && !endTime.isAfter(appointmentEndTime)) return false;
			// From time is in the existing appointment
			else if (!from.isBefore(appointmentStartTime) && !from.isAfter(appointmentEndTime)) return false;
			// New appointment is longer than the existing but still overlaps
			else if(!from.isBefore(appointmentStartTime) && !endTime.isAfter(appointmentEndTime)) return false;
		}

		return true;
	}
}