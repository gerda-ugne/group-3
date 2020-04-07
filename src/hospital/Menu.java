package hospital;

import hospital.staff.*;
import hospital.undo_redo.Action;
import hospital.undo_redo.UndoRedoHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
	private final Staff staff;

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
	 * TODO
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
	 * Displays the menu for the administrator
	 */
	private void showAdminMenu()
	{
		System.out.println("\nStaff management:\n");
		System.out.println("1. Add a new staff member");
		System.out.println("2. Remove a staff member");
		System.out.println("\nTreatment type management:\n");
		System.out.println("3. Add a new treatment type");
		System.out.println("\nOther:\n");
		System.out.println("4. Edit personal information");
		System.out.println("5. Change password");
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
					addStaffMember();
					break;

				case "2":
					removeStaffMember();
					break;

				case "3":
					addTreatmentType();
					break;
				case "4":
					changeDetails();
					break;
				case "5":
					changePassword();
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
					backupStaff();
					// TODO remove this menu item, add searchAppointment
					break;

				case 8:
					restoreStaff();
					// TODO remove this menu item
					break;

				case 9:
					addTask();
					break;

				case 10:
					removeTask();
					break;

				case 11:
					displayTaskList();
					break;

				case 12:
					changePassword();
					break;

				case 13:
					changeDetails();
					break;

				default:
					System.out.println("Invalid user input. Please try again.");
					break;
			}
		}
	}

	/**
	 * This method displays diary.
	 * Gets list of professionals, streams over each professional with filter
	 * based on professionalID. flatMap makes it possible access from professional to ElectornicDiary
	 * and from ElectronicDiary to Appointment.
	 */
	private void displayDiary(Staff staff, Long professionalId) {
		Set<Professional> professionals = staff.getStaff();
		professionals.stream().filter(professional -> Objects.nonNull(professionalId) && professional.getId() == professionalId).
				flatMap(professional -> professional.getDiary().getAppointments().stream()).forEach(System.out::println);
	}

	/**
	 * This method displays an appointment's information with the given appointment and professional's IDs
	 */
	private void searchAppointment() {

		Scanner s = new Scanner(System.in);
		boolean IDfound=false;
		long inputID=-1;

		//get the appointment ID from user input
		while(!IDfound)
		{
			System.out.println("Enter appointment ID: ");
			//check if input is valid ID
			if(s.hasNextLong()) {
				inputID = s.nextLong();
				IDfound=true;
			}
			else System.out.println("Input not valid!");
		}


		Appointment foundAppointment = staff.searchAppointment(activeUser.getId(), inputID);
		if (foundAppointment == null) System.out.println("Appointment not found.");
		else {
			System.out.println("Appointment details:");
			System.out.println(foundAppointment.toString());
		}
	}

	/**
	 * Get inputs from the user for a new appointment, verify it and adds it to the database.
	 */
	private void addAppointment() {
		List<Long> professionals = new ArrayList<>();
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();
		String room = "";
		String treatmentType = "<undefined>";
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
		TreatmentType treatmentType = TreatmentType.searchForTreatment("<undefined>");
		// TODO get input from user
		Appointment oldAppointment = staff.searchAppointment(activeUser.getId(), appointmentId);
		Appointment modifiedAppointment = staff.editAppointment(activeUser.getId(), appointmentId, professionals, startTime, endTime, room, treatmentType);
		if (modifiedAppointment != null && !modifiedAppointment.equals(oldAppointment)) {
			try {
				undoRedoHandler.addAction(new Action(
						"Edit appointment",
						staff,
						staff.getClass().getMethod("editAppointment", long.class, long.class, List.class, LocalDateTime.class, LocalDateTime.class, String.class, TreatmentType.class),
						new Object[]{activeUser.getId(), oldAppointment.getId(), oldAppointment.getProfessionals(), oldAppointment.getStartTime(), oldAppointment.getEndTime(), oldAppointment.getRoom(), oldAppointment.getTreatmentType()},
						staff.getClass().getMethod("editAppointment", long.class, long.class, List.class, LocalDateTime.class, LocalDateTime.class, String.class, TreatmentType.class),
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
		Scanner s = new Scanner(System.in);
		boolean IDfound=false;

		//get the appointment ID from user input
		while(!IDfound)
		{
			System.out.println("Enter appointment ID: ");
			//check if input is valid ID
			if(s.hasNextLong()) {
				appointmentId = s.nextLong();
				IDfound=true;
			}
			else System.out.println("Input not valid!");
		}

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
			Staff staff = (Staff) ois.readObject();
			displayDiary(staff, null);
		} catch (FileNotFoundException e) {
			// TODO handle exception
			e.printStackTrace();
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
			System.out.println("\nWelcome to hospital scheduler.\n");
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

				//TODO add an instance of administrator to staff
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
		String warning = "\nWARNING! This will change your username. Proceed with caution.\n";

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

					activeUser.updateUsername();
					System.out.println("Your new username is " + activeUser.getUsername());

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
				// TODO ask for new input if there was  a wrong one
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

					System.out.println(counter + 1 + "out of " + numberOfProfessionals + ": please specify the role of the professional:");
					role = scanRole.nextLine();

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
}
