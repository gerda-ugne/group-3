package hospital;

import hospital.staff.DeleteAppointmentAction;
import hospital.undo_redo.Action;
import hospital.staff.Appointment;
import hospital.staff.Professional;
import hospital.staff.Staff;
import hospital.undo_redo.UndoRedoHandler;

import java.io.*;
import java.time.LocalDateTime;
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
		System.out.println("0. Log out");

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
					break;

				case 2:
					editAppointment();
					break;

				case 3:
					deleteAppointment();
					break;

				case 4:
					// TODO undo
					break;

				case 5:
					//TODO redo
					break;

				case 6:
					displayDiary(staff, activeUser.getId());
					break;

				case 7:
					backupDiary();
					break;

				case 8:
					restoreDiary();
					break;

				case 9:
					//TODO addTask
					break;

				case 10:
					//TODO deleteTask
					break;

				case 11:
					//TODO displayTask
					break;

				case 12:
					//TODO change password
					break;

				case 13:
					//TODO change personal details
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
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();
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
						staff.getClass().getMethod("bookAppointment", List.class, LocalDateTime.class, LocalDateTime.class, String.class, String.class),
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
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();
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
						staff.getClass().getMethod("editAppointment", long.class, long.class, List.class, LocalDateTime.class, LocalDateTime.class, String.class, String.class),
						new Object[]{activeUser.getId(), oldAppointment.getId(), oldAppointment.getProfessionals(), oldAppointment.getStartTime(), oldAppointment.getEndTime(), oldAppointment.getRoom(), oldAppointment.getTreatmentType()},
						staff.getClass().getMethod("editAppointment", long.class, long.class, List.class, LocalDateTime.class, LocalDateTime.class, String.class, String.class),
						new Object[]{activeUser.getId(), oldAppointment.getId(), oldAppointment.getProfessionals(), oldAppointment.getStartTime(), oldAppointment.getEndTime(), oldAppointment.getRoom(), oldAppointment.getTreatmentType()}
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
	 * Logs the user out of the system.
	 */
	private void logOut() {

		System.out.println("Thank you for using the system.");
		activeUser = null;

		//Do we need to reset the undo-redo handler here too?
	}

	/**
	 * Prompts the user to log-in
	 */
	private void logIn()
	{
		Scanner s = new Scanner(System.in);
		String username, password;
		boolean retry = false;
		boolean isPasswordCorrect = false;

		do {
			try {
				System.out.println("Please log-in to access the system.\n");
				System.out.println("If you're logging in for the first time, your password is set to be 'default'.");
				System.out.println("Your username is the first letter of your name, followed by your last name.\n");
				System.out.println("Enter your username:");

				username = s.nextLine();
				activeUser = staff.searchByUsername(username);

				System.out.println("\nEnter your password:");
				password = s.nextLine();

				isPasswordCorrect = activeUser.checkPassword(password);
				if(isPasswordCorrect)
				{
					System.out.println("You've successfully logged in! Welcome, " + activeUser.getFirstName() +".");
					if(activeUser.checkPassword("default")) System.out.println("Please don't forget to change the default password.");
					retry = false;
				}
				else{
					System.out.println("Your password is incorrect. Please try again.");
					retry = true;
				}

			} catch (NullPointerException e) {
				System.out.println("No such username found. Please try again.");
				retry = true;
			}
		} while (retry);

	}
}