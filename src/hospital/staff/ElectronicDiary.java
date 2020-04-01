package hospital.staff;

import java.util.*;

/**
 * Represents an electronic diary of a professional. Contains appointments.
 */
public class ElectronicDiary {

	/**
	 * The list of appointments the professional has
	 */
	private List<Appointment> appointments;

	/**
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
	 */
	public boolean addAppointment(Professional professional, Appointment newAppointment) {
		//gets appointment start time
		Date from = newAppointment.getStartTime();

		//checks if the given professional has the free slot needed
		if(searchIfTimeAvailable(from))
		{
			//if he does, adds the appointment to his diary and returns true
			professional.addAppointment(newAppointment);
			return true;
		}
		return false;
	}

	/**
	 * Deletes an appointment from the diary.
	 *
	 * @param appointmentId the ID of the appointment to delete.
	 */
	public boolean deleteAppointment(long appointmentId) {
		Appointment appointmentToDelete = getAppointment(appointmentId);
		if (appointmentToDelete != null) {
			return appointments.remove(appointmentToDelete);
		}
		return false;
	}

	/**
	 * Return an appointment from the diary.
	 *
	 * @param appointmentId The ID of the appointment to retrieve.
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
	public boolean searchIfTimeAvailable(Date from) {

		//Start time is converted into seconds
		long startTime = from.getTime();

		//End time of an appointment is calculated
		long endTime = from.getTime() + Appointment.TREATMENT_DURATION;

		List<Appointment> appointments = getAppointments();
		for(Appointment appointment: appointments)
		{
			//Gets each appointment's start and end times
			long appointmentStartTime = appointment.getStartTime().getTime();
			long appointmentEndTime = appointment.getStartTime().getTime()+Appointment.TREATMENT_DURATION;

			//checks if times overlap
			if((endTime<appointmentEndTime)&&(endTime>appointmentStartTime)) return false;

			else if ((startTime>appointmentStartTime)&&(startTime<appointmentEndTime)) return false;

			else if((startTime<appointmentStartTime)&&(endTime>appointmentEndTime)) return false;
		}

		return true;
	}
}