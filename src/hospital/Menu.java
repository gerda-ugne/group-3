package hospital;

import hospital.staff.*;
import hospital.undo_redo.Action;
import hospital.undo_redo.UndoRedoHandler;

import java.io.*;
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
	 * Displays the menu options
	 */
	private void showMenu() {
		// TODO - implement Menu.showMenu
		System.out.println("\nAppointment management:\n");
		System.out.println("1. Add a new appointment");
		System.out.println("2. Edit an existing appointment");
		System.out.println("3. Remove an appointment");
		System.out.println("4. Undo last action");
		System.out.println("5. Redo last action");
		System.out.println("6. Display the electronic diary");
		System.out.println("7. Backup the electronic diary");
		System.out.println("8. Restore the latest backup of the electronic diary");


		System.out.println("\nTask management:\n");
		System.out.println("9. Add a new task");
		System.out.println("10. Remove a task");
		System.out.println("11. Display the task list");

		System.out.println("\nOther:\n");
		System.out.println("12. Change your password");
		System.out.println("13. Change your personal details");

	}

	/**
	 * Method to process users choice from the menu and activate corresponding function.
	 * Please note that it uses a Genio class written by UoD to handle data input.
	 * @return int userChoice
	 * @throws Exception is a standard switch case Exception to handle input errors.
	 */

	public int processUserChoice() throws Exception {

		int userChoice = -1;
		boolean loggedIn = true;

		while (loggedIn) {

			showMenu();
			userChoice = Genio.getInteger();

			switch (userChoice) {

				case 0:
					System.out.println("You have been logged out successfully!");
					loggedIn = false;
					break;

				case 1:
					addAppointment();
					processUserChoice();
					break;

				case 2:
					editAppointment();
					processUserChoice();
					break;

				case 3:
					deleteAppointment();
					processUserChoice();
					break;

				case 4:
					// TODO undo
					processUserChoice();
					break;

				case 5:
					//TODO redo
					processUserChoice();
					break;

				case 6:
					displayDiary(staff, activeUser.getId());
					processUserChoice();
					break;

				case 7:
					backupDiary();
					processUserChoice();
					break;

				case 8:
					restoreDiary();
					processUserChoice();
					break;

				case 9:
					//TODO addTask
					processUserChoice();
					break;

				case 10:
					//TODO deleteTask
					processUserChoice();
					break;

				case 11:
					//TODO displayTask
					processUserChoice();
					break;

				case 12:
					//TODO change password
					processUserChoice();
					break;

				case 13:
					//TODO change personal details
					processUserChoice();
					break;

				default:
					System.out.println("Something went wrong");
			}
		} return userChoice;
	}

	/**
	 * This method displays diary. Gets list of professionals, streams over each professional with filter
	 * based on professionalID. flatMap makes it possible access from professional to ElectornicDiary
	 * and from ElectronicDiary to Appointment.
	 */
	private void displayDiary(Staff staff, Long professionalId) {
		Set<Professional> professionals = staff.getStaff();
		professionals.stream().filter(professional -> Objects.nonNull(professionalId) && professional.getId() == professionalId).
				flatMap(professional -> professional.getDiary().getAppointments().stream()).forEach(System.out::println);
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
		//		Appointment newAppointment = staff.bookAppointment(startTime, endTime, room, treatmentType, professionals);
		Appointment newAppointment = staff.bookAppointment(null, null, null, null, null, null);
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
		Appointment oldAppointment = staff.searchAppointment(Collections.singletonList(activeUser), activeUser.getId(), appointmentId);
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
		Appointment deletedAppointment = staff.deleteAppointment(Collections.singletonList(activeUser), appointmentId);
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
	public void logRunningTime() {
		// TODO - implement Menu.logRunningTime
		
	}

	/**
	 * Iterate Over the professional and get all the appointment and save them as a backup
	 */
	private void backupDiary() throws Exception {
		save(staff);
	}


	/**
	 * Try-with-resources method allowing to save data to txt file.
	 * Note there is no finally block because it's try-with-resources statement
	 */
	private void save(Staff staff) throws Exception {
		try(FileOutputStream out = new FileOutputStream("c:\\backupDiary.txt");
			ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(staff);
		}
	}


	/**
	 * Restore all the backup Diary.
	 * Note there is no finally block because it's try-with-resources statement
	 */
	private void restoreDiary() throws IOException {
		try (FileInputStream fin = new FileInputStream("c:\\backupDiary.txt");
			 ObjectInputStream ois = new ObjectInputStream(fin)) {
			Staff staff = (Staff) ois.readObject();
			displayDiary(staff, null);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}


	/**
	 * Gets the professional ID of the user who would like to use the system.
	 * After verification the professional will be able to make changes to their electronic diary.
	 */
	private void changeUser() {
		// TODO - implement Menu.changeUser

	}
}