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

	/**
	 * The handler which responsible for the undo/redo function of the system.
	 * Every action which needs to be undoable have to be registered by this handler.
	 */
	private UndoRedoHandler undoRedoHandler;

	/**
	 * TODO
	 */
	public Menu() {
		// TODO restore from save
		staff = new Staff();

		activeUser = null;
		undoRedoHandler = new UndoRedoHandler();
	}

	/**
	 * TODO
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * TODO
	 */
	private void showMenu() {
		// TODO - implement Menu.showMenu

	}

	/**
	 * TODO
	 */
	private void displayDiary() {
		// TODO - implement Menu.displayDiary
		
	}

	/**
	 * TODO
	 */
	private void searchAppointment() {
		// TODO - implement Menu.searchAppointment
		
	}

	/**
	 * Get inputs from the user for a new appointment, verify it and adds it to the database.
	 */
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

	/**
	 * Get inputs from a user for editing an existing appointment and modify it after verification.
	 */
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

	/**
	 * Gets and ID from the user to delete one of their appointment.
	 * After verification, deletes it.
	 */
	private void deleteAppointment() {
		long appointmentId = 0;
		// TODO get input from user
		Appointment deletedAppointment = staff.deleteAppointment(activeUser.getId(), appointmentId);
		if (deletedAppointment != null) {
			try {
				undoRedoHandler.addAction(new DeleteAppointmentAction(
						"Delete appointment",
						staff,
						deletedAppointment
				));
			} catch (NoSuchMethodException e) {
				// TODO handle exception
				e.printStackTrace();
			}
		}
	}


	/**
	 * TODO
	 */
	private void backupDiary() {
		// TODO - implement Menu.backupDiary
		
	}

	/**
	 * TODO
	 */
	private void restoreDiary() {
		// TODO - implement Menu.restoreDiary
		
	}

	/**
	 * Gets the professional ID of the user who would like to use the system.
	 * After verification the professional will be able to make changes to their electronic diary.
	 */
	private void changeUser() {
		// TODO - implement Menu.changeUser
		
	}
}