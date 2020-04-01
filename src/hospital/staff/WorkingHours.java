package hospital.staff;

import java.time.DayOfWeek;

/**
 * Working hours class defines working hours of the
 * Professional.
 *
 * Day of Week is defined in the HashSet WorkingHours
 * in the Professional class.
 *
 */
public class WorkingHours {

	/**
	 * Daily work shift starting hour
	 */
	private int startHour;

	/**
	 * Daily work shift ending hour
	 */
	private int endHour;

	/**
	 * Constructor for the WorkingHours class
	 * @param day day of week (e.g. Monday)
	 * @param startHour shift start hour
	 * @param endHour shift end hour
	 */
	public WorkingHours(DayOfWeek day, int startHour, int endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}

	/**
	 * Getter method for the starting hour
	 * @return start hour
	 */
	public int getStartHour() {
		return startHour;
	}

	/**
	 * Setter method for the starting hour
	 * @param startHour starting hour to be set
	 */
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	/**
	 * Getter method for the end hour
	 * @return end hour
	 */
	public int getEndHour() {
		return endHour;
	}

	/**
	 * Setter method for the end hour
	 * @param endHour end hour to be set
	 */
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
}