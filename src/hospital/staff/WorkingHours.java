package hospital.staff;

import java.time.DayOfWeek;

public class WorkingHours {

	private long startHour;

	private long endHour;

	public WorkingHours(DayOfWeek day, long startHour, long endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}

	public long getStartHour() {
		return startHour;
	}

	public void setStartHour(long startHour) {
		this.startHour = startHour;
	}

	public long getEndHour() {
		return endHour;
	}

	public void setEndHour(long endHour) {
		this.endHour = endHour;
	}
}