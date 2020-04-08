package hospital;

import hospital.staff.*;
import hospital.undo_redo.Action;
import hospital.undo_redo.RedoNotPossibleException;
import hospital.undo_redo.UndoNotPossibleException;
import hospital.undo_redo.UndoRedoHandler;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
	private User activeUser;

	/**
	 * The handler which responsible for the undo/redo function of the system.
	 * Every action which needs to be undoable have to be registered by this handler.
	 */
	private final UndoRedoHandler undoRedoHandler;

	/**
	 * Constructor of the Menu class.
	 */
	public Menu() {

		//TODO handle exceptions
		staff = new Staff();
		restoreStaff();

		activeUser = null;
		undoRedoHandler = new UndoRedoHandler();
	}

	/**
	 * TODO
	 *
	 * @param args
	 */
	public static void main(String[] args){

		Menu menu = new Menu();
		String input;

		//Displays start menu to the user
		do {

			input = menu.startMenu();
			if(input.equals("1"))
			{
				//User logs in
				String detectUser = menu.logIn();

				//Appropriate menu is shown depending on user type
				if (detectUser != null) {
					if(detectUser.equals("admin")) menu.processAdminChoice();
					//TODO handle exceptions
					else if(detectUser.equals("professional"))  menu.processUserChoice();
					else continue;
				}
			} else if (input.equals("0")) {
				System.out.println("You have exited the system.");
				menu.backupStaff();
				// TODO backup treatmentTypes, id counters, and any other important static fields.
				System.exit(1);
			} else {
				System.out.println("Not a valid input");
			}
		} while (true);

	}

	/**
	 * Displays the menu options
	 */
	private void showMenu() {

		System.out.println("\nAppointment management:\n");
		System.out.println("1. Search for an appointment");
		System.out.println("2. Display the electronic diary");
		System.out.println("3. Backup the electronic diary");
		System.out.println("4. Restore the latest backup of the electronic diary");
		System.out.println("5. Undo last action");
		System.out.println("6. Redo last action");

		System.out.println("\nTask management:\n");
		System.out.println("7. Add a new task");
		System.out.println("8. Remove a task");
		System.out.println("9. Display the task list");

		System.out.println("\nOther:\n");
		System.out.println("10. Change your password");
		System.out.println("11. Change your personal details");
		System.out.println("12. Change your working schedule");

		System.out.println("13. Help");
		System.out.println("\n0. Log out");

	}

	/**
	 * Displays the menu for the administrator
	 */
	private void showAdminMenu()
	{
		System.out.println("\nAppointment management:\n");
		System.out.println("1. Add a new appointment");
		System.out.println("2. Edit an existing appointment");
		System.out.println("3. Remove an appointment");
		System.out.println("4. Search for an appointment");
		System.out.println("5. Undo last action");
		System.out.println("6. Redo last action");
		System.out.println("\nStaff management:\n");
		System.out.println("7. Add a new staff member");
		System.out.println("8. Remove a staff member");
		System.out.println("\nTreatment type management:\n");
		System.out.println("9. Add a new treatment type");
		System.out.println("10. Show available treatment types");
		System.out.println("\nOther:\n");
		System.out.println("11. Edit personal information");
		System.out.println("12. Change password");
		System.out.println("13. Help");
		System.out.println("\n0. Log out");


	}

	/**
	 * Processes administrator choices.
	 */
	public void processAdminChoice() {

		String userChoice;
		Scanner s = new Scanner(System.in);
		boolean loggedIn = true;

		while (loggedIn) {

			showAdminMenu();
			userChoice = s.nextLine();

			switch (userChoice) {

				case "0":
					logOut();
					loggedIn = false;
					break;

				case "1":
					addAppointment();
					break;

				case "2":
					editAppointment();
					break;

				case "3":
					deleteAppointment();
					break;

				case "4":
					searchAppointment();
					break;

				case "5":
					try {
						if (undoRedoHandler.undo() == null)
						{
							System.out.println("No actions found to undo.");
						}

					} catch (UndoNotPossibleException e) {
						System.out.println("There is nothing to undo.");
					}
					break;

				case "6":
					try {
						if (undoRedoHandler.redo() == null)
						{
							System.out.println("No actions found to redo.");
						}
					} catch (RedoNotPossibleException e) {
						System.out.println("There is nothing to redo.");
					}
					break;

				case "7":
					addStaffMember();
					break;

				case "8":
					removeStaffMember();
					break;

				case "9":
					addTreatmentType();
					break;

				case "10":
					TreatmentType.displayTreatments();
					break;

				case "11":
					changeDetails();
					break;

				case "12":
					changePassword();
					break;

				case "13":
					showManual();
					break;

				default:
					System.out.println("Invalid user input. Please try again.");
					break;
			}
		}
	}

	/**
	 * Method to process users choice from the menu and activate corresponding function.
	 * Please note that it uses a Genio class written by UoD to handle data input.
	 */

	public void processUserChoice() {

		int userChoice = -1;
		boolean loggedIn = true;

		while (loggedIn) {

			showMenu();

			userChoice = Genio.getInteger();

			switch (userChoice) {

				case 0:
					logOut();
					loggedIn = false;
					break;

				case 1:
					searchAppointment();
					break;

				case 2:
					displayAppointments(((Professional) activeUser).getDiary().sortByDate());
					break;

				case 3:
					backupStaff();
					break;

				case 4:
					restoreStaff();
					break;

				case 5:
					try {
						if (undoRedoHandler.undo() == null)
						{
							System.out.println("No actions found to undo.");
						}
					} catch (UndoNotPossibleException e) {
						System.out.println("There is nothing to undo.");
					}
					break;

				case 6:
					try {
						if (undoRedoHandler.redo() == null)
						{
							System.out.println("No actions found to redo.");
						}
					} catch (RedoNotPossibleException e) {
						System.out.println("There is nothing to redo.");
					}
					break;

				case 7:
					addTask();
					break;

				case 8:
					removeTask();
					break;

				case 9:
					displayTaskList();
					break;

				case 10:
					changePassword();
					break;

				case 11:
					changeDetails();
					break;

				case 12:
					editWorkingHours();
					break;

				case 13:
					showManual();
					break;


				default:
					System.out.println("Invalid user input. Please try again.");
					break;
			}
		}
	}

	/**
	 * Displays a list of appointments in a table.
	 *
	 * @param appointments The list of appointments to display
	 */
	private void displayAppointments(List<Appointment> appointments) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MMM/uuuu HH");

		// Table header
		displayTableRowDivider();
		System.out.println(generateTableRow("ID", "Start time", "End time", "Treatment type", "Room", "Professionals"));
		displayTableRowDivider();
		displayTableRowDivider();

		// Table rows
		for (Appointment appointment : appointments) {
			List<Professional> professionals = appointment.getProfessionals();
			// Data
			System.out.println(generateTableRow(
					String.valueOf(appointment.getId()),
					appointment.getStartTime().format(dateFormat),
					appointment.getEndTime().format(dateFormat),
					appointment.getTreatmentType().getLabel(),
					appointment.getRoom(),
					professionals.get(0).getFirstName() + " " + professionals.get(0).getLastName()));
			// Rest of the professionals
			for (int i = 1; i < professionals.size(); i++) {
				System.out.println(generateTableRow("", "", "", "", "",
						professionals.get(i).getFirstName() + " " + professionals.get(i).getLastName()));
			}
			displayTableRowDivider();
		}
	}

	private String generateTableRow(String cell1, String cell2, String cell3, String cell4, String cell5, String cell6) {
		return String.format("|%6s|%15s|%15s|%30s|%12s|%30s|", cell1, cell2, cell3, cell4, cell5, cell6);
	}

	private void displayTableRowDivider() {
		System.out.println(generateTableRow("", "", "", "", "", "").replace(' ', '-'));
	}

	/**
	 * This method displays an appointment's information with the given appointment and professional's IDs
	 */
	private void searchAppointment()
	{
		Scanner s = new Scanner(System.in);
		boolean IDfound = false;
		long appID=-1;
		//get the appointment ID from user input
		while(!IDfound)
		{
			System.out.println("Enter appointment ID: ");
			try {
				String input = s.nextLine();
				appID = Long.parseLong(input);
				IDfound=true;
			}
			catch(NumberFormatException ex)
			{
				System.out.println("Input invalid, try again!");
			}
		}
		Appointment foundAppointment = staff.searchAppointment(appID);
		if (foundAppointment == null) System.out.println("Appointment not found.");
		else {
			displayAppointments(Collections.singletonList(foundAppointment));
		}
	}

	/**
	 * Get inputs from the user for a new appointment, verify it and adds it to the database.
	 */
	private void addAppointment() {
		// The objects needed to book a new appointment
		Scanner in = new Scanner(System.in);
		Scanner stringSc = new Scanner(System.in);

		List<Professional> professionals = new ArrayList<>();
		LocalDateTime from = LocalDateTime.now();
		LocalDateTime until = LocalDateTime.now();
		String room = "<undefined>";
		TreatmentType treatmentType;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu HH");

		// Get input for treatment
		List<TreatmentType> treatments = new ArrayList<>(TreatmentType.getTreatmentTypes());
		int treatInput = -1;
		while (treatInput >= treatments.size() || treatInput < 0) {
			System.out.println("\nPlease choose a treatment type you'd like to book an appointment for:");
			TreatmentType.displayTreatments();

			if (in.hasNextInt()) {
				treatInput = in.nextInt();
			} else {
				in.nextLine();
				System.out.println("Invalid input.");
			}
		}
		treatmentType = treatments.get(treatInput);

		// Collect all the competent professionals needed for the treatment
		Map<Role, List<Professional>> competentProfessionals = new HashMap<>(treatmentType.getRequiredRoles().size());
		for (Role role : treatmentType.getRequiredRoles()) {
			competentProfessionals.put(role, staff.getProfessionalsByRole(role));
		}

		// Get input for the time interval
		List<Appointment> availableSlots = new ArrayList<>();
		while (availableSlots.size() == 0) {
			System.out.println("\nPlease provide an interval you'd like to book the appointment in. (dd-mm-year hh) or 0 to return");
			while (true) {
				System.out.print("From: ");
				try {

					String input = stringSc.nextLine();
					if (input.equals("0")) return;

					from = LocalDateTime.parse(input, dateFormat);
					break;
				} catch (DateTimeParseException e) {
					System.out.println("Invalid format. Please use a day-month-year hour format in this way: dd-mm-year hh");
				}
			}
			while (true) {
				System.out.print("Till: ");
				try {
					String input = stringSc.nextLine();
					until = LocalDateTime.parse(input, dateFormat);
					break;
				} catch (DateTimeParseException e) {
					System.out.println("Invalid format. Please use a day-month-year hour format in this way: dd-mm-year hh");
				}
			}

			// Choose a professional for every role who has the most empty slot in the given interval.
			LocalDateTime finalFrom = from;
			LocalDateTime finalUntil = until;
			professionals = competentProfessionals.values().stream()
					.map(professionalList -> professionalList.stream()
							.max(Comparator.comparingInt(professional -> professional.searchAvailability(finalFrom, finalUntil).size()))
							.orElse(null))
					.collect(Collectors.toList());
			availableSlots = staff.searchAvailability(professionals, from, until);
			if (availableSlots.size() == 0) {
				System.out.println("There are no available appointments in the given interval.");
				System.out.println("Please provide a new one.");
			}
		}

		int slotInput = -1;
		while (slotInput < 0 || slotInput >= availableSlots.size()) {
			System.out.println("\nChoose one of the available slots for the appointment:\n");
			for (int i = 0; i < availableSlots.size(); i++) {
				Appointment appointment = availableSlots.get(i);
				System.out.println(i + ". " + appointment.getStartTime().format(dateFormat));
			}
			if (in.hasNextInt()) {
				slotInput = in.nextInt();
				in.nextLine();
			} else {
				in.nextLine();
				System.out.println("Invalid input.");
			}
		}

		// Get input for the room
		System.out.println("\nPlease provide a room where the treatment will take place:");
		String input = in.nextLine();
		if (!input.isBlank()) room = input;

		// Book the appointment
		Appointment newAppointment = staff.bookAppointment(professionals, availableSlots.get(slotInput).getStartTime(), availableSlots.get(slotInput).getEndTime(), room, treatmentType);
		displayAppointments(Collections.singletonList(newAppointment));
		if (newAppointment != null) {
			try {
				undoRedoHandler.addAction(new BookAppointmentAction(
						"Add appointment",
						staff,
						newAppointment
				));
			} catch (NoSuchMethodException | IllegalArgumentException e) {
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
		List<Professional> professionals = new ArrayList<>();
		LocalDateTime from = LocalDateTime.now();
		LocalDateTime until = LocalDateTime.now();
		String room = "";
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu HH");

		Scanner in = new Scanner(System.in);
		Scanner stringSc = new Scanner(System.in);

		Appointment oldAppointment = null;

		// Get the appointment to modify
		while (true) {
			System.out.println("Please enter the ID of the appointment you'd like to modify, or 0 to return");
			try {
				appointmentId = Long.parseLong(in.nextLine());
				if (appointmentId < 0) {
					throw new NumberFormatException();
				} else if (appointmentId == 0) {
					return;
				}
				oldAppointment = staff.searchAppointment(appointmentId);
				if (oldAppointment == null) {
					System.out.println("No appointment found with this ID.");
				} else {
					System.out.println("The appointment:");
					displayAppointments(Collections.singletonList(oldAppointment));
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid ID");
			}
		}

		// Collect all the competent professionals needed for the treatment
		Map<Role, List<Professional>> competentProfessionals = new HashMap<>(oldAppointment.getTreatmentType().getRequiredRoles().size());
		for (Role role : oldAppointment.getTreatmentType().getRequiredRoles()) {
			competentProfessionals.put(role, staff.getProfessionalsByRole(role));
		}

		// Get input for the time interval
		List<Appointment> availableSlots = new ArrayList<>();
		while (availableSlots.size() == 0) {
			System.out.println("\nPlease provide an interval you'd like to book the appointment in. (dd-mm-year hh) or 0 to return");
			while (true) {
				System.out.print("From: ");
				try {

					String input = stringSc.nextLine();
					if (input.equals("0")) return;

					from = LocalDateTime.parse(input, dateFormat);
					break;
				} catch (DateTimeParseException e) {
					System.out.println("Invalid format. Please use a day-month-year hour format in this way: dd-mm-year hh");
				}
			}
			while (true) {
				System.out.print("Till: ");
				try {
					String input = stringSc.nextLine();
					until = LocalDateTime.parse(input, dateFormat);
					break;
				} catch (DateTimeParseException e) {
					System.out.println("Invalid format. Please use a day-month-year hour format in this way: dd-mm-year hh");
				}
			}

			// Choose a professional for every role who has the most empty slot in the given interval.
			LocalDateTime finalFrom = from;
			LocalDateTime finalUntil = until;
			professionals = competentProfessionals.values().stream()
					.map(professionalList -> professionalList.stream()
							.max(Comparator.comparingInt(professional -> professional.searchAvailability(finalFrom, finalUntil).size()))
							.orElse(null))
					.collect(Collectors.toList());
			availableSlots = staff.searchAvailability(professionals, from, until);
			if (availableSlots.size() == 0) {
				System.out.println("There are no available appointments in the given interval.");
				System.out.println("Please provide a new one.");
			}
		}

		int slotInput = -1;
		while (slotInput < 0 || slotInput >= availableSlots.size()) {
			System.out.println("\nChoose one of the available slots for the appointment:\n");
			for (int i = 0; i < availableSlots.size(); i++) {
				Appointment appointment = availableSlots.get(i);
				System.out.println(i + ". " + appointment.getStartTime().format(dateFormat));
			}
			if (in.hasNextInt()) {
				slotInput = in.nextInt();
				in.nextLine();
			} else {
				in.nextLine();
				System.out.println("Invalid input.");
			}
		}

		// Get input for the room
		System.out.println("\nPlease provide a room where the treatment will take place:");
		String input = in.nextLine();
		if (!input.isBlank()) room = input;

		Appointment modifiedAppointment = staff.editAppointment(appointmentId, professionals, availableSlots.get(slotInput).getStartTime(), availableSlots.get(slotInput).getEndTime(), room);
		displayAppointments( Collections.singletonList( modifiedAppointment ) );
		if (modifiedAppointment != null && !modifiedAppointment.equals(oldAppointment)) {
			try {
				undoRedoHandler.addAction(new Action(
						"Edit appointment",
						staff,
						staff.getClass().getMethod("editAppointment", long.class, List.class, LocalDateTime.class, LocalDateTime.class, String.class),
						new Object[]{oldAppointment.getId(), oldAppointment.getProfessionals(), oldAppointment.getStartTime(), oldAppointment.getEndTime(), oldAppointment.getRoom()},
						staff.getClass().getMethod("editAppointment", long.class, List.class, LocalDateTime.class, LocalDateTime.class, String.class),
						new Object[]{modifiedAppointment.getId(), modifiedAppointment.getProfessionals(), modifiedAppointment.getStartTime(), modifiedAppointment.getEndTime(), modifiedAppointment.getRoom()}
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
		Scanner s = new Scanner(System.in);
		boolean IDfound=false;

		//get the appointment ID from user input
		while(!IDfound)
		{
			System.out.println("Enter appointment to delete ID: ");
			//check if input is valid ID
			if(s.hasNextLong()) {
				appointmentId = s.nextLong();
				IDfound=true;
			}
			else System.out.println("Input not valid!");
		}

		Appointment deletedAppointment = staff.deleteAppointment(appointmentId);

		if (deletedAppointment != null) {
			System.out.println("The appointment has been deleted successfully.");
			try {
				undoRedoHandler.addAction(new DeleteAppointmentAction(
						"Delete appointment",
						staff,
						deletedAppointment
				));
			} catch (NoSuchMethodException | IllegalArgumentException e) {
				// TODO handle exception
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Appointment was not found, nothing to delete.");
		}
	}


	/**
	 * Safe staff and all of its data to be able to restore later
	 */
	private void backupStaff() {
		try(FileOutputStream out = new FileOutputStream("backupStaff.txt");
			ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(staff);
		} catch (FileNotFoundException e) {
			System.out.println("No backup file found for restoring the Staff's data.");
		} catch (IOException e) {
			// TODO handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Restore the staff and all of its data.
	 */
	private void restoreStaff() {
		try (FileInputStream fin = new FileInputStream("backupStaff.txt");
			 ObjectInputStream ois = new ObjectInputStream(fin)) {
			staff = (Staff) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("The last backup could not be restored as the file could not be found.");
		} catch (InvalidClassException e)
		{
			System.out.println("The last backup could not be restored as the structure of the program has been changed.");
		} catch (IOException e) {
			// TODO handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Allows the active user to change the password.
	 */
	private void changePassword()
	{
		Scanner s = new Scanner(System.in);
		String password, confirmPassword;

		System.out.println("Please enter your new password:");
		password = s.nextLine();

		System.out.println("\nPlease confirm your new password:");
		confirmPassword = s.nextLine();

		if(password.equals(confirmPassword)) activeUser.setPassword(password);
		else System.out.println("Passwords do no match. Try again later.");
	}

	/**
	 * Start menu that is shown upon starting the program.
	 * Allows to user to either login or to exit the system.
	 *
	 * @return received input of the user: 1 for login, 0 to exit the system
	 */
	private String startMenu()
	{
		do {
			System.out.println("\nWelcome to the hospital scheduler.\n");
			System.out.println("Please choose one of the following options:\n");

			System.out.println("1. Log-in");
			System.out.println("0. Exit");

			Scanner s = new Scanner(System.in);
			String input = s.nextLine();

			switch (input)
			{
				case "1":
				case "0":
					return input;

				default: System.out.println("Wrong user input. Please try again."); break;
			}
		} while (true);


	}

	/**
	 * Logs the user out of the system.
	 */
	private void logOut() {

		System.out.println("Thank you for using the system.");
		activeUser = null;
		undoRedoHandler.clearHistory();
	}

	/**
	 * Prompts the user to log-in
	 * @return "admin" or "professional" depending on
	 * who logged in to the system, or "noUser" if logging in was unsuccessful.
	 */
	private String logIn()
	{
		Scanner s = new Scanner(System.in);
		String username, password;
		boolean retry = false;
		boolean isPasswordCorrect = false;

		do {
			try {
				System.out.println("Please log-in to access the system, or enter 0 to go back.\n");
				System.out.println("If you're logging in for the first time, your password is set to be 'default'.");
				System.out.println("Your username is your first and last name combined in lowercase letters, or 'admin' by default if you're an administrator.\n");
				System.out.println("Enter your username:");

				username = s.nextLine();
				if(username.equals("0")) return "noUser";

				if(username.equals(staff.getAdmin().getUsername())) activeUser = staff.getAdmin();
				else activeUser = staff.searchByUsername(username);

				System.out.println("\nEnter your password:");
				password = s.nextLine();

				isPasswordCorrect = activeUser.checkPassword(password);
				if(isPasswordCorrect)
				{
					System.out.println("You've successfully logged in! Welcome, " + activeUser.getFirstName() +".");
					if(activeUser.checkPassword("default")) System.out.println("Please don't forget to change the default password.");
					retry = false;

					//Informs system whether admin or a professional has logged in
					if(activeUser.getRole().equals(Role.Administrator)) return "admin";
					else return "professional";
				}
				else{
					System.out.println("Your password is incorrect. Please try again.");
					activeUser = null;
					retry = true;
				}

			} catch (NullPointerException e) {
				System.out.println("No such username found. Please try again.");
				retry = true;
			}
		} while (retry);

		return null;

	}

	/**
	 * Updates the user details.
	 * User is able to change their first and last names and the office.
	 *
	 */
	private void changeDetails()
	{
		Scanner s = new Scanner(System.in);
		String userInput;
		String warning = "\nWARNING! If you're a professional, this will change your username. Proceed with caution.\n";

		do {
			System.out.println("\nPlease specify which data you want to change:");
			System.out.println("1. First name");
			System.out.println("2. Last name");
			System.out.println("3. Office");
			System.out.println("0. Exit");

			userInput = s.nextLine();

			switch (userInput)
			{
				case "1":{
					System.out.println(warning);
					Scanner name = new Scanner(System.in);
					String newName;

					System.out.println("Enter your new name:");
					newName = name.nextLine();
					String oldName = activeUser.getFirstName();

					activeUser.setFirstName(newName);
					System.out.println("Name changed successfully.");

					//If active user is a professional, username is updated
					if(activeUser.getClass().isInstance(Professional.class))
					{
						activeUser.updateUsername();
						System.out.println("Your new username is " + activeUser.getUsername());
					}

					try {
						undoRedoHandler.addAction(new Action(
								"First Name modification",
								activeUser,
								activeUser.getClass().getMethod("setFirstName", String.class),
								new Object[] {oldName},
								activeUser.getClass().getMethod("setFirstName", String.class),
								new Object[] {newName}
						));
					} catch (NoSuchMethodException e) {
						// TODO handle error
						e.printStackTrace();
					}
				} break;
				case "2":{

					System.out.println(warning);
					Scanner name = new Scanner(System.in);
					String newName;

					System.out.println("Enter your new last name:");
					newName = name.nextLine();
					String oldName = activeUser.getLastName();

					activeUser.setLastName(newName);
					System.out.println("Last name changed successfully.");

					activeUser.updateUsername();
					System.out.println("Your new username is " + activeUser.getUsername());

					try {
						undoRedoHandler.addAction(new Action(
								"Last Name modification",
								activeUser,
								activeUser.getClass().getMethod("setLastName", String.class),
								new Object[] {oldName},
								activeUser.getClass().getMethod("setLastName", String.class),
								new Object[] {newName}
						));
					} catch (NoSuchMethodException e) {
						// TODO handle error
						e.printStackTrace();
					}
				} break;
				case "3":
				{
					Scanner office = new Scanner(System.in);
					String newOffice;

					System.out.println("Enter your new office details:");
					newOffice = office.nextLine();
					String oldOffice = activeUser.getOffice();

					activeUser.setOffice(newOffice);
					System.out.println("Office changed successfully.");

					try {
						undoRedoHandler.addAction(new Action(
								"Office modification",
								activeUser,
								activeUser.getClass().getMethod("setOffice", String.class),
								new Object[] {oldOffice},
								activeUser.getClass().getMethod("setOffice", String.class),
								new Object[] {newOffice}
						));
					} catch (NoSuchMethodException e) {
						// TODO handle error
						e.printStackTrace();
					}
				} break;
				default: System.out.println("Wrong input. Please check it and try again.");break;
			}
		} while (!(userInput.equals("0")));

	}

	/**
	 * Adds a task to the active user's task list.
	 */
	private void addTask()
	{
		String taskName, description, input, dueBy;
		boolean retry = false;

		Scanner s = new Scanner(System.in);

		do {
			System.out.println("Please enter the task details, or 0 to return:\n");

			System.out.println("Enter the task name:");
			taskName = s.nextLine();
			if(taskName.equals("0")) return;
			else if (((Professional)activeUser).getTasks().findTask(taskName)!=null) {
				System.out.println("Task with this label already exists, try again.");
				continue;
			}


			System.out.println("Enter the task description:");
			description = s.nextLine();


			System.out.println("Enter the date when your task is due by (day-month-year format, e.g. 05-12-2020):");
			dueBy = s.nextLine();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu");
			LocalDate dueByDate;

			try {
				//Parsing the String
				dueByDate = LocalDate.parse(dueBy, dateFormat);
			} catch (DateTimeParseException e) {
				System.out.println("There was an error recording your due-by date, please try again.");
				retry = true;
				continue;
			}

			System.out.println("These are the details of your task:\n");
			System.out.println("Task name: " + taskName);
			System.out.println("Description: " + description);
			System.out.println("Due by: " + dueBy);

			System.out.println("\nConfirm the task by entering Y, or retry by providing any other input.");
			input = s.nextLine();

			if(input.equals("Y") || input.equals("y"))
			{
				//Checks for duplicates
				boolean success;
				success = (((Professional)activeUser).addTask(taskName,description,dueByDate));
				//Error message printed in TaskList class if duplicate

				if(success) {
					retry = false;
					System.out.println("Task was added successfully.");
					try {
						undoRedoHandler.addAction(new Action(
								"New task addition",
								activeUser,
								Professional.class.getMethod("deleteTask", String.class),
								new Object[] {taskName},
								Professional.class.getMethod("addTask", String.class, String.class, LocalDate.class),
								new Object[] {taskName, description, dueByDate}
						));
					} catch (NoSuchMethodException e) {
						// TODO handle error
						e.printStackTrace();
					}
				} else retry = true;

			}
			else
			{
				System.out.println("Task was not confirmed. Please retry.");
				retry = true;
			}

		} while (retry);

	}

	/**
	 * Removes a task from the active user's diary.
	 */
	private void removeTask()
	{
		Scanner s = new Scanner(System.in);
		String toDelete;

		boolean deleted = false;

		do {

			displayTaskList();
			System.out.println("\n\nPlease enter the task name you would like to delete, or enter 0 to return:");
			toDelete = s.nextLine();
			if(toDelete.equals("0")) return;
			Task task = (((Professional)activeUser).getTasks().findTask(toDelete));

			deleted = (((Professional)activeUser).deleteTask(toDelete));
			if(deleted) {
				System.out.println("Your task was deleted successfully.");
				try {
					undoRedoHandler.addAction(new Action(
							"Task deletion",
							activeUser,
							Professional.class.getMethod("addTask", String.class, String.class, LocalDate.class),
							new Object[] {task.getTaskName(), task.getDescription(), task.getDueBy()},
							Professional.class.getMethod("deleteTask", String.class),
							new Object[] {toDelete}
					));
				} catch (NoSuchMethodException e) {
					// TODO handle error
					e.printStackTrace();
				}
			} else System.out.println("There was an error finding your task. Please try again.");

		} while (!deleted);
	}

	/**
	 * Displays the task list of the active user.
	 */
	private void displayTaskList()
	{
		System.out.println("Your personal task list:\n");
		((Professional)activeUser).getTasks().displayTaskList();
	}

	/**
	 * Adds a new staff member to the crew.
	 * Part of administrator management.
	 */
	private void addStaffMember()
	{
		Scanner s = new Scanner(System.in);
		boolean exists, invalidRole = true, retry = false;
		String firstName, lastName, role, office, input;


		do {
			System.out.println("\nPlease specify the details about the new staff member, or 0 to return:\n");
			System.out.println("Please enter the first name of the professional:");
			firstName = s.nextLine();

			if(firstName.equals("0")) return;

			System.out.println("\nPlease enter the last name of the professional:");
			lastName = s.nextLine();

			do {
				System.out.println("\nPlease specify the role of the professional:");
				role = s.nextLine();

				exists = Role.checkIfRoleExists(role);
				if(!exists) {
					System.out.println("No such role. Please try again and choose one of the following roles.");
					Role.printRoles();
					invalidRole = true;
				}
				else invalidRole = false;

			} while (invalidRole);

			System.out.println("\nPlease specify the office of the professional:");
			office = s.nextLine();

			System.out.println("\nThese are the details of the new professional.\n");
			System.out.println("First name: " + firstName);
			System.out.println("Last name: " + lastName);
			System.out.println("Role: " + role);
			System.out.println("Office: " + office);

			System.out.println( "\nEnter Y to confirm");
			input = s.nextLine();

			if(input.equals("Y") || input.equals("y"))
			{

				Professional newMember = new Professional(firstName, lastName, Role.valueOf(role), office);
				staff.addMember(newMember);
				try {
					undoRedoHandler.addAction(new Action(
							"New staff member addition",
							staff,
							staff.getClass().getMethod("removeMember", Professional.class),
							new Object[] {newMember},
							staff.getClass().getMethod("addMember", Professional.class),
							new Object[] {newMember}
					));
				} catch (NoSuchMethodException e) {
					// TODO handle exception
					e.printStackTrace();
				}

				System.out.println("\nStaff member added successfully.");
				retry = false;
			}
			else {
				System.out.println("\nAddition was not confirmed. Please try again.");
				retry = true;
			}


		} while (retry);

	}

	/**
	 * Removes a staff member from the crew.
	 * Part of administrator management.
	 */
	private void removeStaffMember()
	{
		Scanner s = new Scanner(System.in);
		Professional toDelete;
		boolean retry = false;
		String firstName, lastName, input;


		do {
			System.out.println("\nPlease specify the details about the staff member to delete, or 0 to return:\n");
			System.out.println("Please enter the first name of the professional:");
			firstName = s.nextLine();

			if (firstName.equals("0")) return;

			System.out.println("\nPlease enter the last name of the professional:");
			lastName = s.nextLine();

			toDelete = staff.searchByUsername((firstName + lastName).toLowerCase());
			if (toDelete == null)
			{
				System.out.println("No such professional found. Try again.");
				retry = true;
				continue;
			}

			System.out.println("\nThese are the details of the professional to delete.\n");
			System.out.println("First name: " + firstName);
			System.out.println("Last name: " + lastName);

			System.out.println( "Enter Y to confirm deletion.");
			input = s.nextLine();

			if(input.equals("Y") || input.equals("y"))
			{
				staff.removeMember(toDelete);
				try {
					undoRedoHandler.addAction(new Action(
							"Staff member deletion",
							staff,
							staff.getClass().getMethod("addMember", Professional.class),
							new Object[] {toDelete},
							staff.getClass().getMethod("removeMember", Professional.class),
							new Object[] {toDelete}
							));
				} catch (NoSuchMethodException e) {
					// TODO handle exception
					e.printStackTrace();
				}

				System.out.println("\nStaff member deleted successfully.");
				retry = false;
			}
			else {
				System.out.println("Deletion was not confirmed. Please try again.");
				retry = true;
			}


		} while (retry);

	}

	/**
	 * Adds a new treatment type to the hospital.
	 * Part of administrator management.
	 */
	private void addTreatmentType()
	{
		Scanner s = new Scanner(System.in);
		Scanner scanRole = new Scanner(System.in);
		Scanner integer = new Scanner(System.in);
		boolean retry = false, integerValid = false, exists;


		int numberOfProfessionals = 0;
		String label, role;
		List<Role> requirements = new ArrayList<>();

		do {

			System.out.println("Please provide the label for the new treatment type, or enter 0 to return:");
			label = s.nextLine();

			if(label.equals("0")) return;
			//Checks whether treatment already exists
			if (TreatmentType.searchForTreatment(label) !=null){
				System.out.println("This treatment already exists. Please try again.");
				retry = true;
				continue;
			}

			//Loop to handle wrong integer input.
			do {
				System.out.println("Please specify how many professionals are needed for the treatment:");
				try {
					numberOfProfessionals = integer.nextInt();
					integerValid = true;

				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Try again.");
					integerValid = false;
				}
			} while (!integerValid);


				int counter = 0;
				while(counter < numberOfProfessionals) {

					System.out.println(counter+1  + " out of " + numberOfProfessionals + ": please specify the role of the professional:");
					role = scanRole.nextLine();

					//Adjusts the input to match the enum grammar
					role = role.substring(0,1).toUpperCase() + role.substring(1).toLowerCase();
					exists = Role.checkIfRoleExists(role);
					if (exists) {
						//If role exists, it's added to the requirements
						requirements.add(Role.valueOf(role));
						counter++;
					} else {

						System.out.println("No such role. Try again.");
						continue;
					}
				}

			TreatmentType.addTreatmentType(label, requirements);
			System.out.println("Treatment was successfully added to the database.");
			retry = false;

		} while (retry);

	}

	/**
	 * Edits the working hours of the Professional
	 */
	public void editWorkingHours()
	{
		Scanner scanInt = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		String input;

		DayOfWeek chosenDay = null;
		boolean validInput = true, retry = true;

		LocalTime startTime = null, endTime = null;
		String start, end;

		do {
			do {


				System.out.println("Please choose which working day you would like to edit:");
				System.out.println("1. Monday");
				System.out.println("2. Tuesday");
				System.out.println("3. Wednesday");
				System.out.println("4. Thursday");
				System.out.println("5. Friday");
				System.out.println("6. Saturday");
				System.out.println("7. Sunday");
				System.out.println("\n0. Return");

				input = scanInt.nextLine();

				switch(input)
				{
					case "1": chosenDay = DayOfWeek.MONDAY; validInput = true;break;
					case "2": chosenDay = DayOfWeek.TUESDAY;validInput = true; break;
					case "3": chosenDay = DayOfWeek.WEDNESDAY;validInput = true; break;
					case "4": chosenDay = DayOfWeek.THURSDAY;validInput = true; break;
					case "5": chosenDay = DayOfWeek.FRIDAY;validInput = true; break;
					case "6": chosenDay = DayOfWeek.SATURDAY;validInput = true; break;
					case "7": chosenDay = DayOfWeek.SUNDAY;validInput = true; break;
					case "0": return;
					default:System.out.println("Invalid input, try again."); validInput = false;
				}
			} while (!validInput);


			do {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");


				System.out.println("\nPlease enter new working hour standards for the specified day in the format HH:\n");
				System.out.println("Please enter your shift start time:");
				start = scanString.nextLine();
				System.out.println("Please enter your shift end time:");
				end = scanString.nextLine();


				try {

					//Parsing the String
					startTime = LocalTime.parse(start, dtf);
					endTime = LocalTime.parse(start, dtf);

					retry = false;
				} catch (DateTimeParseException e) {
					System.out.println("There was an error recording your shift times, please try again.");
					retry = true;
					continue;
				}
			} while (retry);

			Map<DayOfWeek, WorkingHours>  newWorkingHours = ((Professional)activeUser).getWorkingHours();

			for (Map.Entry<DayOfWeek, WorkingHours> entry : newWorkingHours.entrySet()) {

				//If the day of the week match of the input and the working hours set,
				if(entry.getKey().equals(chosenDay))
				{
					//New values are set
					entry.getValue().setStartHour(startTime);
					entry.getValue().setEndHour(endTime);

					break;
				}
			}

			List<Appointment> appointments = ((Professional) activeUser).getDiary().getAppointments();
			int startHour = Integer.parseInt(start);
			int endHour = Integer.parseInt(end);
			for(Appointment app : appointments)
			{
				if(app.getStartTime().getDayOfWeek()==chosenDay)
				{
					if((app.getStartTime().getHour()<startHour)||(app.getEndTime().getHour()>endHour))
					{
						appointments.remove(app);
					}
				}
			}

			System.out.println("Your schedule has been updated.\n");
		} while (true);
	}

	public void showManual() {
		if (Desktop.isDesktopSupported()) {
			try {
				File myFile = new File("UserManual.pdf");
				Desktop.getDesktop().open(myFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
