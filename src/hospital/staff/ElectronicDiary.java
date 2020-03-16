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
		appointments.add(newAppointment);
		return false;
	}

	/**
	 * 
	 * @param appointmentId
	 */
	public boolean deleteAppointment(long appointmentId) {
		Appointment appointmentToDelete=getAppointment(appointmentId);
		if(appointmentToDelete!=null) appointments.remove(appointmentToDelete);
		return false;
	}

	/**
	 * 
	 * @param appointmentId
	 */
	public Appointment getAppointment(long appointmentId) {
		for(Appointment appointment : appointments) {
			if(appointment.getId().equals(appointmentId)) {
				return appointment;
			}
		}
		return null;
	}

}