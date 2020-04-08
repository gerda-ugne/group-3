package hospital.staff;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Working hours class defines working hours of the
 * Professional.
 *
 * Day of Week is defined in the HashSet WorkingHours
 * in the Professional class.
 *
 */
public class WorkingHours implements Serializable {

	/**
	 * The day of the week when the working hour is defined
	 */
	private DayOfWeek dayOfWeek;

	/**
	 * Daily work shift starting hour
	 */
	private LocalTime startHour;

	/**
	 * Daily work shift ending hour
	 */
	private LocalTime endHour;

	/**
	 * Constructor for the WorkingHours class
	 * @param dayOfWeek day of week (e.g. Monday)
	 * @param startHour shift start hour
	 * @param endHour shift end hour
	 */
	public WorkingHours(DayOfWeek dayOfWeek, LocalTime startHour, LocalTime endHour) {
		this.dayOfWeek = dayOfWeek;
		this.startHour = startHour;
		this.endHour = endHour;
	}

	/**
	 * Getter method for the day of week
	 *
	 * @return the day when the working hours are defined
	 */
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * Setter of the day of week
	 *
	 * @param dayOfWeek the day when the working hours are defined
	 */
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * Getter method for the starting hour
	 * @return start hour
	 */
	public LocalTime getStartHour() {
		return startHour;
	}

	/**
	 * Setter method for the starting hour
	 * @param startHour starting hour to be set
	 */
	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	/**
	 * Getter method for the end hour
	 * @return end hour
	 */
	public LocalTime getEndHour() {
		return endHour;
	}

	/**
	 * Setter method for the end hour
	 * @param endHour end hour to be set
	 */
	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}
}