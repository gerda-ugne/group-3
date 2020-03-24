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
	 * TODO
	 * @param newAppointment
	 */
	public boolean addAppointment(Appointment newAppointment) {
		// TODO check for conflicts
		appointments.add(newAppointment);
		return false;
	}

	/**
	 * TODO
	 * @param appointmentId
	 */
	public boolean deleteAppointment(long appointmentId) {
		Appointment appointmentToDelete = getAppointment(appointmentId);
		if (appointmentToDelete != null) {
			return appointments.remove(appointmentToDelete);
		}
		return false;
	}

	/**
	 * TODO
	 * @param appointmentId
	 */
	public Appointment getAppointment(long appointmentId) {
		for(Appointment appointment : appointments) {
			if(appointment.getId() == (appointmentId)) {
				return appointment;
			}
		}
		return null;
	}

}