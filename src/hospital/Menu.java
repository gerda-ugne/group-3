package hospital;

import hospital.commands.Command;
import hospital.staff.Appointment;
import hospital.staff.Professional;
import hospital.staff.Staff;

import java.util.Set;
import java.util.Stack;

/**
 * Represents the menu in the Hospital Booking System.
 */
public class Menu {

	/**
	 * The collection of employees in the hospital (doctors, nurses, etc.)
	 */
	private Staff staff;

	/**
	 * The staff member who currently using the system.
	 */
	private Professional activeUser;

	/**
	 * The stack of commands executed by the user, for undo/redo feature.
	 */
	private Stack<Command<Appointment>> commands;

	public Menu() {

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
		// TODO - implement Menu.addAppointment
		
	}

	private void editAppointment() {
		// TODO - implement Menu.editAppointment
		
	}

	private void deleteAppointment() {
		// TODO - implement Menu.deleteAppointment
		
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

	private void undo() {
		// TODO - implement Menu.undo
		
	}

	private void redo() {
		// TODO - implement Menu.redo
		
	}

}