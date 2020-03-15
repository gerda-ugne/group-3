package hospital.staff;

import java.util.ArrayList;
import java.util.List;

public class ElectronicDiary {

	private List<Appointment> appointments;

	public ElectronicDiary() {
		appointments = new ArrayList<>();
	}

	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	/**
	 * 
	 * @param newAppointment
	 */
	public boolean addAppointment(Appointment newAppointment) {
		// TODO - implement ElectronicDiary.addAppointment
		return false;
	}

	/**
	 * 
	 * @param appointmentId
	 */
	public boolean deleteAppointment(long appointmentId) {
		// TODO - implement ElectronicDiary.deleteAppointment
		return false;
	}

	/**
	 * 
	 * @param appointmentId
	 */
	public Appointment getAppointment(long appointmentId) {
		// TODO - implement ElectronicDiary.getAppointment
		return null;
	}

}