package hospital;

import hospital.staff.DeleteAppointmentAction;
import hospital.undo_redo.Action;
import hospital.staff.Appointment;
import hospital.staff.Professional;
import hospital.staff.Staff;
import hospital.undo_redo.UndoRedoHandler;

import java.util.*;

/**
 * Represents the menu in the Hospital Booking System.
 */
public class Menu {

	/**
	 * The collection of employees in the hospital (doctors, nurses, etc.)
	 */
	private Staff staff;

	/**
	 * The staff member who currently using the system. Null if nobody is logged in.
	 */
	private Professional activeUser;

	private UndoRedoHandler undoRedoHandler;

	public Menu() {
		// TODO restore from save
		staff = new Staff();

		activeUser = null;
		undoRedoHandler = new UndoRedoHandler();
	}

	public static void main(String[] args) {

	}

	private void showMenu() {
		// TODO - implement Menu.showMenu

	}

	private void displayDiary() {
		// TODO - implement Menu.displayDiary
		
	}

	private void searchAppointment() {
		// TODO - implement Menu.searchAppointment
		
	}

	private void addAppointment() {
		List<Long> professionals = new ArrayList<>();
		Date startTime = new Date();
		Date endTime = new Date();
		String room = "";
		String treatmentType = "";
		// TODO get input from user
		Appointment newAppointment = staff.bookAppointment(professionals, startTime, endTime, room, treatmentType);
		if (newAppointment != null) {
			try {
				undoRedoHandler.addAction(new Action(
						"Add appointment",
						staff,
						staff.getClass().getMethod("deleteAppointment", long.class, long.class),
						new Object[]{activeUser.getId(), newAppointment.getId()},
						staff.getClass().getMethod("bookAppointment", List.class, Date.class, Date.class, String.class, String.class),
						new Object[]{professionals, startTime, endTime, room, treatmentType}
				));
			} catch (NoSuchMethodException e) {
				// TODO handle exception
				e.printStackTrace();
			}
		}
	}

	private void editAppointment() {
		long appointmentId = 0;
		List<Long> professionals = new ArrayList<>();
		Date startTime = new Date();
		Date endTime = new Date();
		String room = "";
		String treatmentType = "";
		// TODO get input from user
		Appointment oldAppointment = staff.searchAppointment(activeUser.getId(), appointmentId);
		Appointment modifiedAppointment = staff.editAppointment(activeUser.getId(), appointmentId, professionals, startTime, endTime, room, treatmentType);
		if (!modifiedAppointment.equals(oldAppointment)) {
			try {
				undoRedoHandler.addAction(new Action(
						"Edit appointment",
						staff,
						staff.getClass().getMethod("editAppointment", long.class, long.class),
						new Object[]{activeUser.getId(), appointmentId},
						staff.getClass().getMethod("editAppointment", long.class, long.class),
						new Object[] {}
				));
			} catch (NoSuchMethodException e) {
				// TODO handle exception
				e.printStackTrace();
			}
		}
	}

	private void deleteAppointment() {
		long appointmentId = 0;
		// TODO - implement Menu.deleteAppointment
		Appointment deletedAppointment = staff.deleteAppointment(activeUser.getId(), appointmentId);
		if (deletedAppointment != null) {
			try {
				undoRedoHandler.addAction(new DeleteAppointmentAction(
						"Delete appointment",
						staff,
						new Object[]{deletedAppointment.getProfessionals(), deletedAppointment.getStartTime(), deletedAppointment.getEndTime(), deletedAppointment.getRoom(), deletedAppointment.getTreatmentType()},
						new Object[] {activeUser.getId(), null}
				));
			} catch (NoSuchMethodException e) {
				// TODO handle exception
				e.printStackTrace();
			}
		}
	}

	public void logRunningTime() {
		// TODO - implement Menu.logRunningTime
		
	}

	private void backupDiary() {
		// TODO - implement Menu.backupDiary
		
	}

	private void restoreDiary() {
		// TODO - implement Menu.restoreDiary
		
	}

	/**
	 * 
	 * @param newProfessionalId
	 */
	private void changeUser(long newProfessionalId) {
		// TODO - implement Menu.changeUser
		
	}
}