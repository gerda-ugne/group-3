package hospital.staff;

import java.util.ArrayList;
import java.util.List;

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
	public boolean addAppointment(Appointment newAppointment) {
		// TODO - implement ElectronicDiary.addAppointment
		return false;
	}

	/**
	 * Deletes an appointment from the diary.
	 *
	 * @param appointmentId the ID of the appointment to delete.
	 */
	public boolean deleteAppointment(long appointmentId) {
		// TODO - implement ElectronicDiary.deleteAppointment
		return false;
	}

	/**
	 * Return an appointment from the diary.
	 *
	 * @param appointmentId The ID of the appointment to retrieve.
	 */
	public Appointment getAppointment(long appointmentId) {
		// TODO - implement ElectronicDiary.getAppointment
		return null;
	}

}